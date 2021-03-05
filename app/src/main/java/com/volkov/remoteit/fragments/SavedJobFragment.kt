package com.volkov.remoteit.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.volkov.remoteit.MainActivity
import com.volkov.remoteit.R
import com.volkov.remoteit.adaptor.SavedJobsAdapter
import com.volkov.remoteit.databinding.FragmentSavedJobBinding
import com.volkov.remoteit.model.JobToSave
import com.volkov.remoteit.viewmodel.RemoteJobViewModel


class SavedJobFragment : Fragment(R.layout.fragment_saved_job),
    SavedJobsAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var savedJobsAdapter: SavedJobsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedJobBinding.inflate(inflater, container, false)
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

            adapter = savedJobsAdapter
        }

        viewModel.getAllJob().observe(viewLifecycleOwner) { savedJob ->
            savedJobsAdapter.differ.submitList(savedJob)
            updateUI(savedJob)
        }
    }

    private fun updateUI(savedJob: List<JobToSave>) {
        if (savedJob.isNotEmpty()) {
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        } else {
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(job: JobToSave, view: View, position: Int) {
        deleteJob(job)
    }

    private fun deleteJob(job: JobToSave) {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete job?")
                .setMessage("Are you sure you want permanently delete this job?")
                .setPositiveButton("DELETE") { _, _ ->
                    viewModel.deleteJob(job)
                    Toast.makeText(activity, "Job deleted", Toast.LENGTH_SHORT).show()
                }
            setNegativeButton("CANCEL", null)
        }.create().show()

    }

}