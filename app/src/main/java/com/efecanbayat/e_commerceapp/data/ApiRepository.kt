package com.efecanbayat.e_commerceapp.data

import com.efecanbayat.e_commerceapp.data.remote.RemoteDataSource
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource
) {

}