package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vyaperclone.databinding.FragmentAddNewPartyBinding
import com.example.vyaperclone.databinding.FragmentExpensesBinding
import com.example.vyaperclone.databinding.ItemCategoryLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class ExpensesFragment : Fragment() {

    private lateinit var binding: FragmentExpensesBinding

    companion object {
        fun newInstance() = ExpensesFragment()
    }

    private lateinit var viewModel: ExpensesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPagerAdapter = ExpenseViewPagerAdapter(this)

        binding.expenseViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.expenseTabLayout, binding.expenseViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Categories"
                1 -> "Items"
                else -> "Categories"
            }

        }.attach()



        binding.addExpensesBtn.setOnClickListener {
            val action = ExpensesFragmentDirections.actionNavExpensesToAddExpenseFragment()
            findNavController().navigate(action)
        }
    }

    private class ExpenseViewPagerAdapter(fm: ExpensesFragment) :
        FragmentStateAdapter(fm) {

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> return CategoriesFragment.newInstance()
                1 -> return ItemFragment.newInstance()
                else -> CategoriesFragment.newInstance()
            }
        }
    }
}