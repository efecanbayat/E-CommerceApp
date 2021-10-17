package com.efecanbayat.e_commerceapp.ui.productdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.efecanbayat.e_commerceapp.databinding.FragmentProductDetailBinding
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel: ProductDetailViewModel by viewModels()
    private val args: ProductDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

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
                    binding.productImageView.visibility = View.GONE
                    binding.linearLayout.visibility = View.GONE
                    binding.loadingAnimation.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    binding.productImageView.visibility = View.VISIBLE
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.loadingAnimation.visibility = View.GONE
                    val product = it.data!!.product

                    Glide.with(requireContext())
                        .load(product.imageUrl)
                        .into(binding.productImageView)
                    binding.productNameTextView.text = product.name
                    binding.productDescriptionTextView.text = product.description
                    binding.priceTextView.text = "${product.price} ₺"

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
                    binding.productImageView.visibility = View.GONE
                    binding.linearLayout.visibility = View.GONE
                    binding.loadingAnimation.visibility = View.VISIBLE
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
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Are you sure?")
            builder.setMessage("Do you really want to delete this product?")
            builder.setPositiveButton("Yes") { dialog, which ->
                deleteProduct()
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                //do nothing
            }
            builder.show()
        }
    }
}