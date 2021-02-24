package com.volkov.remoteit.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.volkov.remoteit.MainActivity
import com.volkov.remoteit.R
import com.volkov.remoteit.adaptor.SavedJobsAdapter
import com.volkov.remoteit.databinding.FragmentSavedJobBinding
import com.volkov.remoteit.model.JobToSave
import com.volkov.remoteit.viewmodel.RemoteJobViewModel


class SavedJobFragment : Fragment(R.layout.fragment_saved_job) {

    private var _binding : FragmentSavedJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : RemoteJobViewModel
    private lateinit var savedJobsAdapter: SavedJobsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedJobBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        savedJobsAdapter = SavedJobsAdapter(this)
        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object : DividerItemDecoration(activity, LinearLayout.HORIZONTAL){})
            adapter = savedJobsAdapter
        }

        viewModel.getAllJob().observe(viewLifecycleOwner,{
            savedJob ->
            savedJobsAdapter.differ.submitList(savedJob)
            updateUI(savedJob)
        })
    }

    private fun updateUI(savedJob: List<JobToSave>) {
        if(savedJob.isNotEmpty()){
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        }
        else{
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}