package com.raven.home.data.remote.models

import com.google.gson.annotations.SerializedName

data class MediaMetadataRemoteModel (
    @SerializedName("url") val url:String,
    @SerializedName("format") val format:String,
    @SerializedName("height") val height:Int,
    @SerializedName("width") val width:Int,
)