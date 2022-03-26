package com.mypharmacybd.data_models.order.get

import com.google.gson.annotations.SerializedName

data class Upazila(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("district_id") var districtId: String? = null

)