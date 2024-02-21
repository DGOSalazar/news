package com.raven.home.data

import com.raven.home.data.remote.models.ApiResponseModel
import com.raven.home.data.remote.models.Resource
import com.raven.home.data.remote.service.HomeService
import com.raven.home.domain.HomeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private var apiClient: HomeService
) : HomeDataSource {

    override suspend fun getNews(): Flow<Resource<ApiResponseModel>> = flow {
        val response = apiClient.getNews()
        emit(Resource.success(response.body()))
    }.flowOn(Dispatchers.IO)
}
