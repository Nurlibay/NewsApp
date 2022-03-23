package com.example.newsapp.presentation.article_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.domain.repository.MainRepository
import com.example.newsapp.utils.UiState
import com.example.newsapp.utils.isConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    fun getAllArticles(query: String, from: String, sortBy: String, apiKey: String) {
        if (!isConnected()) {
            _uiState.value = UiState.NetworkError("Интернет не подключен!")
            return
        }
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = mainRepository.getAllArticles(query, from, sortBy, apiKey)
                _uiState.value = UiState.Success(result.articles)
            } catch (error: Exception) {
                _uiState.value = UiState.Error(error.localizedMessage!!)
            }
        }
    }
}