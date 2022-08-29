package com.raddyr.recruitmenttask.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.raddyr.recruitmenttask.BuildConfig
import com.raddyr.recruitmenttask.data.db.ArticleDao
import com.raddyr.recruitmenttask.data.db.ArticleDatabase
import com.raddyr.recruitmenttask.data.db.CacheMapper
import com.raddyr.recruitmenttask.data.repository.Repository
import com.raddyr.recruitmenttask.data.repository.RepositoryImpl
import com.raddyr.recruitmenttask.data.rest.retrofit.NetworkMapper
import com.raddyr.recruitmenttask.data.rest.retrofit.ServicesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun create(retrofit: Retrofit.Builder): ServicesApi =
        retrofit.build().create(ServicesApi::class.java)

    @Singleton
    @Provides
    fun provideTodoDatabase(@ApplicationContext app: Context): ArticleDatabase {
        return Room.databaseBuilder(
            app,
            ArticleDatabase::class.java,
            "article_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideArticleDAO(articleDb: ArticleDatabase): ArticleDao {
        return articleDb.articleDao()
    }

    @Singleton
    @Provides
    fun provideRepository(
        dao: ArticleDao,
        servicesApi: ServicesApi,
        networkMapper: NetworkMapper,
        cacheMapper: CacheMapper
    ): Repository {
        return RepositoryImpl(dao, servicesApi, networkMapper, cacheMapper)
    }
}