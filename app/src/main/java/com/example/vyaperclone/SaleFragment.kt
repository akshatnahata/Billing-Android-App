package com.example.vyaperclone

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentPurchaseBinding
import com.example.vyaperclone.databinding.FragmentSaleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaleFragment : Fragment() {

    private lateinit var binding: FragmentSaleBinding

    var transactions = mutableListOf<TransactionEntity>()
    val adapter = CustomAdapter(transactions) { text -> binding.etCustomer.setText(text) }

    private val sharedViewModel: SaleSharedViewModel by activityViewModels()


    override fun onResume() {
        super.onResume()
        //hiding the nav bar
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        val data = sharedViewModel.listOfSale.value
        var totalPrice: Long = 0
        for (i in data.indices) {
            totalPrice += (data[i].rate.toLong() * data[i].quantity)
        }
        binding.etTotalAmount.setText(totalPrice.toString())

        sharedViewModel.getReport().observe(this, Observer {
            transactions.clear()
            transactions.addAll(it.distinctBy { transaction -> transaction.partyName })
            adapter.notifyDataSetChanged()
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(com.example.vyaperclone.R.menu.sales_menu , menu)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = FragmentSaleBinding.inflate(inflater, container, false)
        return binding.root

        binding.ItemRecyclerCompose.setContent{
                LazyColumn() {
                    itemsIndexed(
                        items = sharedViewModel.listOfSale.value
                    ) { index, sale ->
                        BilledItem(addItems = sale)
//                        BilledItem(sale.productName, index, sale.quantity, sale.price)
                    }
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = sharedViewModel.listOfSale.value
        var totalPrice: Long = 0
        for (i in data.indices) {
            totalPrice += (data[i].rate.toLong() * data[i].quantity)
        }
        binding.etTotalAmount.setText(totalPrice.toString())

        binding.etPaidAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (binding.etTotalAmount.text.toString().isNotEmpty() && binding.etPaidAmount.text.toString()
                        .isNotEmpty()
                ) {
                    val balanceDue: Long =
                        binding.etTotalAmount.text.toString().toLong() - binding.etPaidAmount.text.toString()
                            .toLong()
                    val due = balanceDue.toString()
                    binding.etBalanceDue.setText(due)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        binding.recyclerviewCustomer.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewCustomer.adapter = adapter

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnAddItems2.setOnClickListener {
            val action = SaleFragmentDirections.actionNavSaleToAddSaleFragment()
            findNavController().navigate(action)
        }

        binding.btnSave2.setOnClickListener {

            if (isDataValid()) {
                CoroutineScope(Dispatchers.IO).launch {


                    sharedViewModel.addTransaction(

                        TransactionEntity(
                            binding.etInvNo.text.toString().toInt(),
                            Constants.SALE,
                            binding.etCustomer.text.toString(),
                            convertListToBilledItems(),
                            convertListToBilledRate(),
                            convertListToBilledQuantity(),
                            0,
                            binding.etPaidAmount.text.toString().toLong(),
                            binding.etTotalAmount.text.toString().toLong(),
                            binding.etContactNo.text.toString(),
                            partyBillingAddress = null
                        )
                    )
                    sharedViewModel.listOfSale.value.clear()

                }
                activity?.onBackPressed()
            }


        }


        //toast msg
//        binding.btnSaveAndNew2.setOnClickListener {
//            Toast.makeText(
//                activity,
//                "Item / services name cannot be left empty",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        binding.ibCameraSaleFragment.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
            true
        }

        binding.ibShare2SalesFragment.setOnClickListener {
            val myIntent = Intent(Intent.ACTION_SEND)
            myIntent.type = "text/plain"
            val name =
                "Item Details:  \n " +
                        "Invoice No: ${binding.etInvNo.text}\n Customer Name: ${binding.etCustomer.text}\n " +
                        " Total Amount: ${binding.etTotalAmount.text}\n"

            myIntent.putExtra(Intent.EXTRA_TEXT, name);
            startActivity(Intent.createChooser(myIntent, "Share Using"))
        }

        binding.etCustomer.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Scroll RecyclerView to desired position
                binding.recyclerviewCustomer.visibility = View.VISIBLE
            }
            false
        }

        binding.etCustomer.addTextChangedListener(object : TextWatcher {
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
        if (binding.etInvNo.text.toString().isEmpty()) {
            binding.etInvNo.error = "Required"
            isValid = false
        }
        if (binding.etCustomer.text.toString().isEmpty()) {
            binding.etCustomer.error = "Required"
            isValid = false
        }
        if (binding.etContactNo.text.toString().isEmpty()) {
            binding.etContactNo.error = "Required"
            isValid = false
        }
        if (binding.etPaidAmount.text.toString().isEmpty()) {
            binding.etPaidAmount.error = "Required"
            isValid = false
        }
        if (binding.etTotalAmount.text.toString().isEmpty()) {
            binding.etTotalAmount.error = "Required"
            isValid = false
        }
//        if (sharedViewModel.listOfSale.value.isEmpty()) {
//            Toast.makeText(activity, "Item is empty", Toast.LENGTH_SHORT).show()
//            isValid = false
//        }

        return isValid
    }


    private fun convertListToBilledQuantity(): String? {
        val data = sharedViewModel.listOfSale.value
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
        val data = sharedViewModel.listOfSale.value
        var sales = ""
        for (i in data.indices) {
            if (i == 0) {
                sales += "${data[i].itemsName}"
            } else {
                sales += ",${data[i].itemsName}"
            }

        }
        return sales
    }

    private fun convertListToBilledRate(): String? {
        val data = sharedViewModel.listOfSale.value
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
}

@Composable
fun BilledItem(addItems: AddItems) {
    Card(
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth(),
        backgroundColor = Color(0xFFF2F2F2)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = addItems.itemsName,
                    color = Color(0xFF2A424A),
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFamily,
                    fontSize = 15.sp
                )

                Text(
                    text = "${addItems.quantity * addItems.rate}",
                    color = Color(0xFF236885),
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFamily,
                    fontSize = 15.sp
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "item Subtotal",
                    color = Color(0xFF2A424A),
                    fontWeight = FontWeight.Medium,
                    fontFamily = robotoFamily,
                    fontSize = 10.sp
                )

                Text(
                    text = "${addItems.quantity} X ${addItems.rate} = ${addItems.quantity * addItems.rate}",
                    color = Color.LightGray,
                    fontWeight = FontWeight.Normal,
                    fontFamily = robotoFamily,
                    fontSize = 15.sp
                )

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun salePreview() {
    Column {
        BilledItem(addItems =
        AddItems(
            1,
            "coco",
            12,
            "bag",
            100.0,
            0.0,
        ))
    }
}