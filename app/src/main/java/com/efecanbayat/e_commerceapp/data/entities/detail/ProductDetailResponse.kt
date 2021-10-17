package com.efecanbayat.e_commerceapp.data.entities.detail

import com.efecanbayat.e_commerceapp.data.entities.Product
import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("data")
    val product: Product,
    @SerializedName("success")
    val success: Boolean
)
