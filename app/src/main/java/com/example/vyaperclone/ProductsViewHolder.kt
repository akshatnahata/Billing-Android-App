package com.example.vyaperclone

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.vyaperclone.databinding.FragmentAddExpenseBinding
import com.example.vyaperclone.databinding.StockItemListLayoutBinding

class ProductsViewHolder(itemView: View, val listener: OnItemClick) :
    RecyclerView.ViewHolder(itemView) {
    private lateinit var binding: StockItemListLayoutBinding
    fun setData(itemsEntity: ItemsEntity) {
        itemView.apply {
            binding.tvMenuItemName.text = itemsEntity.name
//            tvStockValueNumber.text = itemsEntity.itemCode
            binding.tvSalePriceNumber.text = itemsEntity.salePrice.toString()
            binding.tvPurchasePriceNumber.text = itemsEntity.purchasePrice.toString()
            binding.tvStockQuantityNumber.text = itemsEntity.stock.toString()

            binding.ibShareItem.setOnClickListener {

                listener.onClickShare(itemsEntity)
            }

            binding.ibEDitItem.setOnClickListener {
                listener.onEditClick(itemsEntity)
            }
        }
    }
}