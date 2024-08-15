package com.example.intorn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ZReportAdapter(private var reportItem: MutableList<ZReportModel>):
    RecyclerView.Adapter<ZReportAdapter.ReportViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ZReportAdapter.ReportViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_z_report, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ZReportAdapter.ReportViewHolder, position: Int) {
        val item = reportItem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return reportItem.size
    }

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: TextView = itemView.findViewById(R.id.paymentType)
        val productNumber: TextView = itemView.findViewById(R.id.productNumber)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
        val priceSell: TextView = itemView.findViewById(R.id.priceSell)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val userID: TextView = itemView.findViewById(R.id.userID)

        fun bind(item: ZReportModel) {
            type.text = "Payment Type:"+item.type
            productNumber.text = "Product Count:"+item.productNumber
            quantity.text = "Quantity:"+item.quantity
            priceSell.text = "Price Sell:"+item.priceSell
            amount.text = "Amount:"+item.amount
            userID.text = "UserID:"+item.userID
        }
    }
}