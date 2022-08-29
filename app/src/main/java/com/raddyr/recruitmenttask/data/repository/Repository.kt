package com.raddyr.recruitmenttask.data.repository

import com.raddyr.recruitmenttask.ui.list.Article
import com.raddyr.recruitmenttask.util.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getArticles(): Flow<DataState<List<Article>>>
}