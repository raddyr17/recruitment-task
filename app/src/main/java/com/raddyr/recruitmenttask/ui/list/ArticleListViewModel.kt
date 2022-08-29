package com.raddyr.recruitmenttask.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raddyr.recruitmenttask.data.repository.Repository
import com.raddyr.recruitmenttask.ui.list.ListEvent.GeArticles
import com.raddyr.recruitmenttask.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _dataState = MutableStateFlow<DataState<List<Article>>>(DataState.Loading)
    val dataState: StateFlow<DataState<List<Article>>>
        get() = _dataState

    init {
        setEvent(GeArticles)
    }

    fun setEvent(event: ListEvent) {
        viewModelScope.launch {
            when (event) {
                is GeArticles -> {
                    repository.getArticles().onEach { dataState ->
                        _dataState.value = dataState
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class ListEvent {
    object GeArticles : ListEvent()
}