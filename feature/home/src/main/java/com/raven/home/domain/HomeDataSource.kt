package com.raven.home.domain

import com.raven.home.data.remote.models.ApiResponseModel
import com.raven.home.data.remote.models.Resource
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {
    suspend fun getNews(): Flow<Resource<ApiResponseModel>>
}
