package com.raven.home.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaMetaData (
    val url:String,
    val format:String,
    val height:Int,
    val width:Int,
): Parcelable