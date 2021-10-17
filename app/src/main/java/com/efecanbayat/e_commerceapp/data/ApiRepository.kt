package com.efecanbayat.e_commerceapp.data

import com.efecanbayat.e_commerceapp.data.entities.update.ProductUpdateRequest
import com.efecanbayat.e_commerceapp.data.remote.RemoteDataSource
import com.efecanbayat.e_commerceapp.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource
) {

    fun getProducts() = performNetworkOperation {
        remoteDataSource.getProducts()
    }

    fun getProductById(productId: String) = performNetworkOperation {
        remoteDataSource.getProductById(productId)
    }

    fun updateProduct(productId: String, productUpdateRequest: ProductUpdateRequest) = performNetworkOperation {
            remoteDataSource.updateProduct(productId, productUpdateRequest)
        }

    fun deleteProduct(productId: String) = performNetworkOperation {
        remoteDataSource.deleteProduct(productId)
    }
}