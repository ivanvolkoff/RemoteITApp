package com.volkov.remoteit.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.volkov.remoteit.R
import com.volkov.remoteit.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun setUpTabBar(){
        val adapter = FragmentPagerItemAdapter(childFragmentManager, FragmentPagerItems.with(activity)
            .add("Jobs",RemoteJobFragment::class.java)
            .add("Search",SearchJobFragment::class.java)
            .add("Saved Jobs",SavedJobFragment::class.java).create()
        )

        binding.viewpager.adapter = adapter
        binding.viewpagertab.setViewPager(binding.viewpager)
    }

}