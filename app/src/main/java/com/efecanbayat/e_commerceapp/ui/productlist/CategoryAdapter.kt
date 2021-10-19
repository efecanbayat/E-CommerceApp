package com.efecanbayat.e_commerceapp.ui.productlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.efecanbayat.e_commerceapp.R
import com.efecanbayat.e_commerceapp.data.entities.Category
import com.efecanbayat.e_commerceapp.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList = ArrayList<Category>()
    private var listener: ICategoryOnClickListener? = null
    private var selectedItem = 0

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categoryList[position]

        holder.binding.apply {

            Glide.with(categoryImageView.context)
                .load(category.categoryImage).into(categoryImageView)

            categoryNameTextView.text = category.categoryName

            if (selectedItem == position) {
                categoryCardView.setCardBackgroundColor(
                    categoryCardView.context.getColor(
                        R.color.button_blue
                    )
                )
            } else {
                categoryCardView.setCardBackgroundColor(
                    categoryCardView.context.getColor(
                        R.color.white
                    )
                )
            }
        }

        holder.itemView.setOnClickListener {
            selectedItem = holder.adapterPosition
            listener?.onClick(category)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = categoryList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryList(categoryList: List<Category>) {
        this.categoryList = ArrayList(categoryList)
        notifyDataSetChanged()
    }

    fun addListener(listener: ICategoryOnClickListener) {
        this.listener = listener
    }
}