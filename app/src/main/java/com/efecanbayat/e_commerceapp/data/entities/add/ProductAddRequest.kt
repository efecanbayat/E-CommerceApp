package com.efecanbayat.e_commerceapp.data.entities.add

import com.google.gson.annotations.SerializedName

data class ProductAddRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("category")
    val category: String
)
