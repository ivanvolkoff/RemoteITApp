package com.volkov.remoteit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volkov.remoteit.MainActivity
import com.volkov.remoteit.R
import com.volkov.remoteit.adaptor.JobRecyclerViewAdapter
import com.volkov.remoteit.databinding.FragmentSearchJobBinding
import com.volkov.remoteit.utils.Constansts
import com.volkov.remoteit.viewmodel.RemoteJobViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchJobFragment : Fragment(R.layout.fragment_search_job) {

    private var _binding: FragmentSearchJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var jobAdaprer: JobRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchJobBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel


        if(Constansts.isNetworkAvailable(requireContext())){
            searchJob()
            setUpRecyclerView()
        }
        else{
            Toast.makeText(activity,"There's no internet connection",Toast.LENGTH_SHORT).show()
        }



    }

    private fun searchJob() {
        var jobs: Job? = null
        binding.etSearch.addTextChangedListener { text ->
            jobs?.cancel()
            jobs = MainScope().launch {
                delay(500L)
                text?.let {
                    if (text.toString().isNotEmpty()) {
                        viewModel.searchRemoteJob(text.toString())
                    }
                }

            }
        }

    }

    private fun setUpRecyclerView() {
        jobAdaprer = JobRecyclerViewAdapter()
        binding.rvSearchJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = jobAdaprer
        }
        viewModel.searchResult().observe(viewLifecycleOwner
        ) {
            jobAdaprer.differ.submitList(it.jobs)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}