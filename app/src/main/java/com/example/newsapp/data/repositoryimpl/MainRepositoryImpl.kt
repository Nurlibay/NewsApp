package com.example.newsapp.data.repositoryimpl

import com.example.newsapp.data.remote.ApiInterface
import com.example.newsapp.domain.model.response.Result
import com.example.newsapp.domain.repository.MainRepository
import java.lang.RuntimeException

class MainRepositoryImpl constructor(
    private val api: ApiInterface
) : MainRepository {

    override suspend fun getAllArticles(
        query: String,
        from: String,
        sortBy: String,
        apiKey: String
    ): Result {
        return api.getAllArticles(query, from, sortBy, apiKey).body() ?: throw RuntimeException("Unknown Exception")
    }
}