package com.efecanbayat.e_commerceapp.data.remote

import com.efecanbayat.e_commerceapp.data.entities.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("product")
    suspend fun getProducts(): Response<ProductListResponse>
}