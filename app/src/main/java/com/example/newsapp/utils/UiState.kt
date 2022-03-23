package com.example.newsapp.utils

import com.example.newsapp.domain.model.Article

sealed class UiState {
    object Empty: UiState()
    object Loading: UiState()
    data class Success(val list: List<Article>): UiState()
    data class Error(val msg: String): UiState()
    data class NetworkError(val msg: String?): UiState()
}