package com.example.intorn.masterData.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.R

class ArticleAdapter(private val articleItems: List<ArticleModel>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleItems.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleItems[position])
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val departmentName: TextView = itemView.findViewById(R.id.name_textview)

        fun bind(item: ArticleModel) {
            departmentName.text = item.name+"\n"+item.id+"\n"+item.stock
        }
    }
}