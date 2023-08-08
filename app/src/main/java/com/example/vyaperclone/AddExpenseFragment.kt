package com.example.vyaperclone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vyaperclone.databinding.ActivityAddPurchaseBinding
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddExpenseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddExpenseFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseBinding

    private val viewModel: AddExpenseViewModel by viewModels()
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_line_item , menu)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var qty: Int = 0
        var price: Int = 0

        binding.etQty.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                qty = binding.etQty.text.toString().toInt()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                price = binding.etPrice.text.toString().toInt()
                val amt = (qty * price).toString()
                binding.etTotalAmt.setText(amt)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.btnAddExpenseSave.setOnClickListener {

            if (isDataValid()){

                val expenseEntity = ExpenseEntity(
                    binding.etExpenseCategory.text.toString(),
                    binding.etItemName.text.toString(),
                    binding.etQty.text.toString().toInt(),
                    binding.etPrice.text.toString().toInt(),
                    binding.etTotalAmt.text.toString().toInt()
                )
                viewModel.addExpense(expenseEntity)
//                val action = AddExpenseFragmentDirections.actionAddExpenseFragmentToNavExpenses()
//                findNavController().navigate(action)
            }

        }

        binding.btnAddExpenseSaveAndNew.setOnClickListener {
            Toast.makeText(
                activity,
                "Item / services name cannot be left empty",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    private fun isDataValid(): Boolean {
        var isValid = true
        if (binding.etItemName.text.toString().isEmpty()) {
            binding.etItemName.error = "Required"
            isValid = false
        }
        if (binding.etQty.text.toString().isEmpty()) {
            binding.etQty.error = "Required"
            isValid = false
        }
        if (binding.etPrice.text.toString().isEmpty()) {
            binding.etPrice.error = "Required"
            isValid = false
        }
        if (binding.etTotalAmt.text.toString().isEmpty()) {
            binding.etTotalAmt.error = "required"
            isValid = false
        }
        if (binding.etExpenseCategory.text.toString().isEmpty()) {
            binding.etExpenseCategory.error = "required"
            isValid = false
        }


        return isValid
    }

    companion object {

        fun newInstance() = AddExpenseFragment()
    }
}