package com.mypharmacybd.data_models.order.get

import com.google.gson.annotations.SerializedName

data class Division(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null

)