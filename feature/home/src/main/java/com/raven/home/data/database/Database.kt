package com.raven.home.data.database

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.raven.home.data.database.dao.Dao
import com.raven.home.data.database.entities.ArticleEntity

@androidx.room.Database (entities = [ArticleEntity::class], version = 3)
abstract class Database : RoomDatabase() {
    abstract fun getDao(): Dao
    companion object {
        val MIGRATION_1_2: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE articles_table_complete ADD COLUMN abstract TEXT NOT NULL DEFAULT ''")
            }
        }
    }
}