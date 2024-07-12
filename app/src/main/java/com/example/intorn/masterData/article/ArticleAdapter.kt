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
            .inflate(R.layout.item_recyclerview_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleItems.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articleItems[position])
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val articleName: TextView = itemView.findViewById(R.id.article_name_textview)
        private val articleId :TextView = itemView.findViewById(R.id.article_Id)
        private val articlePrice: TextView = itemView.findViewById(R.id.article_price)
        fun bind(item: ArticleModel) {
            articleName.text = item.name
            articleId.text = item.id
            articlePrice.text = item.stock
        }
    }
}