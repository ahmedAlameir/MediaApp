package com.example.mediaapp.features.video.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mediaapp.features.video.dataModel.Video
import com.example.mediaapp.features.video.dataSource.VideoPagingSource
import kotlinx.coroutines.flow.Flow

class VideoRepository(private val context: Context) {
    fun getPagingData(): Flow<PagingData<Video>> {
        val pagingConfig = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = false
        )

        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = { VideoPagingSource(context) }
        )

        return pager.flow
    }
}