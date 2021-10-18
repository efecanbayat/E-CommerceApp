package com.efecanbayat.e_commerceapp.data.remote

import com.efecanbayat.e_commerceapp.data.entities.add.ProductAddRequest
import com.efecanbayat.e_commerceapp.data.entities.update.ProductUpdateRequest
import com.efecanbayat.e_commerceapp.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: APIService) : BaseDataSource() {

    suspend fun getProducts() = getResult {
        apiService.getProducts()
    }

    suspend fun getProductById(productId: String) = getResult {
        apiService.getProductById(productId)
    }

    suspend fun addProduct(productAddRequest: ProductAddRequest) = getResult {
        apiService.addProduct(productAddRequest)
    }

    suspend fun updateProduct(productId: String, productUpdateRequest: ProductUpdateRequest) = getResult {
        apiService.updateProduct(productId,productUpdateRequest)
    }

    suspend fun deleteProduct(productId: String) = getResult {
        apiService.deleteProduct(productId)
    }

}