package com.raddyr.recruitmenttask.data.db

import com.raddyr.recruitmenttask.ui.list.Article
import com.raddyr.recruitmenttask.util.EntityMapper
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<ArticleCacheEntity, Article> {

    override fun mapFromEntity(entity: ArticleCacheEntity): Article {
        return Article(
            title = entity.title,
            image_url = entity.image_url,
            modificationDate = entity.modificationDate,
            orderId = entity.orderId,
            description = entity.description,
            url = entity.url
        )
    }

    override fun mapToEntity(domainModel: Article) = ArticleCacheEntity(
        title = domainModel.title,
        description = domainModel.description,
        image_url = domainModel.image_url,
        modificationDate = domainModel.modificationDate,
        orderId = domainModel.orderId,
        url = domainModel.url
    )


    fun mapFromEntityList(entities: List<ArticleCacheEntity>) =
        entities.map { mapFromEntity(it) }
}
