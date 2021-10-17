package com.efecanbayat.e_commerceapp.data.entities.update

import com.google.gson.annotations.SerializedName

data class ProductUpdateRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("category")
    val category: String
)
