package com.efecanbayat.e_commerceapp.ui.productlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.efecanbayat.e_commerceapp.R
import com.efecanbayat.e_commerceapp.data.entities.Product
import com.efecanbayat.e_commerceapp.databinding.FragmentProductListBinding
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    private val viewModel: ProductListViewModel by viewModels()
    private val productAdapter = ProductAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProductAdapter()
        fetchProducts()
        addListeners()
    }

    private fun initProductAdapter() {
        binding.productRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.productRecyclerView.adapter = productAdapter
    }

    private fun fetchProducts() {
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

    private fun addListeners() {
        productAdapter.addListener(object : IProductOnClickListener {
            override fun onClick(product: Product) {
                val action = ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(product.id)
                findNavController().navigate(action)
                productAdapter.removeListener()
            }

        })
        binding.addImageView.setOnClickListener {

            findNavController().navigate(R.id.action_productListFragment_to_productAddFragment)
            productAdapter.removeListener()
        }
    }

    private fun setProductList(productList: ArrayList<Product>?) {
        productAdapter.setProductList(productList)
    }
}