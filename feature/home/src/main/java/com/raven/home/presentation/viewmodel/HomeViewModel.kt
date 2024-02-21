package com.raven.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raven.home.utils.Resource
import com.raven.home.domain.models.Article
import com.raven.home.domain.usecases.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _newsState : MutableStateFlow<Resource<List<Article>>> =
        MutableStateFlow(Resource.loading())
    val newsState: StateFlow<Resource<List<Article>>> = _newsState
    private var article = Article()

    fun getNews() {
        viewModelScope.launch {
            getNewsUseCase.execute(Unit).collect {
                _newsState.value = it
            }
        }
    }

    fun saveArticle(arti: Article) {
        article = arti
    }
    fun getArticle() = article
}
