package com.mypharmacybd.data_models.order
import com.mypharmacybd.data_models.order.get.*
import com.google.gson.annotations.SerializedName

data class GetOrderResponse(
    @SerializedName("data") var data: ArrayList<Data> = arrayListOf(),
    @SerializedName("links") var links: Links? = Links(),
    @SerializedName("meta") var meta: Meta? = Meta()
)








