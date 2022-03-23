package com.example.newsapp.presentation.article_detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleDetailBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.utils.onClick

class ArticleDetailFragment : Fragment(R.layout.fragment_article_detail) {

    private lateinit var binding: FragmentArticleDetailBinding
    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        binding = FragmentArticleDetailBinding.bind(view).apply {
            Glide
                .with(requireContext())
                .load(article.urlToImage)
                .into(newsImage)
            newsAuthor.text = article.author
            newsTitle.text = article.title
            newsDescription.text = article.description
            newsPublishedAt.text = article.publishedAt
            newsUrl.text = article.url

            newsUrl.movementMethod = LinkMovementMethod.getInstance()
            newsUrl.onClick {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(article.url)
                startActivity(i)
            }

            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.share_article -> {
                        shareData(article)
                        true
                    }
                    else -> {
                        throw RuntimeException("Unknown menu")
                    }
                }
            }

            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun shareData(article: Article) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, article.url)
        context?.startActivity(
            Intent.createChooser(
                sharingIntent,
                context?.getString(R.string.share_article)
            )
        )
    }
}