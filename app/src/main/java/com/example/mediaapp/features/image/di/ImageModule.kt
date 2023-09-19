package com.example.mediaapp.features.image.di

import android.content.Context
import androidx.paging.PagingSource
import com.example.mediaapp.features.image.dataModel.Image
import com.example.mediaapp.features.image.dataSource.ImagePagingSource
import com.example.mediaapp.features.image.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    @Provides
    fun provideImageRepository(imageDataSource: ImagePagingSource): ImageRepository {
        return ImageRepository(imageDataSource)
    }
    @Provides
    fun provideImageDataSource( context: Context): PagingSource<Int, Image> {
        return ImagePagingSource(context)
    }

}


