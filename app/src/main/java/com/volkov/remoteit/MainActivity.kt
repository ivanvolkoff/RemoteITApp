package com.volkov.remoteit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.volkov.remoteit.databinding.ActivityMainBinding
import com.volkov.remoteit.db.RemoteJobDatabase
import com.volkov.remoteit.repository.RemoteJobRepository
import com.volkov.remoteit.viewmodel.RemoteJobViewModel
import com.volkov.remoteit.viewmodel.RemoteJobViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: RemoteJobViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""

        setUpViewModel()

    }


    private fun setUpViewModel() {

        val remoteJobRepository = RemoteJobRepository(
            RemoteJobDatabase(this)
        )

        val viewModelProviderFactory =
            RemoteJobViewModelFactory(
                application,
                remoteJobRepository
            )

        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(RemoteJobViewModel::class.java)

    }
}