package com.raddyr.recruitmenttask.data.db

import com.raddyr.recruitmenttask.ui.list.Article
import org.junit.Assert.assertEquals
import org.junit.Test


class CacheMapperTest {

    private var cacheMapper = CacheMapper()

    @Test
    fun `mapFromEntity correct url test`() {
        val article = Article(
            "Article 1",
            "Some nice description",
            "www.someimage.com",
            "2022-07-01",
            0,
            "https://www.onet.pl"
        )

        val networkEntity = ArticleCacheEntity(
            0,
            "Article 1",
            "Some nice description",
            "www.someimage.com",
            "2022-07-01",
            "https://www.onet.pl"
        )
        assertEquals(article, cacheMapper.mapFromEntity(networkEntity))
    }

    @Test
    fun `mapToEntity test`() {
        val article = Article(
            "Article 1",
            "Some nice description",
            "www.someimage.com",
            "2022-07-01",
            0,
            "https://www.onet.pl"
        )

        val networkEntity = ArticleCacheEntity(
            0,
            "Article 1",
            "Some nice description",
            "www.someimage.com",
            "2022-07-01",
            "https://www.onet.pl"
        )
        assertEquals(networkEntity, cacheMapper.mapToEntity(article))
    }
}