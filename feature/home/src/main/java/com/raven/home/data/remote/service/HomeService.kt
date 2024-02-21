package com.raven.home.data.remote.service

import com.raven.home.data.remote.models.ApiResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {

    //TODO("Correctly apply the Path and its answers. The API Key is provided in your PDF document")
    @GET("svc/mostpopular/v2/emailed/7.json?api-key=f4zL4tULmghgTFeT1A4HrZ8rzeKbzKjO")
    suspend fun getNews(): Response<ApiResponseModel>
}
