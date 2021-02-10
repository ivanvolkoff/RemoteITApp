package com.volkov.remoteit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.volkov.remoteit.model.RemoteJob
import com.volkov.remoteit.repository.RemoteJobRepository

class RemoteJobViewModel(
    app: Application,
    private val remoteJobRepository: RemoteJobRepository
    ) : AndroidViewModel(app) {


        fun remoteJobResult() = remoteJobRepository.getRemoteJobsResponse()



}