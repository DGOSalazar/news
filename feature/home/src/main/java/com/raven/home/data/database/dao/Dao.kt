package com.raven.home.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raven.home.data.database.entities.ArticleEntity

@Dao
interface Dao {
    @Query("SELECT * FROM articles_table_complete")
    fun getArticles():List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles_table_complete")
    fun deleteArticles()
}