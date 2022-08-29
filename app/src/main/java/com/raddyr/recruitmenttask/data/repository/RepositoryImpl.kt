package com.raddyr.recruitmenttask.data.repository

import com.raddyr.recruitmenttask.data.db.CacheMapper
import com.raddyr.recruitmenttask.data.db.ArticleDao
import com.raddyr.recruitmenttask.data.rest.retrofit.NetworkMapper
import com.raddyr.recruitmenttask.data.rest.retrofit.ServicesApi
import com.raddyr.recruitmenttask.util.DataState
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: ArticleDao,
    private val servicesApi: ServicesApi,
    private val networkMapper: NetworkMapper,
    private val cacheMapper: CacheMapper
): Repository {
    override suspend fun getArticles() = flow {
        emit(DataState.Loading)
        try {
            val networkItems = servicesApi.getArticles()
            val items = networkMapper.mapFromEntityList(networkItems)
            for (item in items) {
                dao.insertArticle(cacheMapper.mapToEntity(item))
            }
            val cachedItems = dao.getArticles()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedItems)))
        } catch (e: Exception) {
            runCatching {
                dao.getArticles()
            }.onSuccess {
                emit(DataState.NoInternet(cacheMapper.mapFromEntityList(it)))
            }.onFailure {
                emit(DataState.Error(it))
            }
        }
    }
}