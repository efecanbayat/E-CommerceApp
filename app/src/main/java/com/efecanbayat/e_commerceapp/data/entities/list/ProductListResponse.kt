package com.efecanbayat.e_commerceapp.data.entities.list


import com.efecanbayat.e_commerceapp.data.entities.Product
import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("data")
    val productList: ArrayList<Product>,
    @SerializedName("success")
    val success: Boolean
)