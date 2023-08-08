package com.example.vyaperclone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentAddPurchaseBinding
import com.example.vyaperclone.databinding.FragmentAddSaleBinding

class AddSaleFragment : Fragment() {

    lateinit var _binding: FragmentAddSaleBinding
    val binding: FragmentAddSaleBinding get() = _binding
    private val sharedViewModel: SaleSharedViewModel by activityViewModels()

    var itemsEntity = mutableListOf<ItemsEntity>()
    val adapter = ProductServiceAdapter(itemsEntity) { text -> binding.etProductName.setText(text) }
    private val viewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //hiding the action bar

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        _binding = FragmentAddSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {

            if (isDataValid()) {
                sharedViewModel.listOfSale.value.add(

                    AddItems(
                        id,
                        binding.etProductName.text.toString(),
                        binding.etQuantity.text.toString().toInt(),
                        binding.etUnit.text.toString(),
                        binding.etRate.text.toString().toDouble(),
                        binding.etTaxExcluded.text.toString().toDouble()
                    )
                )
                activity?.onBackPressed()
            }
        }

        _binding.ivBackBtn.setOnClickListener {
            val action = AddSaleFragmentDirections.actionAddSaleFragmentToNavSale()
            findNavController().navigate(action)
        }
//        _binding.ivSettings.setOnClickListener {
//            val action = AddSaleFragmentDirections.actionAddSaleFragmentToNavSettings()
//            findNavController().navigate(action)
//        }

//        _binding.cvSaveAndNew.setOnClickListener {
//            Toast.makeText(
//                activity,
//                "Item / services name cannot be left empty",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        binding.rvAddSale.layoutManager = LinearLayoutManager(context)
        binding.rvAddSale.adapter = adapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.etProductName.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Scroll RecyclerView to desired position
                binding.rvAddSale.visibility = View.VISIBLE
            }
            false
        }

        binding.etProductName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())
            }
        })
    }
    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filteredTransactions = ArrayList<ItemsEntity>()

        //looping through existing elements
        for (itemProduct in itemsEntity) {
            //if the existing elements contains the search input
            if (itemProduct.name?.toLowerCase()?.contains(text.toLowerCase())!!) {
                //adding the element to filtered list
                filteredTransactions.add(itemProduct)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filteredTransactions)
    }
    override fun onResume() {
        super.onResume()
        //hiding the nav bar
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        viewModel.getItems().observe(this, Observer {
            itemsEntity.clear()
            itemsEntity.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun isDataValid(): Boolean {
        var isValid = true
        if (_binding.etProductName.text.toString().isEmpty()) {
            _binding.etProductName.error = "Required"
            isValid = false
        }
        if (_binding.etQuantity.text.toString().isEmpty()) {
            _binding.etQuantity.error = "Required"
            isValid = false
        }
        if (_binding.etRate.text.toString().isEmpty()) {
            _binding.etRate.error = "Required"
            isValid = false
        }
        if (_binding.etUnit.text.toString().isEmpty()) {
            _binding.etUnit.error = "Required"
            isValid = false
        }
        if (_binding.etTaxExcluded.text.toString().isEmpty()) {
            _binding.etTaxExcluded.error = "Required"
            isValid = false
        }


        return isValid
    }

}