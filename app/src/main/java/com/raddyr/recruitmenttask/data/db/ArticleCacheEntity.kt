package com.raddyr.recruitmenttask.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleCacheEntity(
    @PrimaryKey val orderId: Int? = null,
    val title: String?,
    val description: String?,
    val image_url: String?,
    val modificationDate: String?,
    val url: String?
)