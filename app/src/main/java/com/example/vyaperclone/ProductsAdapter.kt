package com.example.vyaperclone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ProductsAdapter(
    private val productList: List<ItemsEntity>,
    private val listener: OnItemClick
) :
    RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stock_item_list_layout, parent, false)
        return ProductsViewHolder(view,listener)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = productList[position]
        holder.setData(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}