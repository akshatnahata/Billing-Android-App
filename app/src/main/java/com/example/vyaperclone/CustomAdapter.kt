package com.example.vyaperclone

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.security.AccessController.getContext


class CustomAdapter(private var transactions: List<TransactionEntity>,private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<SalesReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.example.vyaperclone.R.layout.item_sales_report,parent,false)
        return SalesReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: SalesReportViewHolder, position: Int) {
        holder.mPartyName.text = transactions[position].partyName.toString()
        holder.mId.visibility = View.GONE
        holder.mAmount.visibility = View.GONE
        holder.mBillNo.visibility = View.GONE
        holder.mReceivedAmnt.visibility = View.GONE
        holder.mTotal.visibility = View.GONE

        holder.itemView.setOnClickListener {
            onItemClick(transactions[position].partyName.toString())
            holder.itemView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun filterList(filterdNames: List<TransactionEntity>) {
        transactions = filterdNames
        notifyDataSetChanged()
    }

}

