package com.example.mediaapp.features.image.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mediaapp.databinding.FragmentImageBinding
import com.example.mediaapp.features.image.screen.adapter.ImageAdapter
import com.example.mediaapp.features.image.viewModel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private var adapter:ImageAdapter = ImageAdapter()
    private val viewModel: ImageViewModel by  viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentImageBinding.inflate(layoutInflater,container,false)
        binding.recyclerView.layoutManager =  GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            viewModel.imageLoadingStateFlow.collectLatest { loadingState ->
                adapter.submitData(viewLifecycleOwner.lifecycle, loadingState)

            }

        }
        return binding.root
    }

}