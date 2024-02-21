package com.raven.home.data.remote.models

import com.google.gson.annotations.SerializedName

data class ArticleRemoteModel(
    @SerializedName("title") val title:String,
    @SerializedName("published_date") val publishedDate: String,
    @SerializedName("abstract") val abstract:String,
    @SerializedName("byline") val byline:String,
    @SerializedName("section") val section:String,
    @SerializedName("url") val url:String,
    @SerializedName("media") val media:List<MediaRemoteModel>
)