package com.example.intorn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class BillAlertAdapter(private val billItem: List<BillAlertModel>):
    RecyclerView.Adapter<BillAlertAdapter.BillViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BillAlertAdapter.BillViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bill_alert_recyclerview, parent, false)
        return BillViewHolder(view)
    }

    override fun onBindViewHolder(holder: BillAlertAdapter.BillViewHolder, position: Int) {
        val item = billItem[position]
        holder.bind(item)    }

    override fun getItemCount(): Int {
        return billItem.size
    }

    class BillViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.billName)
        private val  amount: TextView = itemView.findViewById(R.id.billAmount)
        fun bind(itemModel: BillAlertModel){
            name.text = itemModel.name
            amount.text = itemModel.amount.toString()
        }
    }
}
