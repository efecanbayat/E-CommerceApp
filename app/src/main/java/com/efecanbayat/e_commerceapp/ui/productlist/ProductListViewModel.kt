package com.efecanbayat.e_commerceapp.ui.productlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.efecanbayat.e_commerceapp.data.ApiRepository
import com.efecanbayat.e_commerceapp.data.entities.Product
import com.efecanbayat.e_commerceapp.data.entities.list.ProductListResponse
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private var apiRepository: ApiRepository
) : ViewModel() {

    var productList: ArrayList<Product>? = null

    fun getProducts(): LiveData<Resource<ProductListResponse>> {
        return apiRepository.getProducts()
    }

    fun getProductByCategory(category: String): LiveData<Resource<ProductListResponse>> {
        return apiRepository.getProductByCategory(category)
    }

    fun searchTextOnProductList(text: String?): ArrayList<Product>? {
        if (text.isNullOrEmpty())
            return productList

        val filterList: ArrayList<Product> = arrayListOf()
        productList?.forEach { product ->
            if (product.name.contains(text, true))
                filterList.add(product)
            else if (product.category.contains(text, true))
                filterList.add(product)
        }
        return filterList
    }
}