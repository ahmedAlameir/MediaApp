package com.example.mediaapp.features.image.screen

import android.Manifest
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
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
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageBinding.inflate(layoutInflater,container,false)
        gridLayoutManager =  GridLayoutManager(requireContext(), calculateSpanCount())
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.adapter = adapter
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                lifecycleScope.launch {
                    viewModel.imageLoadingStateFlow.collectLatest { loadingState ->
                        adapter.submitData(viewLifecycleOwner.lifecycle, loadingState)
                    }
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }else{
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

        }
        adapter.addLoadStateListener { loadState ->

            if ( loadState.append.endOfPaginationReached )
            {
                binding.emptyStateTextView.isVisible = adapter.itemCount < 1
            }
        }
        return binding.root
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        gridLayoutManager.spanCount = calculateSpanCount()
    }

    private fun calculateSpanCount(): Int {
        val orientation = resources.configuration.orientation
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            5
        } else {
            2
        }
    }



}