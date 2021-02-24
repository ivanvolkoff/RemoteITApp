package com.volkov.remoteit.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.volkov.remoteit.model.JobToSave


@Database(entities = [JobToSave::class], version = 1)
abstract class SavedJobsDatabase : RoomDatabase() {

    abstract fun getRemoteJobDao(): SavedJobsDao

    companion object {
        @Volatile
        private var instance: SavedJobsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                SavedJobsDatabase::class.java,
                "saved_jobs"
            ).build()
    }
}