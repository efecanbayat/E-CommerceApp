package com.efecanbayat.e_commerceapp.ui.productlist

import com.efecanbayat.e_commerceapp.data.entities.Product

interface IProductOnClickListener {

    fun onClick(product: Product)
}