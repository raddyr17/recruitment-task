package com.raddyr.recruitmenttask.data.rest.retrofit

import com.raddyr.recruitmenttask.data.rest.model.ArticleNetworkEntity
import retrofit2.http.GET

interface ServicesApi {

    @GET("recruitment-task")
    suspend fun getArticles(): List<ArticleNetworkEntity>
}