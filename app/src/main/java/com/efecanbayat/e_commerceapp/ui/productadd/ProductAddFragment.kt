package com.efecanbayat.e_commerceapp.ui.productadd

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.efecanbayat.e_commerceapp.base.BaseFragment
import com.efecanbayat.e_commerceapp.data.entities.add.ProductAddRequest
import com.efecanbayat.e_commerceapp.databinding.FragmentProductAddBinding
import com.efecanbayat.e_commerceapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductAddFragment : BaseFragment<FragmentProductAddBinding>(FragmentProductAddBinding::inflate) {

    private val viewModel: ProductAddViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListeners()
    }

    private fun addListeners() {

        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addButton.setOnClickListener {
            addProduct()
        }
    }

    private fun addProduct() {

        binding.apply {
            val imageUrl = addImageUrlEditText.editText?.text.toString()
            val name = addNameEditText.editText?.text.toString()
            val category = addCategoryEditText.editText?.text.toString()
            val description = addDescriptionEditText.editText?.text.toString()
            val price = addPriceEditText.editText?.text.toString().toIntOrNull()

            if (imageUrl.isEmpty() || name.isEmpty() || category.isEmpty() || description.isEmpty() || price == null) {

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Error!")
                builder.setMessage("Please fill out all the fields")
                builder.setPositiveButton("Ok") { dialog, which ->
                }
                builder.show()
            } else {
                viewModel.addProduct(
                    ProductAddRequest(name, imageUrl, description, price, category)
                )
                    .observe(viewLifecycleOwner, {
                        when (it.status) {
                            Resource.Status.LOADING -> {
                                loadingAnimation.visibility = View.VISIBLE
                                itemConstraintLayout.visibility = View.GONE
                            }
                            Resource.Status.SUCCESS -> {
                                loadingAnimation.visibility = View.GONE
                                findNavController().popBackStack()
                                Toast.makeText(
                                    requireContext(),
                                    "Product added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
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