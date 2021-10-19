package com.efecanbayat.e_commerceapp.ui.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.efecanbayat.e_commerceapp.R
import com.efecanbayat.e_commerceapp.data.entities.update.ProductUpdateRequest
import com.efecanbayat.e_commerceapp.databinding.FragmentProductUpdateDialogBinding
import com.efecanbayat.e_commerceapp.utils.Resource
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductUpdateDialogFragment(private val productId: String) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentProductUpdateDialogBinding
    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }
        binding = FragmentProductUpdateDialogBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProductDetail()
        addListeners()
    }

    private fun getProductDetail() {
        binding.apply {
            viewModel.getProductDetail(productId).observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        dialogProgressBar.visibility = View.VISIBLE
                        itemConstraintLayout.visibility = View.INVISIBLE
                    }
                    Resource.Status.SUCCESS -> {
                        dialogProgressBar.visibility = View.GONE
                        itemConstraintLayout.visibility = View.VISIBLE
                        updateImageUrlEditText.editText?.setText(it.data!!.product.imageUrl)
                        updateNameEditText.editText?.setText(it.data!!.product.name)
                        updateCategoryEditText.editText?.setText(it.data!!.product.category)
                        updateDescriptionEditText.editText?.setText(it.data!!.product.description)
                        updatePriceEditText.editText?.setText(it.data!!.product.price.toString())
                    }
                    Resource.Status.ERROR -> {
                        dialogProgressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error! Try again", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
    }

    private fun addListeners() {

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.updateButton.setOnClickListener {

            binding.apply {
                val imageUrl = updateImageUrlEditText.editText?.text.toString()
                val name = updateNameEditText.editText?.text.toString()
                val category = updateCategoryEditText.editText?.text.toString()
                val description = updateDescriptionEditText.editText?.text.toString()
                val price = updatePriceEditText.editText?.text.toString().toInt()

                viewModel.updateProduct(
                    productId,
                    ProductUpdateRequest(name, imageUrl, description, price, category)
                )
                    .observe(viewLifecycleOwner, {
                        when (it.status) {
                            Resource.Status.LOADING -> {
                                dialogProgressBar.visibility = View.VISIBLE
                                itemConstraintLayout.visibility = View.GONE
                            }
                            Resource.Status.SUCCESS -> {
                                dialogProgressBar.visibility = View.GONE
                                itemConstraintLayout.visibility = View.VISIBLE
                                dismiss()
                                val action =
                                    ProductDetailFragmentDirections.actionProductDetailFragmentSelf(
                                        productId
                                    )
                                findNavController().navigate(action)
                            }
                            Resource.Status.ERROR -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Error! Try again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }


        }
    }
}