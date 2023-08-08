package com.example.vyaperclone

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vyaperclone.databinding.FragmentAddNewPartyBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewPartyFragment : Fragment() {

    private lateinit var binding: FragmentAddNewPartyBinding

    private val partiesViewModel: PartiesViewModel by viewModels()
    private val itemsViewModel: ItemsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNewPartyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewPageAdapter = PartyViewPagerAdapterNew(this)
        binding.viewPagerAddNewParty.adapter = viewPageAdapter

        TabLayoutMediator(binding.tabLayoutAddNewParty, binding.viewPagerAddNewParty) { tab, position ->
            tab.text = when (position) {
                0 -> "Addresses"
                else -> "GST"
            }
        }.attach()


        var partyName: String = ""
        var contactNo: String = ""
        var partyType: String = ""
        var receiveAmount: Long = 0
        var paidAmount: Long = 0

        binding.btnSave.setOnClickListener {
            if (isDataValid()) {
                val partyName = binding.partyNameEditText.text.toString()
                val existingParty = Constants.existPartyName.find {it.partyName.equals(partyName.trim(), ignoreCase = true)}
                if (existingParty != null) {
                    Toast.makeText(requireContext(), "This Party name already exist.Create a new party name", Toast.LENGTH_LONG).show()
                } else {
                    val partyType = if (binding.receiveRadioButton.isChecked) "Receive" else "Pay"
                    val contactNo = binding.contactNumberEditText.text.toString()
                    val inputString = binding.openingBalanceEditText.text.toString()
                    val amount = inputString.toLongOrNull() ?: 0L

                    if (binding.receiveRadioButton.isChecked){
                         receiveAmount = inputString.toLongOrNull() ?: 0L
                    }else{
                         paidAmount = inputString.toLongOrNull() ?: 0L
                    }
                    val partyEntity = TransactionEntity(billNo = null,type = partyType,partyName = partyName,billedItemNames = null,billedItemRate = null,
                    billedItemQuantity = null,paidAmt = null, received = null,total = amount, partyContactNumber = contactNo,partyBillingAddress = null)

                    partiesViewModel.addParty(partyEntity)
                    activity?.onBackPressed()
                }
            }
        }

    }

    private fun isDataValid(): Boolean {
        var isValid = true
        if ( binding.partyNameEditText.text.toString().isEmpty()) {
            binding.partyNameEditText.error = "Required"
            isValid = false
        }

        if (binding.contactNumberEditText.text.toString().isEmpty()|| binding.contactNumberEditText.text.toString().length != 10) {
            binding.contactNumberEditText.error = "Required"
            isValid = false
        }
        if (binding.openingBalanceEditText.text.toString().isEmpty()) {
            binding.openingBalanceEditText.error = "Required"
            isValid = false
        }


        return isValid
    }
}

private class PartyViewPagerAdapterNew(fm: AddNewPartyFragment) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> return AddressesFragment.newInstance()
            else -> GstFragment.newInstance()
        }
    }
}