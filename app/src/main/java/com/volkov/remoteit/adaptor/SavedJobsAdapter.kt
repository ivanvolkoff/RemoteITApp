package com.volkov.remoteit.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.volkov.remoteit.databinding.JobLayoutAdapterBinding
import com.volkov.remoteit.fragments.MainFragmentDirections
import com.volkov.remoteit.fragments.SavedJobFragment
import com.volkov.remoteit.model.Job
import com.volkov.remoteit.model.JobToSave


class SavedJobsAdapter( private val itemClick: SavedJobFragment) : RecyclerView.Adapter<SavedJobsAdapter.JobHolder>() {

    private var binding: JobLayoutAdapterBinding? = null

    private val differCallback = object :
        DiffUtil.ItemCallback<JobToSave>() {
        override fun areItemsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return oldItem.jobId == newItem.jobId
        }

        override fun areContentsTheSame(oldItem: JobToSave, newItem: JobToSave): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobHolder {
        binding = JobLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobHolder(binding!!)
    }

    override fun onBindViewHolder(holder: JobHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(currentJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName?.text = currentJob.companyName
            binding?.tvJobLocation?.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle?.text = currentJob.title
            binding?.tvJobType?.text = currentJob.jobType

            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate?.text = dateJob?.get(0)
        }.setOnClickListener{
            mView ->
            val tags = arrayListOf<String>()
            val job = Job(
                currentJob.candidateRequiredLocation,
                currentJob.category,
                currentJob.companyLogoUrl,
                currentJob.companyName,
                currentJob.description,
                currentJob.jobId,
                currentJob.jobType,
                currentJob.publicationDate,
                currentJob.salary,
                tags,
                currentJob.title,
                currentJob.url)
            var direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(job!!)
            mView.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class JobHolder(itemBinding: JobLayoutAdapterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {


    }
}