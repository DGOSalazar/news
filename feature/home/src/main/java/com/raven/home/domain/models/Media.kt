package com.raven.home.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Media(
    val caption: String,
    val copyright: String,
    val photos: List<MediaMetaData>
): Parcelable