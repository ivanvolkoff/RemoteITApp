package com.volkov.remoteit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.volkov.remoteit.MainActivity
import com.volkov.remoteit.R
import com.volkov.remoteit.databinding.FragmentJobDetailsViewsBinding
import com.volkov.remoteit.model.Job
import com.volkov.remoteit.model.JobToSave
import com.volkov.remoteit.viewmodel.RemoteJobViewModel


class JobDetailsFragment : Fragment(R.layout.fragment_job_details_views) {

    private var _binding: FragmentJobDetailsViewsBinding? = null
    private val binding get() = _binding!!
    private val args: JobDetailsFragmentArgs by navArgs()
    private lateinit var currentJob: Job
    private lateinit var viewModel: RemoteJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJobDetailsViewsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        currentJob = args.job!!

        setUpWebView()

        binding.fabAddFavorite.setOnClickListener {
            addJobToFavorite(view)
        }

    }

    private fun setUpWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            currentJob.url?.let { loadUrl(it) }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            setSupportZoom(false)
            builtInZoomControls = false
            displayZoomControls = false
            textZoom = 100
            blockNetworkImage = false
            loadsImagesAutomatically = true
        }
    }

    private fun addJobToFavorite(view: View) {
        val job = JobToSave(
            0,
            currentJob.candidateRequiredLocation, currentJob.category,
            currentJob.companyLogoUrl, currentJob.companyName,
            currentJob.description, currentJob.id, currentJob.jobType,
            currentJob.publicationDate, currentJob.salary, currentJob.title, currentJob.url
        )

        viewModel.insertJob(job)
        Snackbar.make(view, "Job saved successfully", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}