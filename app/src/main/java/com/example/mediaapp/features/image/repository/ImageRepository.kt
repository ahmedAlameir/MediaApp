package com.example.mediaapp.features.image.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.mediaapp.features.image.dataModel.Image
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
interface ImageRepositoryInterface {
    fun getPagingData(): Flow<PagingData<Image>>
}
class ImageRepository @Inject constructor(private val imageDataSource: PagingSource<Int, Image>):ImageRepositoryInterface {
    override fun getPagingData(): Flow<PagingData<Image>> {
        val pagingConfig = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = false
        )

        val pager = Pager(
            config = pagingConfig,
            pagingSourceFactory = { imageDataSource}
        )

        return pager.flow
    }
}