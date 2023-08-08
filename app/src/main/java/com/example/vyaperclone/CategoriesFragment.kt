package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding
import com.example.vyaperclone.databinding.FragmentAddNewPartyBinding
import com.example.vyaperclone.databinding.FragmentCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding

    val viewModel: ExpensesViewModel by viewModels()
    private val expenseList = mutableListOf<CategoryModel>()

    val adapter = CategoriesAdapter(expenseList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = CategoriesFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getExpenses()
        viewModel.hashMapList.observe(viewLifecycleOwner, Observer {
            expenseList.clear()
            expenseList.addAll(it)
            adapter.notifyDataSetChanged()
        })
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.categoriesRecyclerView.adapter = adapter

        viewModel.totalMapList.observe(viewLifecycleOwner, Observer {
            binding.tvTotalChangeNumber.text = it.toString()
        })
    }
}