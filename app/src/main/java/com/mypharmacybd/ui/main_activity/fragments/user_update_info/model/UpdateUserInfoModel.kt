package com.mypharmacybd.ui.main_activity.fragments.user_update_info.model

import android.util.Log
import com.mypharmacybd.data_models.address.*
import com.mypharmacybd.data_models.user.UserUpdateInfo
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.db.PharmacyDAO
import com.mypharmacybd.network.api.ApiConfig
import com.mypharmacybd.network.api.ApiServices
import com.mypharmacybd.network.api.models.APIFailure
import com.mypharmacybd.ui.main_activity.fragments.user_update_info.UpdateInfoContract
import com.mypharmacybd.user.Session
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUserInfoModel(
    val dbService: PharmacyDAO,
    val apiServices: ApiServices
) : UpdateInfoContract.Model {

    private var _divisionResponse:DivisionResponse? = null
    private var _districtResponse:DistrictResponse? = null
    private var _upazilaResponse:UpazilaResponse? = null

    private var _selectedDivision:Division? = null
    private var _selectedDistrict:District? = null
    private var _selectedUpazila:Upazila? = null



    override fun getDivision(onFinishedListener: UpdateInfoContract.OnFinishedListener) {
        // check previous loaded Division Data
        if(_divisionResponse != null){
            onFinishedListener.onSuccessGetDivision(_divisionResponse!!)
            return
        }

        // or Load Division Data from API
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken
        apiServices.getDivision(headerMap).enqueue(object : Callback<DivisionResponse> {
            override fun onResponse(
                call: Call<DivisionResponse>,
                response: Response<DivisionResponse>
            ) {
                Log.d(TAG, "onResponse: is called with code ${response.code()}")
                if (response.isSuccessful) {
                    response.body()?.let {
                        onFinishedListener.onSuccessGetDivision(it)
                        _divisionResponse = it
                    }
                } else {
                    onFinishedListener.onFailureGetDivision(APIFailure(response.code(), response.message()))
                }
            }

            override fun onFailure(call: Call<DivisionResponse>, t: Throwable) {
                onFinishedListener.onFailureGetDivision(APIFailure(-1, t.message.toString()))
            }
        })
    }


    override fun getDistrictByDivision(
        division: Division,
        onFinishedListener: UpdateInfoContract.OnFinishedListener
    ) {
        // Check Previous Loaded Data
        if (_selectedDivision == null) _selectedDivision = division
        else if(_selectedDivision?.id == division.id){
            if(_districtResponse != null){
                onFinishedListener.onSuccessGetDistrict(_districtResponse!!)
                return
            }
        }

        // or Load Data from API
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken
        apiServices.getDistrictsByDivId(headerMap, division.id.toString())
            .enqueue(object: Callback<DistrictResponse>{
                override fun onResponse(
                    call: Call<DistrictResponse>,
                    response: Response<DistrictResponse>
                ) {
                    if(response.isSuccessful){
                        response.body()?.let {
                            onFinishedListener.onSuccessGetDistrict(it)
                            _districtResponse = it
                        }
                    } else {
                        onFinishedListener.onFailureGetDistrict(APIFailure(response.code(),
                            response.message()))
                    }
                }

                override fun onFailure(call: Call<DistrictResponse>, t: Throwable) {
                    onFinishedListener.onFailureGetDistrict(APIFailure(-1, t.message.toString()))
                }
            })
    }

    override fun getUpazilaByDistrict(
        district: District,
        onFinishedListener: UpdateInfoContract.OnFinishedListener
    ) {
        if(_selectedDistrict == null) _selectedDistrict = district
        else if(_selectedDistrict?.id == district.id){
            if(_upazilaResponse != null){
                onFinishedListener.onSuccessGetUpazila(_upazilaResponse!!)
                return
            }
        }

        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken
        apiServices.getUpazilasByDisId(headerMap, district.id.toString()).enqueue(
            object : Callback<UpazilaResponse>{
                override fun onResponse(
                    call: Call<UpazilaResponse>,
                    response: Response<UpazilaResponse>
                ) {
                    if(response.isSuccessful){
                        response.body()?.let { onFinishedListener.onSuccessGetUpazila(it) }
                    } else {
                        onFinishedListener.onFailureGetUpazila(APIFailure(response.code(),
                            response.message()))
                    }
                }

                override fun onFailure(call: Call<UpazilaResponse>, t: Throwable) {
                    onFinishedListener.onFailureGetUpazila(APIFailure(-1, t.message.toString()))
                }
            }
        )
    }

    override fun updateUserInfo(
        updateInfo: UserUpdateInfo,
        onFinishedListener: UpdateInfoContract.OnFinishedListener
    ) {
        val headerMap = ApiConfig.headerMap
        headerMap[ApiConfig.AUTHORIZATION] = "Bearer " + Session.authToken
        apiServices.updateUserInformation(headerMap, updateInfo).enqueue(
            object : Callback<UserUpdateInfoResponse>{
                override fun onResponse(
                    call: Call<UserUpdateInfoResponse>,
                    response: Response<UserUpdateInfoResponse>
                ) {
                    if(response.isSuccessful){
                        response.body()?.let { onFinishedListener.onSuccessUpdateUserInfo(it) }
                        Log.d(TAG, "onResponse: message = ${response.body()?.message}")
                    } else {
                        onFinishedListener.onFailureUpdateUserInfo(
                            APIFailure(response.code(), response.message())
                        )
                        Log.d(TAG, "onResponse: response code ${response.code()}")
                        Log.d(TAG, "onResponse: response message ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserUpdateInfoResponse>, t: Throwable) {
                    onFinishedListener.onFailureUpdateUserInfo(
                        APIFailure(-1, t.message.toString())
                    )
                    Log.d(TAG, "onFailure: message = ${t.message}")
                }
            })
    }

    companion object {
        private const val TAG = "UpdateUserInfoModel"
    }
}