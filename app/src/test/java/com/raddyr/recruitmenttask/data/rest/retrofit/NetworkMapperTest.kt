package com.raddyr.recruitmenttask.data.rest.retrofit

import com.raddyr.recruitmenttask.data.rest.model.ArticleNetworkEntity
import com.raddyr.recruitmenttask.ui.list.Article
import org.junit.Assert
import org.junit.Test

class NetworkMapperTest {

    private var networkMapper = NetworkMapper()

    @Test
    fun `mapFromEntity correct url test`() {
        val article = Article(
            "Article 1",
            "Some nice title",
            "www.someimage.com",
            "01/07/2022",
            0,
            "https://www.onet.pl"
        )

        val networkEntity = ArticleNetworkEntity(
            "Article 1",
            "Some nice titlehttps://www.onet.pl",
            "www.someimage.com",
            "2022-07-01",
            0
        )
        Assert.assertEquals(article, networkMapper.mapFromEntity(networkEntity))
    }

    @Test
    fun `mapFromEntity incorrect url test`() {
        val article = Article(
            "Article 1",
            "Some nice titlehts://www.onet.pl",
            "www.someimage.com",
            "01/07/2022",
            0,
            null
        )

        val networkEntity = ArticleNetworkEntity(
            "Article 1",
            "Some nice titlehts://www.onet.pl",
            "www.someimage.com",
            "2022-07-01",
            0
        )
        Assert.assertEquals(article, networkMapper.mapFromEntity(networkEntity))
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

        val networkEntity = ArticleNetworkEntity(
            "Article 1",
            "Some nice description",
            "www.someimage.com",
            "2022-07-01",
            0
        )
        Assert.assertEquals(networkEntity, networkMapper.mapToEntity(article))
    }
}