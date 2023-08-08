package com.example.vyaperclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding
import com.example.vyaperclone.databinding.FragmentAddProductBinding
import com.example.vyaperclone.databinding.FragmentMenuItemsBinding
import com.google.android.material.tabs.TabLayoutMediator

class MenuItemsFragment : Fragment() {

    private lateinit var binding: FragmentMenuItemsBinding

    companion object {
        fun newInstance() = MenuItemsFragment()
    }

    private lateinit var viewModel: MenuItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuItemsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MenuItemsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewPagerAdapter = MenuItemsViewPagerAdapter(this)

        binding.menuItemsViewPager.adapter = viewPagerAdapter

        TabLayoutMediator( binding.menuItemsTabLayout,  binding.menuItemsViewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Products"
                1 -> "Services"
                2 -> "Units"
                else -> "Products"
            }

        }.attach()
    }

    private class MenuItemsViewPagerAdapter(fm: MenuItemsFragment) :
        FragmentStateAdapter(fm) {

        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> return ProductFragment.newInstance()
                1 -> return ServicesFragment.newInstance()
                2 -> return UnitsFragment.newInstance()
                else -> ProductFragment.newInstance()
            }
        }
    }
}