package com.example.mediaapp.features.image.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mediaapp.features.image.dataModel.Image
import com.example.mediaapp.features.image.dataSource.ImagePagingSource
import kotlinx.coroutines.flow.Flow

class ImageRepository(private val context: Context) {
    fun getPagingData(): Flow<PagingData<Image>> {
        val pagingConfig = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = false
        )

        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = { ImagePagingSource(context) }
        )

        return pager.flow
    }
}