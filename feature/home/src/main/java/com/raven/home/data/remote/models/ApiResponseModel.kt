package com.raven.home.data.remote.models

import com.google.gson.annotations.SerializedName

data class ApiResponseModel (
    @SerializedName("status") val status:String = "",
    @SerializedName("copyright") val copyright:String = "",
    @SerializedName("num_results") val num_results:Int = 0,
    @SerializedName("results") val results:List<ArticleRemoteModel> = listOf(),
)