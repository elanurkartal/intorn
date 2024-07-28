package com.example.intorn.masterData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.R
import com.example.intorn.staff.UserModel


class Department_adapter(private var departmentItems: List<Department_model>) :
    RecyclerView.Adapter<Department_adapter.DepartmentViewHolder>() {
    private var onClickDeleteItem: ((Department_model) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return DepartmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return departmentItems.size
    }
    fun updateItems(arrayList: List<Department_model>) {
        this.departmentItems = arrayList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val item = departmentItems[position]
        holder.bind(departmentItems[position])
        holder.imageViewDelete.setOnClickListener {
            onClickDeleteItem?.invoke(item)
        }
    }

    class DepartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewDelete: ImageView = itemView.findViewById(R.id.imageView2)
        private val departmentName: TextView = itemView.findViewById(R.id.name_textview)

        fun bind(item:Department_model) {
            departmentName.text = item.department_name
        }
    }
    fun setOnClickDeleteItem(callback: (Department_model) -> Unit) {
        this.onClickDeleteItem = callback
    }
}