package com.efecanbayat.e_commerceapp.ui.productdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.efecanbayat.e_commerceapp.data.ApiRepository
import com.efecanbayat.e_commerceapp.data.entities.delete.ProductDeleteResponse
import com.efecanbayat.e_commerceapp.data.entities.detail.ProductDetailResponse
import com.efecanbayat.e_commerceapp.data.entities.update.ProductUpdateRequest
import com.efecanbayat.e_commerceapp.data.entities.update.ProductUpdateResponse
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private var apiRepository: ApiRepository
) : ViewModel() {

    fun getProductDetail(productId: String): LiveData<Resource<ProductDetailResponse>> {
        return apiRepository.getProductById(productId)
    }

    fun updateProduct(
        productId: String,
        productUpdateRequest: ProductUpdateRequest
    ): LiveData<Resource<ProductUpdateResponse>> {
        return apiRepository.updateProduct(productId, productUpdateRequest)
    }

    fun deleteProduct(productId:String): LiveData<Resource<ProductDeleteResponse>>{
        return apiRepository.deleteProduct(productId)
    }
}