package com.example.intorn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.ItemAdapter.OnClickListener
import com.example.intorn.staff.UsersAdapter.UserViewHolder

class HomeAdapter(private var productItems: List<HomeModel>):
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>(){

    private var onClickListener:HomeAdapter.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_products_recyclerview, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val item = productItems[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position,item)
        }
    }
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    // Interface for the click listener
    interface OnClickListener {
        fun onClick(position: Int, model: HomeModel)
    }

    override fun getItemCount(): Int {
        return productItems.size
    }
    fun updateItems(arrayList: ArrayList<HomeModel>) {
        this.productItems = arrayList
        notifyDataSetChanged()
    }

    class HomeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.productsName)
        private val  price: TextView = itemView.findViewById(R.id.productsPrice)
        private val stock: TextView = itemView.findViewById(R.id.productsStock)

        fun bind(item: HomeModel){
            name.text = item.name
            price.text ="PRICE:  ${item.price}"
            stock.text = "STOCK: ${item.stock}"
        }

    }
}
