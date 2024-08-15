package com.example.intorn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReportAdapter(private var reportItem: List<ReportModel>):
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportAdapter.ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportAdapter.ReportViewHolder, position: Int) {
        val item = reportItem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return reportItem.size
    }

    inner class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerCount: TextView = itemView.findViewById(R.id.customerCount)
        val productCount: TextView = itemView.findViewById(R.id.productCount)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val type: TextView = itemView.findViewById(R.id.paymentType)
        fun bind(item: ReportModel) {
            customerCount.text = "Müşteri sayısı:"+item.customerCount.toString()
            productCount.text = "Miktar:"+item.productCount.toString()
            amount.text = "Tutar:"+item.amount.toString()
            type.text = "Payment Type:"+item.type
        }
    }
}