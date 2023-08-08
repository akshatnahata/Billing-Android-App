package com.example.vyaperclone

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding
import com.example.vyaperclone.databinding.ItemCategoryLayoutBinding

class ItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: ItemCategoryLayoutBinding
    fun setData(expenseEntity: ExpenseEntity) {
        itemView.apply {
            binding.categoryName.text = expenseEntity.itemName.toString()
            binding.categoryAmt.text = expenseEntity.price.toString()
        }
    }
}