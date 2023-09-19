package com.example.mediaapp.features.video.di

import android.content.Context
import androidx.paging.PagingSource
import com.example.mediaapp.features.video.dataModel.Video
import com.example.mediaapp.features.video.dataSource.VideoPagingSource
import com.example.mediaapp.features.video.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object VideoModule {
    @Provides
    fun provideVideoRepository(videoDataSource: VideoPagingSource): VideoRepository {
        return VideoRepository(videoDataSource)
    }
    @Provides
    fun provideVideoDataSource( context: Context): PagingSource<Int, Video> {
        return VideoPagingSource(context)
    }

}
