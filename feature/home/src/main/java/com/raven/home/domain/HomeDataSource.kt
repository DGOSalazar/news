package com.raven.home.domain

import com.raven.home.data.database.entities.ArticleEntity
import com.raven.home.data.remote.models.ApiResponseModel
import com.raven.home.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    suspend fun getNews(): Flow<Resource<ApiResponseModel>>
    suspend fun insertArticlesDao(articles: List<ArticleEntity>): Flow<Unit>
    suspend fun getNewsDao() : Flow<List<ArticleEntity>>
    suspend fun deleteNewsDao(): Flow<Unit>
}
