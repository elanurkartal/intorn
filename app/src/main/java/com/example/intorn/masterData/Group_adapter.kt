package com.example.intorn.masterData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.R
import com.example.intorn.staff.UserModel
import com.example.intorn.staff.UsersAdapter.OnClickListener


class Group_adapter(private var groupItems: List<Group_model>) :
    RecyclerView.Adapter<Group_adapter.GroupViewHolder>() {
    private var onClickDeleteItem: ((Group_model) -> Unit)? = null
    private var onClickUpdateItem: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupItems.size
    }
    fun updateItems(arrayList: List<Group_model>) {
        this.groupItems = arrayList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val item = groupItems[position]
        holder.bind(groupItems[position])
        holder.imageViewDelete.setOnClickListener {
            onClickDeleteItem?.invoke(item)
        }
        holder.imageViewUpdate.setOnClickListener {
            onClickUpdateItem?.onClick(position,item)
        }
    }

    class GroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewDelete: ImageView = itemView.findViewById(R.id.imageView2)
        val imageViewUpdate: ImageView = itemView.findViewById(R.id.imageView3)
        private val groupName: TextView = itemView.findViewById(R.id.name_textview)

        fun bind(item:Group_model) {
           groupName.text = item.groupName
        }
    }
    fun deleteItem(article: Group_model) {
        val index = this.groupItems.indexOf(article)
        //this.groupItems.removeAt(index)
        this.notifyItemRemoved(index)
    }
    fun setOnClickDeleteItem(callback: (Group_model) -> Unit) {
        this.onClickDeleteItem = callback
    }
    fun setOnClickUpdateItem(listener: OnClickListener) {
        this.onClickUpdateItem = listener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: Group_model)
    }
}






