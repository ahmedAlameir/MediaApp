package com.example.mediaapp.features.common.state

import androidx.paging.PagingData

sealed class LoadingState<out T : Any> {
    data class Success<T : Any> (val data: PagingData<T>) : LoadingState<T>()
    object Loading : LoadingState<Nothing>()
    data class Error(val error: Throwable) : LoadingState<Nothing>()
    object Empty : LoadingState<Nothing>() // New state for empty data
}