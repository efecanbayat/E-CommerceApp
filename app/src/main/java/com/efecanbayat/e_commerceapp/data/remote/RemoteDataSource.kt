package com.efecanbayat.e_commerceapp.data.remote

import com.efecanbayat.e_commerceapp.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: APIService) : BaseDataSource() {

    suspend fun getProducts() = getResult {
        apiService.getProducts()
    }

}