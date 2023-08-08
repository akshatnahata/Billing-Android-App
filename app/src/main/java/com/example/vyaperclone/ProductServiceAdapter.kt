package com.example.vyaperclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductServiceAdapter(private var itemsEntity: List<ItemsEntity>,private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<ProductDerviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDerviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout,parent,false)
        return ProductDerviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductDerviceViewHolder, position: Int) {
        holder.tvMenuItemName.text = itemsEntity[position].name.toString()

        holder.itemView.setOnClickListener {
            onItemClick(itemsEntity[position].name.toString())
            holder.itemView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return itemsEntity.size
    }

    fun filterList(filterdNames: List<ItemsEntity>) {
        itemsEntity = filterdNames
        notifyDataSetChanged()
    }

}