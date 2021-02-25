package com.volkov.remoteit.api


import com.volkov.remoteit.model.RemoteJob
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteJobResponse {

  @GET("remote-jobs?limit=200")
  fun getRemoteJobResponse() : Call<RemoteJob>

  @GET("remote-jobs")
  fun searchRemoteJob(@Query("search") query: String?) : Call<RemoteJob>
}