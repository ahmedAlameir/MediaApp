package com.example.mediaapp.features.image.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mediaapp.databinding.FragmentImageBinding
import com.example.mediaapp.features.common.state.LoadingState
import com.example.mediaapp.features.image.repository.ImageRepository
import com.example.mediaapp.features.image.viewModel.ImageViewModel
import com.example.mediaapp.features.image.viewModel.ImageViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private val viewModel: ImageViewModel by viewModels {
        ImageViewModelFactory(ImageRepository(requireContext())) // Replace with your repository
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentImageBinding.inflate(layoutInflater,container,false)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageLoadingStateFlow.collectLatest { loadingState ->
                    when (loadingState) {
                        is LoadingState.Success -> {
                            val pagingData = loadingState.data

                        }
                        is LoadingState.Loading -> {

                        }
                        is LoadingState.Error -> {
                            val error = loadingState.error
                            // Handle error, display an error message, etc.
                        }
                        is LoadingState.Empty -> {
                            // Handle the empty data state
                        }
                    }
                }
            }
        }
        return binding.root
    }

}