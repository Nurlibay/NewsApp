package com.example.newsapp.presentation.article_list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.presentation.article_list.adapter.ArticleAdapter
import com.example.newsapp.utils.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.RuntimeException

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel: ArticleViewModel by viewModel()
    private val articleAdapter = ArticleAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view).apply {
            rvArticle.adapter = articleAdapter
        }
        val query = "Apple"
        val from = "2022-03-23"
        val sortBy = "popularity"
        val apiKey = "fbb37f0830a04ebca3022044c9adde69"
        viewModel.getAllArticles(query, from, sortBy, apiKey)
        setUpObserver()
        articleAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_articleFragment_to_articleDetailFragment, bundle)
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is UiState.Loading -> {
                            showProgressBar()
                        }
                        is UiState.Success -> {
                            hideProgressBar()
                            articleAdapter.models = it.list.toMutableList()
                        }
                        is UiState.Error -> {
                            hideProgressBar()
                            Toast.makeText(requireContext(), "Something wrong !", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            hideProgressBar()
                            throw RuntimeException(getString(R.string.unknown_error))
                        }
                    }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}