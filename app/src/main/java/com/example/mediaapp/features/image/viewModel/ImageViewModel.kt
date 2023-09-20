package com.example.mediaapp.features.image.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mediaapp.features.image.dataModel.Image
import com.example.mediaapp.features.image.repository.ImageRepository
import com.example.mediaapp.features.image.repository.ImageRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: ImageRepositoryInterface) : ViewModel() {

    private val _imageLoadingStateFlow = MutableStateFlow<PagingData<Image>>(PagingData.empty())
    val imageLoadingStateFlow: StateFlow<PagingData<Image>> = _imageLoadingStateFlow
    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch {
            val pagingDataFlow: Flow<PagingData<Image>> =
                repository.getPagingData().cachedIn(viewModelScope)
            pagingDataFlow.collectLatest { pagingData ->
                _imageLoadingStateFlow.value = pagingData
            }
        }
    }
}