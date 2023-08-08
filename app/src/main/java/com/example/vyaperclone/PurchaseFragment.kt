package com.example.vyaperclone

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentPurchaseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class PurchaseFragment : Fragment() {
    private lateinit var binding: FragmentPurchaseBinding

    var transactions = mutableListOf<TransactionEntity>()
    val adapter = CustomAdapter(transactions) { text -> binding.etParty.setText(text) }


    companion object {
        fun newInstance() = PurchaseFragment()

    }

   private val sharedViewModel: PurchaseSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        return binding.root

        binding.addItemRecyclerCompose.setContent {
            LazyColumn() {
                itemsIndexed(
                    items = sharedViewModel.listOfPurchase.value
                ) { index, purchase ->
                    BilledItem(addItems = purchase)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.btnAddItems.setOnClickListener {
            val action = PurchaseFragmentDirections.actionNavPurchaseToAddPurchaseFragment()
            findNavController().navigate(action)
        }

        binding.etParty.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Scroll RecyclerView to desired position
                binding.recyclerviewName.visibility = View.VISIBLE
            }
            false
        }

        binding.btnSave.setOnClickListener {
            if (isDataValid()) {

                MainScope().launch{
                    sharedViewModel.addTransaction(
                        TransactionEntity(
                            binding.etBillNo.text.toString().toInt(),
                            Constants.PURCHASE,
                            binding.etParty.text.toString(),
                            convertListToBilledItems(),
                            convertListToBilledRate(),
                            convertListToBilledQuantity(),
                            binding.etPaid.text.toString().toLong(),
                            0,
                            binding.etTotal.text.toString().toLong(),
                            binding.etContactNo.text.toString(),
                            partyBillingAddress = null
                        )
                    )
                    sharedViewModel.listOfPurchase.value.clear()

                }
                activity?.onBackPressed()
            }


        }

        binding.btnCameraPurchaseFrag.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
            true
        }
        binding.btnSharePurchaseFrag.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            val name =
                "Item Details:  \n " +
                        "BillNo: ${ binding.etBillNo.text}\n Name: ${binding.etParty.text}\n Paid Amount: ${ binding.etPaid.text}\n " +
                        " Total Amount: ${ binding.etTotal.text}\n"

            myIntent.putExtra(Intent.EXTRA_TEXT, name);
            startActivity(Intent.createChooser(myIntent, "Share Using"))
        }

        binding.etParty.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                val input = editable.toString().trim().toLowerCase()
                filter(input)
            }
        })

    }

    private fun filter(text: String) {
        val filteredTransactions = ArrayList<TransactionEntity>()
        val uniquePartyNames = HashSet<String>()

        for (transaction in transactions) {
            val partyName = transaction.partyName?.toLowerCase()
            if (partyName?.contains(text.toLowerCase()) == true && uniquePartyNames.add(partyName)) {
                filteredTransactions.add(transaction)
            }
        }

        adapter.filterList(filteredTransactions)
    }


    private fun isDataValid(): Boolean {
        var isValid = true
        if ( binding.etBillNo.text.toString().isEmpty()) {
            binding.etBillNo.error = "Required"
            isValid = false
        }
        if ( binding.etParty.text.toString().isEmpty()) {
            binding.etParty.error = "Required"
            isValid = false
        }
        if ( binding.etContactNo.text.toString().isEmpty()) {
            binding.etContactNo.error = "Required"
            isValid = false
        }
        if ( binding.etPaid.text.toString().isEmpty()) {
            binding.etPaid.error = "Required"
            isValid = false
        }
        if ( binding.etTotal.text.toString().isEmpty()) {
            binding.etTotal.error = "Required"
            isValid = false
        }
        return isValid
    }

    private fun convertListToBilledQuantity(): String? {
        val data = sharedViewModel.listOfPurchase.value
        var quantity = ""
        for (i in data.indices) {

            if (i == 0) {
                quantity += "${data[i].quantity}"
            } else {
                quantity += ",${data[i].quantity}"
            }

        }
        return quantity
    }

    private fun convertListToBilledItems(): String? {
        val data = sharedViewModel.listOfPurchase.value
        var purchase = ""
        for (i in data.indices) {
            if (i == 0) {
                purchase += "${data[i].itemsName}"
            } else {
                purchase += ",${data[i].itemsName}"
            }

        }
        return purchase
    }

    private fun convertListToBilledRate(): String? {
        val data = sharedViewModel.listOfPurchase.value
        var rate = ""
        for (i in data.indices) {
            if (i == 0) {
                rate += "${data[i].rate}"
            } else {
                rate += ",${data[i].rate}"
            }

        }
        return rate
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.getReport().observe(this, Observer {
            transactions.clear()
            transactions.addAll(it.distinctBy { transaction -> transaction.partyName })
            adapter.notifyDataSetChanged()
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.recyclerviewName.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewName.adapter = adapter
    }

}