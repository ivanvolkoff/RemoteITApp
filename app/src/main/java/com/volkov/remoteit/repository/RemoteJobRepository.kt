package com.volkov.remoteit.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.volkov.remoteit.api.RemoteJobResponse
import com.volkov.remoteit.api.RetrofitInstance
import com.volkov.remoteit.model.RemoteJob
import com.volkov.remoteit.utils.Constansts.TAG

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RemoteJobRepository {
    private val remoteJobService = RetrofitInstance.apiService
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJob> = MutableLiveData()


     fun getRemoteJobsResponse() {
        remoteJobService.getRemoteJobResponse().enqueue(
            object : Callback<RemoteJob> {

                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    remoteJobResponseLiveData.postValue(response.body())
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
                    remoteJobResponseLiveData.postValue(null)
                    Log.e(TAG, "on Failure: ${t.message}")
                }
            }
        )
    }

    fun removeJob():LiveData<RemoteJob>{
        return remoteJobResponseLiveData
    }
}