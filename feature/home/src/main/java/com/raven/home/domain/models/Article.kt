package com.raven.home.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title : String = "",
    val publishedDate: String = "",
    val abstract : String = "",
    val byLine: String = "",
    val section: String = "",
    val url: String = "",
    val media : List<Media> = listOf()
) : Parcelable