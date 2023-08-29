package com.example.map.app.di.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.map.app.PointsAdapter
import com.example.map.databinding.FragmentPointsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PointsListFragment : Fragment() {

    private lateinit var binding: FragmentPointsListBinding
    private val viewModel: PointsListViewModel by viewModels()
    private lateinit var adapter: PointsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = PointsAdapter()
        binding.rv.layoutManager = LinearLayoutManager(activity)
        binding.rv.adapter = adapter
        viewModel.liveDataPoints.observe(viewLifecycleOwner, Observer {
            adapter.points = it
        })
        super.onViewCreated(view, savedInstanceState)
    }

}