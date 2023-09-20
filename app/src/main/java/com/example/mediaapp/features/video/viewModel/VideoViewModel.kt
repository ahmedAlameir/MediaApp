package com.example.mediaapp.features.video.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mediaapp.features.video.dataModel.Video
import com.example.mediaapp.features.video.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class VideoViewModel @Inject constructor(private val repository: VideoRepository) : ViewModel() {

    private val _videoLoadingStateFlow = MutableStateFlow<PagingData<Video>>(PagingData.empty())
    val videoLoadingStateFlow: StateFlow<PagingData<Video>> = _videoLoadingStateFlow

    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch {
            val pagingDataFlow: Flow<PagingData<Video>> =
                repository.getPagingData().cachedIn(viewModelScope)

            pagingDataFlow.collectLatest { pagingData ->
                _videoLoadingStateFlow.value = pagingData

            }
        }
    }
}