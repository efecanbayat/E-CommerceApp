package com.efecanbayat.e_commerceapp.ui.productlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.efecanbayat.e_commerceapp.R
import com.efecanbayat.e_commerceapp.data.entities.Product
import com.efecanbayat.e_commerceapp.databinding.ItemProductBinding

class ProductAdapter:RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList = ArrayList<Product>()
    private var listener: IProductOnClickListener? = null

    inner class ProductViewHolder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.apply {
            productNameTextView.text = product.name
            productPriceTextView.text = "${product.price} ₺"
            Glide.with(productImageView.context)
                .load(product.imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(productImageView)
        }

        holder.itemView.setOnClickListener{
            listener?.onClick(product)
        }
    }

    override fun getItemCount(): Int = productList.size

    fun addListener(listener: IProductOnClickListener){
        this.listener = listener
    }
    fun removeListener(){
        this.listener = null
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setProductList(productList: ArrayList<Product>?){
        productList?.let {
            this.productList = productList
            notifyDataSetChanged()
        }
    }
}