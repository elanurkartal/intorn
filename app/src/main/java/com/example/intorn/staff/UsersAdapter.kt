package com.example.intorn.staff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.R

class UsersAdapter(private val userItems: List<UserModel>):
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.users_recyclerview, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userItems[position])
    }

    override fun getItemCount(): Int {
        return userItems.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val usersName: TextView = itemView.findViewById(R.id.usersName)
        private val usersType: TextView = itemView.findViewById(R.id.usersType)
        fun bind(item: UserModel) {
            usersName.text = item.userName
            usersType.text = item.userType
        }
    }
}
