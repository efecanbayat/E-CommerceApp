package com.efecanbayat.e_commerceapp.ui.productlist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.efecanbayat.e_commerceapp.R
import com.efecanbayat.e_commerceapp.base.BaseFragment
import com.efecanbayat.e_commerceapp.data.entities.Category
import com.efecanbayat.e_commerceapp.data.entities.Product
import com.efecanbayat.e_commerceapp.databinding.FragmentProductListBinding
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment :
    BaseFragment<FragmentProductListBinding>(FragmentProductListBinding::inflate) {

    private val viewModel: ProductListViewModel by viewModels()
    private val productAdapter = ProductAdapter()
    private val categoryAdapter = CategoryAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
        fetchProducts()
        fetchCategories()
        addListeners()
    }

    private fun initAdapters() {
        binding.itemConstraintLayout.visibility = View.GONE
        binding.productRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.productRecyclerView.adapter = productAdapter
        binding.categoryRecyclerView.adapter = categoryAdapter
    }

    private fun fetchProducts() {
        viewModel.getProducts().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.loadingAnimation.visibility = View.VISIBLE
                    binding.itemConstraintLayout.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.itemConstraintLayout.visibility = View.VISIBLE
                    binding.productRecyclerView.visibility = View.VISIBLE
                    viewModel.productList = it.data?.productList
                    setProductList(viewModel.productList)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error! Try again", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun fetchProductsByCategory(category: Category) {
        viewModel.getProductByCategory(category.categoryName)
            .observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        binding.loadingAnimation.visibility = View.VISIBLE
                        binding.productRecyclerView.visibility = View.GONE
                    }
                    Resource.Status.SUCCESS -> {

                        if (category.categoryName == "All") {
                            fetchAllProducts()
                        } else {
                            binding.loadingAnimation.visibility = View.GONE
                            binding.productRecyclerView.visibility = View.VISIBLE
                            viewModel.productList = it.data?.productList
                            setProductList(viewModel.productList)
                        }

                    }
                    Resource.Status.ERROR -> {
                        Toast.makeText(requireContext(), "Error! Try again", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun fetchAllProducts() {
        viewModel.getProducts().observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.loadingAnimation.visibility = View.VISIBLE
                    binding.productRecyclerView.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    binding.loadingAnimation.visibility = View.GONE
                    binding.productRecyclerView.visibility = View.VISIBLE
                    viewModel.productList = it.data?.productList
                    setProductList(viewModel.productList)
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error! Try again", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun fetchCategories() {
        val categoryList = ArrayList<Category>()
        categoryList.add(Category(R.drawable.ic_all, "All"))
        categoryList.add(Category(R.drawable.ic_phone, "Phone"))
        categoryList.add(Category(R.drawable.ic_laptop, "Laptop"))
        categoryList.add(Category(R.drawable.ic_game_console, "Game Console"))
        categoryList.add(Category(R.drawable.ic_wearable_technology, "Wearable Technology"))
        categoryList.add(Category(R.drawable.ic_headset, "Headset"))
        categoryList.add(Category(R.drawable.ic_monitor, "Monitor"))

        categoryAdapter.setCategoryList(categoryList)
    }

    private fun addListeners() {

        categoryAdapter.addListener(object : ICategoryOnClickListener {
            override fun onClick(category: Category) {
                fetchProductsByCategory(category)
            }

        })

        productAdapter.addListener(object : IProductOnClickListener {
            override fun onClick(product: Product) {
                val action =
                    ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                        product.id
                    )
                findNavController().navigate(action)
                productAdapter.removeListener()
            }

        })

        binding.addImageView.setOnClickListener {

            findNavController().navigate(R.id.action_productListFragment_to_productAddFragment)
            productAdapter.removeListener()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val filterList = viewModel.searchTextOnProductList(query)
                setProductList(filterList)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filterList = viewModel.searchTextOnProductList(newText)
                setProductList(filterList)
                return true
            }

        })
    }

    private fun setProductList(productList: ArrayList<Product>?) {
        productAdapter.setProductList(productList)
    }
}