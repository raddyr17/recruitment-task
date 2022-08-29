package com.raddyr.recruitmenttask.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
@ExperimentalCoroutinesApi
class ArticleDaoTest {

    private lateinit var database: ArticleDatabase
    private lateinit var dao: ArticleDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.articleDao()
    }

    fun teardown() {
        database.close()
    }

    @Test
    fun testInsertArticle() = runTest {
        val article = ArticleCacheEntity(
            0,
            "Some nice title",
            "some nice description",
            "www.someimage.com",
            modificationDate = "1972-11-21",
            "www.onet.pl"
        )
        dao.insertArticle(article)

        val allArticle = dao.getArticles()
        assert(allArticle.contains(article))
    }

    @Test
    fun testGetArticlesAsc() = runTest {
        val article1 = ArticleCacheEntity(
            0,
            "Some nice title",
            "some nice description",
            "www.someimage.com",
            modificationDate = "1972-11-21",
            "www.onet.pl"
        )
        val article2 = ArticleCacheEntity(
            1,
            "Some nice title",
            "some nice description",
            "www.someimage.com",
            modificationDate = "1972-11-21",
            "www.onet.pl"
        )
        val article3 = ArticleCacheEntity(
            2,
            "Some nice title",
            "some nice description",
            "www.someimage.com",
            modificationDate = "1972-11-21",
            "www.onet.pl"
        )
        dao.insertArticle(article3)
        dao.insertArticle(article1)
        dao.insertArticle(article2)

        val allArticle = dao.getArticles()
        allArticle.forEachIndexed { index, articleCacheEntity ->
            //check if order is correct
            assert(articleCacheEntity.orderId == index)
        }
    }
}