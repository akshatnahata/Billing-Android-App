package com.example.vyaperclone

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vyaperclone.databinding.ItemCategoryLayoutBinding

class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var binding:ItemCategoryLayoutBinding
    fun setData(expenseEntity: CategoryModel) {
        itemView.apply {
            binding.categoryName.text = expenseEntity.categoryName
            binding.categoryAmt.text = expenseEntity.totalAmount.toString()
        }
    }
}