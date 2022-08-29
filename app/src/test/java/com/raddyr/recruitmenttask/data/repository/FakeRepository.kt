package com.raddyr.recruitmenttask.data.repository

import com.raddyr.recruitmenttask.ui.list.Article
import com.raddyr.recruitmenttask.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository() : Repository {

    private var result: Result = Result.SUCCESS

    companion object {
        val data = listOf<Article>(
            Article(
                "Article 1",
                "Some nice title",
                "some nice description",
                "www.someimage.com",
                0,
                "www.onet.pl"
            ),
            Article(
                "Article 2",
                "Some nice title",
                "some nice description",
                "www.someimage.com",
                1,
                "www.onet.pl"
            ),
            Article(
                "Article 3",
                "Some nice title",
                "some nice description",
                "www.someimage.com",
                2,
                "www.onet.pl"
            ),
            Article(
                "Article 4",
                "Some nice title",
                "some nice description",
                "www.someimage.com",
                3,
                "www.onet.pl"
            ),
            Article(
                "Article 5",
                "Some nice title",
                "some nice description",
                "www.someimage.com",
                4,
                "www.onet.pl"
            ),
        )
        val error = Exception()
    }

    enum class Result {
        NO_INTERNET, ERROR, SUCCESS
    }

    fun setResult(value: Result) {
        result = value
    }

    override suspend fun getArticles(): Flow<DataState<List<Article>>> = flow {
        when (result) {
            Result.ERROR -> {
                emit(DataState.Loading)
                emit(DataState.Error(error))
            }
            Result.NO_INTERNET -> {
                emit(DataState.Loading)
                emit(DataState.NoInternet(data))
            }
            Result.SUCCESS -> {
                emit(DataState.Loading)
                emit(DataState.Success(data))
            }
        }
    }
}