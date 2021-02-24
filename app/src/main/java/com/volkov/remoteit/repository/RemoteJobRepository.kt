package com.volkov.remoteit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.volkov.remoteit.api.RemoteJobResponse
import com.volkov.remoteit.api.RetrofitInstance
import com.volkov.remoteit.db.RemoteJobDatabase
import com.volkov.remoteit.model.Job
import com.volkov.remoteit.model.JobToSave
import com.volkov.remoteit.model.RemoteJob
import com.volkov.remoteit.utils.Constansts.TAG

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteJobRepository (private val db: RemoteJobDatabase){
    private var remoteJobService = RetrofitInstance.apiService
    private var remoteJobResponseLiveData: MutableLiveData<RemoteJob> = MutableLiveData()
    private val searchRemoteJobLiveData: MutableLiveData<RemoteJob> = MutableLiveData()

    init {
        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse() {

        remoteJobService.getRemoteJobResponse().enqueue(
            object : Callback<RemoteJob> {
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    if (response.body() != null) {
                        remoteJobResponseLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                    remoteJobResponseLiveData.postValue(null)
                    Log.d("error ibm", t.message.toString())
                }
            })
    }

//
//    fun searchRemoteJob(query: String?) {
//        remoteJobService.searchRemoteJob(query).enqueue(
//            object : Callback<RemoteJob> {
//                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
//                    if (response.body() != null) {
//                        searchRemoteJobLiveData().postValue(response.body())
//                    }
//                }
//
//                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
//                    searchRemoteJobLiveData.postValue(null)
//                    Log.d("error ibm", t.message.toString())
//                }
//
//            }
//        )
//
//    }


    fun getRemoteJobResponseLiveData(): LiveData<RemoteJob> {
        return remoteJobResponseLiveData
    }

//    fun getSearchJobResponseLiveData(): LiveData<RemoteJob> {
//        return searchRemoteJob()
//    }


    suspend fun insertJob(job: JobToSave) = db.getRemoteJobDao().insertJob(job)
    suspend fun deleteJob(job: JobToSave) = db.getRemoteJobDao().deleteJob(job)
    fun getAllJobs() = db.getRemoteJobDao().getAllJob()

}