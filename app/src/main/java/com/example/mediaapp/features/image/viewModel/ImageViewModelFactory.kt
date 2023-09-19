package com.example.mediaapp.features.image.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mediaapp.features.image.repository.ImageRepository
import com.example.mediaapp.features.video.repository.VideoRepository
import com.example.mediaapp.features.video.viewModel.VideoViewModel


class ImageViewModelFactory(private val repository: ImageRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}