package com.raven.home.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "articles_table")
data class ArticleEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="identity") var identity : Int = 0,
    @ColumnInfo(name="title") var title:String ="",
    @ColumnInfo(name="published_date") var publishedDate: String ="",
    @ColumnInfo(name="abstract") var abstract:String="",
    @ColumnInfo(name="byline") var byline:String="",
    @ColumnInfo(name="section") var section:String="",
    @ColumnInfo(name="url") var url:String="",
    @ColumnInfo(name="media") var media:String = ""
)