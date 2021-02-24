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
import com.volkov.remoteit.model.Job


class JobRecyclerViewAdapter : RecyclerView.Adapter<JobRecyclerViewAdapter.JobHolder>() {

    private var binding: JobLayoutAdapterBinding? = null

    private val differCallback = object :
        DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
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
            var direction = MainFragmentDirections.actionMainFragmentToJobDetailsFragment(currentJob!!)
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