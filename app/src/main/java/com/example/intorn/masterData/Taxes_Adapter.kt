package com.example.intorn.masterData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.R
import com.example.intorn.masterData.Group_adapter.GroupViewHolder

class Taxes_Adapter(private val taxesItem: List<Taxes_Model>):
    RecyclerView.Adapter<Taxes_Adapter.TaxesViewHolder>() {

    private var onClickDeleteItem: ((Taxes_Model) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Taxes_Adapter.TaxesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return TaxesViewHolder(view)
    }

    override fun onBindViewHolder(holder: Taxes_Adapter.TaxesViewHolder, position: Int) {
        val item = taxesItem[position]
        holder.bind(taxesItem[position])
        holder.imageViewDelete.setOnClickListener {
            onClickDeleteItem?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return taxesItem.size
    }

    class TaxesViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageViewDelete: ImageView = itemView.findViewById(R.id.imageView2)

        private val taxesName: TextView = itemView.findViewById(R.id.name_textview)

        fun bind(item:Taxes_Model) {
            taxesName.text = item.taxesName
        }
    }
    fun setOnClickDeleteItem(callback: (Taxes_Model) -> Unit) {
        this.onClickDeleteItem = callback
    }
}