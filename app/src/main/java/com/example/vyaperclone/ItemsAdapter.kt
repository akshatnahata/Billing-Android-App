package com.example.vyaperclone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ItemsAdapter(private val itemsList: List<ExpenseEntity>) :
    RecyclerView.Adapter<ItemsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_layout, parent, false)
        return ItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val expenseEntity = itemsList[position]
        holder.setData(expenseEntity)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}