package com.raven.home.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.raven.home.data.remote.models.Resource
import com.raven.home.domain.models.Article
import com.raven.home.domain.usecases.GetNewsUseCase
import com.raven.home.utils.isOnline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    fun getNews() {
        viewModelScope.launch {
            getNewsUseCase.execute(Unit).collect {
                _newsState.value = it
            }
        }
    }
}
