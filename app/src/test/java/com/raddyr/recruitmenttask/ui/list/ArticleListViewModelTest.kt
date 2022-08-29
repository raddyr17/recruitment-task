package com.raddyr.recruitmenttask.ui.list

import com.raddyr.recruitmenttask.data.repository.FakeRepository
import com.raddyr.recruitmenttask.util.DataState
import com.raddyr.recruitmenttask.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleListViewModelTest {

    private lateinit var viewModel: ArticleListViewModel
    private lateinit var repository: FakeRepository

    @get:Rule
    val dispatcher = MainDispatcherRule()

    @Before
    fun setUp() {
        repository = FakeRepository()
        viewModel = ArticleListViewModel(repository)
    }

    @Test
    fun `get data, return success`() = runTest {
        repository.setResult(FakeRepository.Result.SUCCESS)
        viewModel.setEvent(ListEvent.GeArticles)
        val listOfEvents = mutableListOf<DataState<List<Article>>>()
        viewModel.dataState.take(2).toList(listOfEvents)
        Assert.assertEquals(
            listOfEvents,
            listOf(DataState.Loading, (DataState.Success(FakeRepository.data)))
        )
    }

    @Test
    fun `get data, return no internet`() = runTest {
        repository.setResult(FakeRepository.Result.ERROR)
        viewModel.setEvent(ListEvent.GeArticles)
        val listOfEvents = mutableListOf<DataState<List<Article>>>()
        viewModel.dataState.take(2).toList(listOfEvents)
        Assert.assertEquals(
            listOfEvents,
            listOf(DataState.Loading, (DataState.Error(FakeRepository.error)))
        )
    }

    @Test
    fun `get data return error`() = runTest {
        repository.setResult(FakeRepository.Result.NO_INTERNET)
        viewModel.setEvent(ListEvent.GeArticles)
        val listOfEvents = mutableListOf<DataState<List<Article>>>()
        viewModel.dataState.take(2).toList(listOfEvents)
        Assert.assertEquals(
            listOfEvents,
            listOf(DataState.Loading, (DataState.NoInternet(FakeRepository.data)))
        )
    }
}