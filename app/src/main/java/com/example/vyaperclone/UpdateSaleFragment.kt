package com.example.vyaperclone

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.vyaperclone.databinding.FragmentUpdatePurchaseDataBinding
import com.example.vyaperclone.databinding.FragmentUpdateSaleBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateSaleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UpdateSaleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentUpdateSaleBinding

    val addItemsList: List<String> = listOf(Constants.itemsName)
    val addQuantitysList: List<String> = listOf(Constants.quantity)
    val addRateList: List<String> = listOf(Constants.rate)

    val adapter = AddItemsAdapter(addItemsList,addQuantitysList,addRateList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val viewModel: ItemsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateSaleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateSaleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editablePartyName = Editable.Factory.getInstance().newEditable(Constants.PartyName)
        binding.etCustomer.text=editablePartyName
        binding.etCustomer.isEnabled = false

        val editableContact = Editable.Factory.getInstance().newEditable(Constants.CONTACTNO)
        binding.etContactNo.text=editableContact
        binding.etContactNo.isEnabled = false

        val editableTotalAmount = Editable.Factory.getInstance().newEditable(Constants.TotalAmt.toString())
        binding.etTotalAmount.text=editableTotalAmount
        binding.etTotalAmount.isEnabled = false

        val editablePaidAmt = Editable.Factory.getInstance().newEditable(Constants.Received.toString())
        binding.etPaidAmount.text=editablePaidAmt
        binding.etPaidAmount.isEnabled = false

        val editableBillNo = Editable.Factory.getInstance().newEditable(Constants.BillNo.toString())
        binding.etInvNo.text=editableBillNo
        binding.etInvNo.isEnabled = false

        val balanceDueAmt = Constants.TotalAmt - Constants.Received
        val editableBalanceAmt = Editable.Factory.getInstance().newEditable(balanceDueAmt.toString())
        binding.etBalanceDue.text= editableBalanceAmt
        binding.etBalanceDue.isEnabled = false

        binding.btnEdit.setOnClickListener {
            binding.etCustomer.isEnabled = true
            binding.etContactNo.isEnabled = true
            binding.etTotalAmount.isEnabled = true
            binding.etPaidAmount.isEnabled = true
            binding.etBalanceDue.isEnabled = true

            binding.btnEdit.visibility = View.GONE
            binding.btnSave.visibility = View.VISIBLE
        }

        binding.btnSave.setOnClickListener {
            val updatedPartyName = binding.etCustomer.text.toString()
            val updatedContact = binding.etContactNo.text.toString()
            val updatedTotalAmount = binding.etTotalAmount.text.toString().toInt()
            val updatedPaidAmt = binding.etPaidAmount.text.toString().toInt()
            val updatedBillNo = binding.etInvNo.text.toString().toInt()

            val updatedTransaction = TransactionEntity(
                billNo = updatedBillNo,
                Constants.SALE,
                partyName = updatedPartyName,
                billedItemNames = null,
                billedItemRate = null,
                billedItemQuantity = null,
                paidAmt = updatedPaidAmt.toLong(),0,
                total = updatedTotalAmount.toLong(),
                partyContactNumber = updatedContact,
                partyBillingAddress = null
            )

            viewModel.updateTransaction(updatedTransaction)
            findNavController().popBackStack()
        }

        binding.btnDelete.setOnClickListener {
            val billNoToDelete = binding.etInvNo.text.toString().toInt()
            viewModel.deleteTransactionByBillNo(billNoToDelete)
            findNavController().popBackStack()
        }

        val itemNames = Constants.itemsName.split(",").map { it.trim() }
        val quantities = Constants.quantity.split(",").map { it.trim() }
        val rate = Constants.rate.split(",").map { it.trim() }

        adapter.updateData(itemNames,quantities,rate)
        if (Constants.itemsName.isEmpty()){
            binding.recyclerviewCustomer.visibility = View.GONE
        }else{
            binding.recyclerviewCustomer.visibility = View.VISIBLE
        }
        binding.recyclerviewCustomer.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewCustomer.adapter = adapter
    }
}