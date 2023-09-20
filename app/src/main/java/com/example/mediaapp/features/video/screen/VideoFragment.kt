package com.example.mediaapp.features.video.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mediaapp.databinding.FragmentVideoBinding
import com.example.mediaapp.features.video.screen.adapter.VideoAdapter
import com.example.mediaapp.features.video.viewModel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideoFragment : Fragment() {
    private lateinit var binding: FragmentVideoBinding
    private var adapter: VideoAdapter = VideoAdapter()
    private val viewModel: VideoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVideoBinding.inflate(layoutInflater,container,false)
        binding.recyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videoLoadingStateFlow.collectLatest { loadingState ->
                adapter.submitData(viewLifecycleOwner.lifecycle, loadingState)



            }

        }
        return binding.root
    }

}