package com.example.intorn

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.masterData.Department_model
import com.example.intorn.staff.UsersAdapter.UserViewHolder

class SellingProcessAdapter(private var sellingItems: List<SellingProcessDtoModel>):
    RecyclerView.Adapter<SellingProcessAdapter.SellingViewHolder>(){
    private var onClickListener:SellingProcessAdapter.OnClickListener? = null


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
        holder.imageViewDelete.setOnClickListener {
            onClickListener?.onClick(position,item)
        }
    }
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    // Interface for the click listener
    interface OnClickListener {
        fun onClick(position: Int, model: SellingProcessDtoModel)
    }

    override fun getItemCount(): Int {
        return sellingItems.size
    }

    class SellingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewDelete: ImageView = itemView.findViewById(R.id.removeProduct)
        private val name: TextView = itemView.findViewById(R.id.productName)
        private val price: TextView = itemView.findViewById(R.id.productPrice)
        private val quantity: TextView = itemView.findViewById(R.id.productStock)
        fun bind(item: SellingProcessDtoModel){
            name.text = item.name
            price.text = item.price.toString()
            quantity.text = item.quantity.toString()
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(arrayList: List<SellingProcessDtoModel>){
        this.sellingItems = arrayList
        notifyDataSetChanged()
    }

}