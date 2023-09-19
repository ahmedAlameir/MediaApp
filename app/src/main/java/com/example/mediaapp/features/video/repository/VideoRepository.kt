package com.example.mediaapp.features.video.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.mediaapp.features.video.dataModel.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoRepository @Inject constructor(private val videoDataSource: PagingSource<Int, Video>){
    fun getPagingData(): Flow<PagingData<Video>> {
        val pagingConfig = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = false
        )

        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = { videoDataSource}
        )

        return pager.flow
    }
}