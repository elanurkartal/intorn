package com.example.intorn.staff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.ItemAdapter
import com.example.intorn.ItemModel
import com.example.intorn.MainActivity
import com.example.intorn.R
import com.example.intorn.masterData.Group_model

class UsersAdapter(private val userItems: List<UserModel>):
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    private var onClickDeleteItem: ((UserModel) -> Unit)? = null
    private var onClickUpdateItem: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_recyclerview, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = userItems[position]
        holder.bind(userItems[position])
        holder.imageViewDelete.setOnClickListener {
            onClickDeleteItem?.invoke(item)
        }
        holder.imageViewUpdate.setOnClickListener {
            onClickUpdateItem?.onClick(position,item)
        }
    }

    override fun getItemCount(): Int {
        return userItems.size
    }


    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val usersName: TextView = itemView.findViewById(R.id.usersName)
        private val usersType: TextView = itemView.findViewById(R.id.usersType)
        val imageViewDelete: ImageView = itemView.findViewById(R.id.imageView_delete)
        val imageViewUpdate: ImageView = itemView.findViewById(R.id.imageView3)
        fun bind(item: UserModel) {
            usersName.text = item.userName
            usersType.text = item.userType
        }
    }
    fun setOnClickDeleteItem(callback: (UserModel) -> Unit) {
        this.onClickDeleteItem = callback
    }
    fun setOnClickUpdateItem(listener: OnClickListener) {
        this.onClickUpdateItem = listener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: UserModel)
    }
}
