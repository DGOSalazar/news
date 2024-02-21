package com.raven.home.data.database

import androidx.room.RoomDatabase
import com.raven.home.data.database.dao.Dao
import com.raven.home.data.database.entities.ArticleEntity

@androidx.room.Database (entities = [ArticleEntity::class], version = 1)
abstract class Database : RoomDatabase(){
    abstract fun getDao(): Dao
}