package com.example.intorn.masterData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.R


class Department_adapter(private val departmentItems: List<Department_model>) :
    RecyclerView.Adapter<Department_adapter.DepartmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return DepartmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return departmentItems.size
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        holder.bind(departmentItems[position])
    }

    class DepartmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val departmentName: TextView = itemView.findViewById(R.id.name_textview)

        fun bind(item:Department_model) {
            departmentName.text = item.department_name
        }
    }
}