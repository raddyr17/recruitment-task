package com.raddyr.recruitmenttask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(itemDatabaseEntityDbEntity: ArticleCacheEntity)

    @Query("SELECT * FROM ArticleCacheEntity ORDER BY orderId ASC")
    suspend fun getArticles(): List<ArticleCacheEntity>
}