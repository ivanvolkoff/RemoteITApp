package com.volkov.remoteit.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.volkov.remoteit.MainActivity
import com.volkov.remoteit.R
import com.volkov.remoteit.adaptor.JobRecyclerViewAdapter
import com.volkov.remoteit.databinding.FragmentRemoteJobBinding
import com.volkov.remoteit.utils.Constansts
import com.volkov.remoteit.viewmodel.RemoteJobViewModel


class RemoteJobFragment : Fragment(R.layout.fragment_remote_job),
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentRemoteJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var remoteJobViewModel: RemoteJobViewModel
    private lateinit var jobRecyclerViewAdapter: JobRecyclerViewAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRemoteJobBinding.inflate(inflater, container, false)

        swipeRefreshLayout = binding.swipeContainer
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN)

        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing = true
            setUpRecyclerView()
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        remoteJobViewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {

        jobRecyclerViewAdapter = JobRecyclerViewAdapter()


          binding.rvRemoteJobs.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)

            adapter = jobRecyclerViewAdapter
        }
        fetchingData()
    }

    private fun fetchingData() {

        activity?.let {
            if (Constansts.isNetworkAvailable(requireActivity())) {

                remoteJobViewModel.remoteJobResult()
                    .observe(viewLifecycleOwner) { remoteJob ->
                        if (remoteJob != null) {
                            jobRecyclerViewAdapter.differ.submitList(remoteJob.jobs)
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
            } else {
                Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onRefresh() {
        setUpRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}