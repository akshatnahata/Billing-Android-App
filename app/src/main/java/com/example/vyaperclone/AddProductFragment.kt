package com.example.vyaperclone

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding
import com.example.vyaperclone.databinding.FragmentAddProductBinding
import com.example.vyaperclone.databinding.FragmentPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding

    private val addProductViewModel: AddProductViewModel by viewModels()

    private val pic_id = 123
    private val args by navArgs<AddProductFragmentArgs>()

    private var editMode = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAddProductBinding.inflate(inflater, container, false)

        return binding.root


    }

    //calling the menu on action bar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product, menu)

    }

    // selection of camera btn and setting btn
    //what will happen
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_camera -> {

                //opening the camera and taking the photo
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivity(cameraIntent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSaveAddProduct.setOnClickListener {

            if (isDataValid()) {
                val itemsEntity = ItemsEntity(

                    binding.etItemName.text.toString(),
                    Constants.PRODUCT,
                    binding.etItemCode.text.toString(),
                    binding.etHSN.text.toString(),
                    binding.etSalePrice.text.toString().toLong(),
                    binding.etPurchasePrice.text.toString().toLong(),
                    0.2f,
                    if (binding.etStockQuantity.text.isNullOrEmpty()) {
                        0L
                    } else {
                        binding.etStockQuantity.text.toString().toLong()
                    }

                )

                if (!editMode) {
                    addProductViewModel.addItem(itemsEntity)
                } else {
                    if (isDataValid()) {

                        args.itemdetails?.name = binding.etItemName.text.toString()
                        args.itemdetails?.itemCode = binding.etItemCode.text.toString()
                        args.itemdetails?.salePrice = binding.etSalePrice.text.toString().toLong()
                        args.itemdetails?.sacCode = binding.etHSN.text.toString()
                        args.itemdetails?.purchasePrice = binding.etPurchasePrice.text.toString().toLong()
                        args.itemdetails?.stock = binding.etStockQuantity.text.toString().toLong()
                        addProductViewModel.updateItem(args.itemdetails!!)
                    }
                }
                activity?.onBackPressed()

            }
        }

        binding.btnCancel.setOnClickListener {
            activity?.onBackPressed()

        }

        //editing values
        if (args.itemdetails != null) {

            binding.etItemName.setText(args.itemdetails!!.name)
            binding.etItemCode.setText(args.itemdetails!!.itemCode.toString())
            binding.etSalePrice.setText(args.itemdetails!!.salePrice.toString())
            binding.etStockQuantity.setText(args.itemdetails!!.stock.toString())
            binding.etPurchasePrice.setText(args.itemdetails!!.purchasePrice.toString())
            editMode = true
        }


    }


    private fun isDataValid(): Boolean {
        var isValid = true
        if (binding.etItemName.text.toString().isEmpty()) {
            binding.etItemName.error = "Required"
            isValid = false
        }
        if (binding.etItemCode.text.toString().isEmpty()) {
            binding.etItemCode.error = "Required"
            isValid = false
        }
        if (binding.etSalePrice.text.toString().isEmpty()) {
            binding.etSalePrice.error = "Required"
            isValid = false
        }
        if (binding.etPurchasePrice.text.toString().isEmpty()) {
            binding.etPurchasePrice.error = "required"
            isValid = false
        }
        if (binding.etHSN.text.toString().isEmpty()) {
            binding.etHSN.error = "required"
            isValid = false
        }
        if (binding.etTax.toString().isEmpty()) {
            binding.etTax.error = "required"
            isValid = false
        }

        return isValid
    }


    companion object {

        fun newInstance() =
            AddProductFragment().apply {

            }
    }

}