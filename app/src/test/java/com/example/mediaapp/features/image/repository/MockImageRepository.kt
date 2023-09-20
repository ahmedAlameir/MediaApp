package com.example.mediaapp.features.image.repository

import androidx.paging.PagingData
import com.example.mediaapp.features.image.dataModel.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockImageRepository : ImageRepositoryInterface {
    private var mockPagingData: PagingData<Image> = PagingData.empty()

    override fun getPagingData(): Flow<PagingData<Image>> {
        return flowOf(mockPagingData)
    }

    // Function to set the mock paging data for testing
    fun setMockPagingData(data: PagingData<Image>) {
        mockPagingData = data
    }
}