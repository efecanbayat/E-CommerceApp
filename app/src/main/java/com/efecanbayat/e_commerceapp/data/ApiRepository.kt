package com.efecanbayat.e_commerceapp.data

import com.efecanbayat.e_commerceapp.data.remote.RemoteDataSource
import com.efecanbayat.e_commerceapp.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource
) {

    fun getProducts() = performNetworkOperation {
        remoteDataSource.getProducts()
    }
}