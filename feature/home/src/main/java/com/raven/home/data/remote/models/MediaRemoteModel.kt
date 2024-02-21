package com.raven.home.data.remote.models

import com.google.gson.annotations.SerializedName

data class MediaRemoteModel (
    @SerializedName("caption") val caption:String,
    @SerializedName("copyright") val copyright:String,
    @SerializedName("media-metadata") val mediaMetadata:List<MediaMetadataRemoteModel>
)