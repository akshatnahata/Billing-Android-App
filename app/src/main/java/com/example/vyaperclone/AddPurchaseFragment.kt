package com.example.vyaperclone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentAddPurchaseBinding
import com.example.vyaperclone.databinding.FragmentPurchaseBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPurchaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPurchaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentAddPurchaseBinding
    var itemsEntity = mutableListOf<ItemsEntity>()
    val adapter = ProductServiceAdapter(itemsEntity) { text -> binding.etProductNamePurchaseFragment.setText(text) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val viewModel: ProductViewModel by activityViewModels()
    private val sharedViewModel: PurchaseSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPurchaseBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPurchaseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPurchaseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.etProductNamePurchaseFragment.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Scroll RecyclerView to desired position
            binding.rvAddPurchase.visibility = View.VISIBLE
            }
            false
        }

        binding.etProductNamePurchaseFragment.addTextChangedListener(object : TextWatcher {
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
        viewModel.getItems().observe(this, Observer {
            itemsEntity.clear()
            itemsEntity.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvAddPurchase.layoutManager = LinearLayoutManager(context)
        binding.rvAddPurchase.adapter = adapter

        binding.btnSave.setOnClickListener{
            if (isDataValid()) {
                val name = binding.etProductNamePurchaseFragment.text.toString()
                val quantity = binding.etQuantityProductFrag.text.toString().toInt()
                val rate = binding.etRateProductFrag.text.toString().toDouble()
                val tax = binding.etTaxExcluded.text.toString().toDouble()
                val unit = binding.etUnit.text.toString()
                sharedViewModel.addPurchase(id, name, quantity, unit, rate, tax)
                findNavController().popBackStack()
            }
        }
    }

    private fun isDataValid(): Boolean {
        var isValid = true
        if (binding.etProductNamePurchaseFragment.text.toString().isEmpty()) {
            binding.etProductNamePurchaseFragment.error = "Required"
            isValid = false
        }
        if (binding.etQuantityProductFrag.text.toString().isEmpty()) {
            binding.etQuantityProductFrag.error = "Required"
            isValid = false
        }
        if (binding.etRateProductFrag.text.toString().isEmpty()) {
            binding.etRateProductFrag.error = "Required"
            isValid = false
        }
        if (binding.etUnit.text.toString().isEmpty()) {
            binding.etUnit.error = "Required"
            isValid = false
        }
        if (binding.etTaxExcluded.text.toString().isEmpty()) {
            binding.etTaxExcluded.error = "Required"
            isValid = false
        }

        return isValid
            val name = binding.etProductNamePurchaseFragment.text.toString()
            val quantity = binding.etQuantityProductFrag.text.toString().toInt()
            val rate = binding.etRateProductFrag.text.toString().toDouble()
            val tax = binding.etTaxExcluded.text.toString().toDouble()
            val unit = binding.etUnit.text.toString()
            sharedViewModel.addPurchase(id,name, quantity, unit, rate,tax)
            findNavController().popBackStack()
        }

}