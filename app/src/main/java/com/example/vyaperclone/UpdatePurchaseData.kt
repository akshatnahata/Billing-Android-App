package com.example.vyaperclone

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentUpdatePurchaseDataBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePurchaseData.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UpdatePurchaseData : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentUpdatePurchaseDataBinding

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
        binding = FragmentUpdatePurchaseDataBinding.inflate(inflater, container, false)
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdatePurchaseData().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editablePartyName = Editable.Factory.getInstance().newEditable(Constants.PartyName)
        binding.etParty.text = editablePartyName
        binding.etParty.isEnabled = false

        val editableContactNo = Editable.Factory.getInstance().newEditable(Constants.CONTACTNO)
        binding.etContactNo.text = editableContactNo
        binding.etContactNo.isEnabled = false

        val editableTotalAmount =
            Editable.Factory.getInstance().newEditable(Constants.TotalAmt.toString())
        binding.etTotal.text = editableTotalAmount
        binding.etTotal.isEnabled = false

        val editablePaidAmt =
            Editable.Factory.getInstance().newEditable(Constants.PaidAmt.toString())
        binding.etPaid.text = editablePaidAmt
        binding.etPaid.isEnabled = false

        val editableBillNo = Editable.Factory.getInstance().newEditable(Constants.BillNo.toString())
        binding.etBillNo.text = editableBillNo
        binding.etBillNo.isEnabled = false

        val balanceDueAmt = Constants.TotalAmt - Constants.PaidAmt
        val editableBalanceAmt =
            Editable.Factory.getInstance().newEditable(balanceDueAmt.toString())
        binding.balanceDue.text = editableBalanceAmt
        binding.balanceDue.isEnabled = false

        binding.btnEdit.setOnClickListener {
            binding.etParty.isEnabled = true
            binding.etContactNo.isEnabled = true
            binding.etTotal.isEnabled = true
            binding.etPaid.isEnabled = true
            binding.balanceDue.isEnabled = true

            binding.btnEdit.visibility = View.GONE
            binding.btnSave.visibility = View.VISIBLE
        }

        binding.btnSave.setOnClickListener {
            val updatedPartyName = binding.etParty.text.toString()
            val updatedContactNo = binding.etContactNo.text.toString()
            val updatedTotalAmount = binding.etTotal.text.toString().toInt()
            val updatedPaidAmt = binding.etPaid.text.toString().toInt()
            val updatedBillNo = binding.etBillNo.text.toString().toInt()

            val updatedTransaction = TransactionEntity(
                billNo = updatedBillNo,
                Constants.PURCHASE,
                partyName = updatedPartyName,
                billedItemNames = null,
                billedItemRate = null,
                billedItemQuantity = null,
                paidAmt = updatedPaidAmt.toLong(),0,
                total = updatedTotalAmount.toLong(),
                partyContactNumber = updatedContactNo,
                partyBillingAddress = null
            )

            viewModel.updateTransaction(updatedTransaction)
            findNavController().popBackStack()
        }

        binding.btnDelete.setOnClickListener {
            val billNoToDelete = binding.etBillNo.text.toString().toInt()
            viewModel.deleteTransactionByBillNo(billNoToDelete)
            findNavController().popBackStack()
        }

        val itemNames = Constants.itemsName.split(",").map { it.trim() }
        val quantities = Constants.quantity.split(",").map { it.trim() }
        val rate = Constants.rate.split(",").map { it.trim() }

        adapter.updateData(itemNames,quantities,rate)
        if (Constants.itemsName.isEmpty()){
            binding.recyclerviewName.visibility = View.GONE
        }else{
            binding.recyclerviewName.visibility = View.VISIBLE
        }
        binding.recyclerviewName.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewName.adapter = adapter

    }

}