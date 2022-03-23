package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.response.Result

interface MainRepository {

    suspend fun getAllArticles(query: String, from: String, sortBy: String, apiKey: String): Result

}