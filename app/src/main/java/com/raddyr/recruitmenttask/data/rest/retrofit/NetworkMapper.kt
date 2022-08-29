package com.raddyr.recruitmenttask.data.rest.retrofit

import com.raddyr.recruitmenttask.data.rest.model.ArticleNetworkEntity
import com.raddyr.recruitmenttask.ui.list.Article
import com.raddyr.recruitmenttask.util.EntityMapper
import com.raddyr.recruitmenttask.util.extensions.formatDate
import com.raddyr.recruitmenttask.util.extensions.getUrlFromString
import com.raddyr.recruitmenttask.util.extensions.removeUrlFromString
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<ArticleNetworkEntity, Article> {

    override fun mapFromEntity(entity: ArticleNetworkEntity): Article {
        return Article(
            title = entity.title,
            image_url = entity.image_url,
            modificationDate = entity.modificationDate?.formatDate(),
            orderId = entity.orderId,
            url = entity.description?.getUrlFromString(),
            description = entity.description?.removeUrlFromString(),
        )
    }

    override fun mapToEntity(domainModel: Article) = ArticleNetworkEntity(
        title = domainModel.title,
        description = domainModel.description,
        image_url = domainModel.image_url,
        modificationDate = domainModel.modificationDate,
        orderId = domainModel.orderId
    )

    fun mapFromEntityList(entities: List<ArticleNetworkEntity>) =
        entities.map { mapFromEntity(it) }
}