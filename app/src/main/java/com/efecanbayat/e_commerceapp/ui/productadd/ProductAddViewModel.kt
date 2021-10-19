package com.efecanbayat.e_commerceapp.ui.productadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.efecanbayat.e_commerceapp.data.ApiRepository
import com.efecanbayat.e_commerceapp.data.entities.add.ProductAddRequest
import com.efecanbayat.e_commerceapp.data.entities.add.ProductAddResponse
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductAddViewModel @Inject constructor(
    private var apiRepository: ApiRepository
) : ViewModel() {

    fun addProduct(
        productAddRequest: ProductAddRequest
    ): LiveData<Resource<ProductAddResponse>> {
        return apiRepository.addProduct(productAddRequest)
    }
}