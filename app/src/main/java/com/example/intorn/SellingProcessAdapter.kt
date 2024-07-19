package com.example.intorn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.staff.UsersAdapter.UserViewHolder

class SellingProcessAdapter(private val sellingItems: List<SellingProcessModel>):
    RecyclerView.Adapter<SellingProcessAdapter.SellingViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SellingProcessAdapter.SellingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bill_recyclerview, parent, false)
        return SellingViewHolder(view)
    }

    override fun onBindViewHolder(holder: SellingProcessAdapter.SellingViewHolder, position: Int) {
        val item = sellingItems[position]
        holder.bind(sellingItems[position])
    }

    override fun getItemCount(): Int {
        return sellingItems.size
    }

    class SellingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.productName)
        private val price: TextView = itemView.findViewById(R.id.productPrice)
        private val quantity: TextView = itemView.findViewById(R.id.productStock)
        fun bind(item: SellingProcessModel){
            name.text = item.name
            price.text = item.price.toString()
            quantity.text = item.quantity.toString()
        }
    }
}