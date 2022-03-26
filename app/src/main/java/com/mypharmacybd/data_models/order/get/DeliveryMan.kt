package com.mypharmacybd.data_models.order.get

import com.google.gson.annotations.SerializedName

data class DeliveryMan(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("nid") var nid: String? = null

)