package com.example.mediaapp.features.video.screen

import android.os.Bundle
import android.provider.MediaStore.Video
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mediaapp.R
import com.example.mediaapp.databinding.FragmentImageBinding
import com.example.mediaapp.databinding.FragmentVideoBinding
import com.example.mediaapp.features.image.screen.adapter.ImageAdapter
import com.example.mediaapp.features.image.viewModel.ImageViewModel
import com.example.mediaapp.features.image.viewModel.ImageViewModelFactory
import com.example.mediaapp.features.video.repository.VideoRepository
import com.example.mediaapp.features.video.screen.adapter.VideoAdapter
import com.example.mediaapp.features.video.viewModel.VideoViewModel
import com.example.mediaapp.features.video.viewModel.VideoViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class VideoFragment : Fragment() {
    private var adapter: VideoAdapter = VideoAdapter()
    private val viewModel: VideoViewModel by viewModels {
        VideoViewModelFactory(VideoRepository(requireContext()))
    }

    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(layoutInflater,container,false)
        binding.recyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videoLoadingStateFlow.collectLatest { loadingState ->
                adapter.submitData(viewLifecycleOwner.lifecycle,loadingState)

            }
        }
        return binding.root
    }

}