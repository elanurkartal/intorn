package com.example.intorn.masterData.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intorn.R
import com.example.intorn.masterData.Group_model
import com.example.intorn.staff.UserModel

class ArticleAdapter(private var articleItems: List<ArticleModel>) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    private var onClickDeleteItem: ((ArticleModel) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recyclerview_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleItems.size
    }

    fun updateItems(arrayList: List<ArticleModel>) {
        this.articleItems = arrayList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = articleItems[position]
        holder.bind(articleItems[position])
        holder.imageViewDelete.setOnClickListener {
            onClickDeleteItem?.invoke(item)
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val articleName: TextView = itemView.findViewById(R.id.article_name_textview)
        private val articleId :TextView = itemView.findViewById(R.id.article_Id)
        private val articlePrice: TextView = itemView.findViewById(R.id.article_price)
        val imageViewDelete: ImageView = itemView.findViewById(R.id.imageView2)

        fun bind(item: ArticleModel) {
            articleName.text = item.name
            articleId.text = item.id
            articlePrice.text = item.stock
        }
    }
    fun setOnClickDeleteItem(callback: (ArticleModel) -> Unit) {
        this.onClickDeleteItem = callback
    }
}