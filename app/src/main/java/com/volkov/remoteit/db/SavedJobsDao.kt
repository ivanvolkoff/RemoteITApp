package com.volkov.remoteit.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.volkov.remoteit.model.JobToSave


@Dao
interface SavedJobsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobToSave): Long

    @Query("SELECT * FROM saved_jobs ORDER BY id DESC")
    fun getAllJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteJob(job: JobToSave)
}