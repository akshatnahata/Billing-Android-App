package com.example.vyaperclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
class AddItemsAdapter(var addItems: List<String>,var addQuantity: List<String>,var addRate: List<String>) : RecyclerView.Adapter<AddItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.additems_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = addItems[position]
        val currentQuantity = addQuantity[position]
        var currentRate:String = ""
        if (position < addRate.size) {
             currentRate = addRate[position]
        }
        holder.bind(currentItem,currentQuantity, currentRate)
    }

    override fun getItemCount(): Int {
        return addItems.size
    }

        fun updateData(newItems: List<String>,newQuantity:List<String>,newRate:List<String>) {
            addItems = newItems
            addQuantity = newQuantity
            addRate = newRate
            notifyDataSetChanged()
        }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvProductName: TextView = itemView.findViewById(R.id.tvProductName)
        private val tvTotalPrice: TextView = itemView.findViewById(R.id.tvTotalPrice)
        private val tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val tvUnit: TextView = itemView.findViewById(R.id.tvUnit)
        private val tvTotalAmount: TextView = itemView.findViewById(R.id.tvTotalAmount)
        private val tvTotalTax: TextView = itemView.findViewById(R.id.tvTotalTax)
        private val tvRate: TextView = itemView.findViewById(R.id.tvRate)

        fun bind(itemName: String,itemQuantity:String,itemRate:String) {
            tvProductName.text = itemName
            tvQuantity.text = itemQuantity
            tvRate.text = itemRate
            var totalItemAmount: Int =0
            try {
                totalItemAmount = itemQuantity.toInt()*itemRate.toInt()
            } catch (e: NumberFormatException) {
            }
            tvTotalAmount.text = totalItemAmount.toString()
            tvTotalPrice.text = totalItemAmount.toString()
        }
    }
}