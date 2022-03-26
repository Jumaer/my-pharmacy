package com.mypharmacybd.network.api

import com.mypharmacybd.data_models.Categories
import com.mypharmacybd.data_models.Products
import com.mypharmacybd.data_models.address.DistrictResponse
import com.mypharmacybd.data_models.address.DivisionResponse
import com.mypharmacybd.data_models.address.UpazilaResponse
import com.mypharmacybd.data_models.order.PostOrder
import com.mypharmacybd.data_models.order.PostOrderResponse
import com.mypharmacybd.data_models.search.SearchResponse
import com.mypharmacybd.data_models.slider.SliderData
import com.mypharmacybd.data_models.user.UserResponse
import com.mypharmacybd.data_models.user.UserUpdateInfo
import com.mypharmacybd.data_models.user.UserUpdateInfoResponse
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginCredentials
import com.mypharmacybd.ui.auth.fragments.user_login.model.models.LoginResponse
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationData
import com.mypharmacybd.ui.auth.fragments.user_registration.model.models.RegistrationResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("register")
    fun userRegistration(
        @HeaderMap headerMap: Map<String, String>,
        @Body registrationData: RegistrationData
    ): Call<RegistrationResponse>

    @POST("login")
    fun userLogin(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
        @Body loginCredentials: LoginCredentials
    ): Call<LoginResponse>

    @GET("categories")
    fun getAllCategories(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
    ): Call<Categories>


    @GET("products")
    fun getAllProducts(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap
    ): Call<Products>

    @GET("category/products/1")
    fun getHomeProducts(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap
    ): Call<Categories>

    @GET("products/{name}")
    fun getProductByCategory(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
        @Path("name") category: String
    ): Call<Products>

    @GET("user/show")
    fun showUserData(@HeaderMap headerMap: Map<String, String>): Call<UserResponse>

    @GET("divisions")
    fun getDivision(@HeaderMap headerMap: Map<String, String>): Call<DivisionResponse>

    @GET("districts/{id}")
    fun getDistrictsByDivId(
        @HeaderMap headerMap: Map<String, String>,
        @Path("id") divisionId: String
    ): Call<DistrictResponse>

    @GET("upazilas/{id}")
    fun getUpazilasByDisId(
        @HeaderMap headerMap: Map<String, String>,
        @Path("id") districtId: String
    ): Call<UpazilaResponse>


    @PUT("user/update")
    fun updateUserInformation(
        @HeaderMap headerMap: Map<String, String>,
        @Body updateInfo: UserUpdateInfo
    ): Call<UserUpdateInfoResponse>

    @GET("sliders")
    fun getSliders(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap
    ): Call<SliderData>

    @POST("order/store")
    fun postOrder(
        @HeaderMap headerMap: Map<String, String>,
        @Body postOrder: PostOrder
    ): Call<PostOrderResponse>



    @GET("order")
    fun getOrder(
        @HeaderMap headerMap: Map<String, String>
    ): Call<PostOrderResponse>

    @GET("search")
    fun searchByProductName(
        @HeaderMap headerMap: Map<String, String> = ApiConfig.headerMap,
        @Query("product") queryString: String
    ):Call<SearchResponse>


}