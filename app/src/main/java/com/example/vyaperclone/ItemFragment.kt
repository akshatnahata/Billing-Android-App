package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentExpensesBinding
import com.example.vyaperclone.databinding.FragmentItemsBinding


class ItemFragment : Fragment() {

    private lateinit var binding: FragmentItemsBinding

    val viewModel: ExpensesViewModel by viewModels()
    private val expenseList = mutableListOf<ExpenseEntity>()

    val adapter = ItemsAdapter(expenseList)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    companion object {

        fun newInstance() = ItemFragment()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getExpenseItems().observe(this, Observer {
            expenseList.clear()
            expenseList.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.itemsRecyclerView.adapter = adapter

    }

}