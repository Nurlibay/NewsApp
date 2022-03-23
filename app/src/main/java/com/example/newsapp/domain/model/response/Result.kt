package com.example.newsapp.domain.model.response

import com.example.newsapp.domain.model.Article
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("status")
    val status: String = "",
    @SerializedName("totalResults")
    val totalResults: Int = 0,
    @SerializedName("articles")
    val articles: List<Article> = emptyList()
)