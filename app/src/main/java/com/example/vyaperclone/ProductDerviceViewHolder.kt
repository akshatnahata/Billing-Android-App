package com.example.vyaperclone

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vyaperclone.databinding.StockItemListLayoutBinding

class ProductDerviceViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val tvMenuItemName: TextView = view.findViewById(R.id.textViewName)
}