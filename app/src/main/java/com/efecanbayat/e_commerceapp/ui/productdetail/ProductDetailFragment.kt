package com.efecanbayat.e_commerceapp.ui.productdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.efecanbayat.e_commerceapp.base.BaseFragment
import com.efecanbayat.e_commerceapp.databinding.FragmentProductDetailBinding
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchProductDetail()
        addListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchProductDetail() {

        viewModel.getProductDetail(args.productId).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.apply {
                        itemConstraintLayout.visibility = View.GONE
                        loadingAnimation.visibility = View.VISIBLE
                    }
                }
                Resource.Status.SUCCESS -> {
                    binding.apply {
                        itemConstraintLayout.visibility = View.VISIBLE
                        loadingAnimation.visibility = View.GONE
                        val product = it.data!!.product

                        Glide.with(requireContext())
                            .load(product.imageUrl)
                            .into(productImageView)
                        productNameTextView.text = product.name
                        productDescriptionTextView.text = product.description
                        priceTextView.text = "${product.price} ₺"
                        ratingBar.rating = product.rating
                        ratingTextView.text = "(${product.rating})"
                    }


                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error! Try again", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun deleteProduct() {
        viewModel.deleteProduct(args.productId).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    binding.apply {
                        itemConstraintLayout.visibility = View.GONE
                        loadingAnimation.visibility = View.VISIBLE
                    }

                }
                Resource.Status.SUCCESS -> {
                    binding.loadingAnimation.visibility = View.GONE
                    findNavController().popBackStack()
                    Toast.makeText(
                        requireContext(),
                        "Product deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Error! Try again", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun addListeners() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.editImageView.setOnClickListener {
            val dialog = ProductUpdateDialogFragment(args.productId)
            dialog.show(requireActivity().supportFragmentManager, "productUpdateDialog")
        }

        binding.deleteImageView.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.setTitle("Are you sure?")
            alertDialog.setMessage("Do you really want to delete this product?")
            alertDialog.setPositiveButton("Yes") { dialog, which ->
                deleteProduct()
            }
            alertDialog.setNegativeButton("Cancel") { dialog, which ->
                //do nothing
            }
            alertDialog.show()
        }
    }
}