package com.efecanbayat.e_commerceapp.data.remote

import com.efecanbayat.e_commerceapp.data.entities.add.ProductAddRequest
import com.efecanbayat.e_commerceapp.data.entities.add.ProductAddResponse
import com.efecanbayat.e_commerceapp.data.entities.delete.ProductDeleteResponse
import com.efecanbayat.e_commerceapp.data.entities.detail.ProductDetailResponse
import com.efecanbayat.e_commerceapp.data.entities.list.ProductListResponse
import com.efecanbayat.e_commerceapp.data.entities.update.ProductUpdateRequest
import com.efecanbayat.e_commerceapp.data.entities.update.ProductUpdateResponse
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET("product")
    suspend fun getProducts(): Response<ProductListResponse>

    @GET("product/category/{category}")
    suspend fun getProductByCategory(@Path("category") category: String): Response<ProductListResponse>

    @GET("product/{id}")
    suspend fun getProductById(@Path("id") productId: String): Response<ProductDetailResponse>

    @POST("product")
    suspend fun addProduct(
        @Body productAddRequest: ProductAddRequest
    ): Response<ProductAddResponse>

    @PUT("product/{id}")
    suspend fun updateProduct(
        @Path("id") productId: String,
        @Body productUpdateRequest: ProductUpdateRequest
    ): Response<ProductUpdateResponse>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") productId: String): Response<ProductDeleteResponse>
}