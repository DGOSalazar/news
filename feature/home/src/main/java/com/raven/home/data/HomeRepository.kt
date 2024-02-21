package com.raven.home.data


import com.raven.home.data.database.dao.Dao
import com.raven.home.data.database.entities.ArticleEntity
import com.raven.home.data.remote.models.ApiResponseModel
import com.raven.home.utils.Resource
import com.raven.home.data.remote.service.HomeService
import com.raven.home.domain.HomeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private var apiClient: HomeService,
    private var database: Dao
) : HomeDataSource {
    override suspend fun getNews(): Flow<Resource<ApiResponseModel>> = flow {
        try {
            val response = apiClient.getNews()
            if (response.isSuccessful) emit(
                    Resource.success(
                        response.body()
                    )
                )
            else emit(
                    Resource.error(
                        code = response.code(),
                        msg = response.message()
                    )
                )
        } catch (e: Exception) {
            emit(
                Resource.error(
                    code = 500,
                    msg = e.message
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertArticlesDao(articles : List<ArticleEntity>): Flow<Unit> = flow {
        val res = database.insertArticles(articles)
        emit(res)
    }.flowOn(Dispatchers.IO)

    override suspend fun getNewsDao(): Flow<List<ArticleEntity>> = flow {
        val res = database.getArticles()
        emit(res)
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteNewsDao(): Flow<Unit> = flow {
        val res = database.deleteArticles()
        emit(res)
    }.flowOn(Dispatchers.IO)
}
