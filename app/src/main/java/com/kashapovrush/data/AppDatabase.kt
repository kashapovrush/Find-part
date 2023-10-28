package com.kashapovrush.data

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameTable
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [PartDb::class],
    version = 3,
    exportSchema = true
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun partListDao(): PartListDao

    companion object {

        val MIGRATION_1_2 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `parts3` (`name` TEXT NOT NULL, 'count' INTEGER NOT NULL, 'date' TEXT NOT NULL, 'number' TEXT NOT NULL, `id` INTEGER NOT NULL, 'time' TEXT NOT NULL, 'location' TEXT NOT NULL, " +
                        "PRIMARY KEY(`id`))"
                )
            }
        }


        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "parts.db"

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }

            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }

                val database = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = database
                return database
            }
        }
    }

}

