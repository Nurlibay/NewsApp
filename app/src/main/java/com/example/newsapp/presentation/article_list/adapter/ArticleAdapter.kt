package com.example.newsapp.presentation.article_list.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.utils.onClick

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun populateModel(article: Article) {
            binding.apply {
                Glide
                    .with(binding.root.context)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.news_image)
                    .into(newsImage)
                newsAuthor.text = article.author
                newsTitle.text = article.title
                newsPublishedAt.text = article.publishedAt
                cardNews.onClick {
                    onItemClick.invoke(article)
                }
            }
        }
    }

    var models: MutableList<Article> = mutableListOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            models.clear()
            field = value
            notifyDataSetChanged()
        }

    private var onItemClick: (article: Article) -> Unit = {}
    fun setOnItemClickListener(onItemClick: (article: Article) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun getItemCount() = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.populateModel(models[position])
    }
}