package com.example.mediaapp.features.image.viewModel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.example.mediaapp.features.image.dataModel.Image
import com.example.mediaapp.features.image.repository.ImageRepositoryInterface
import com.example.mediaapp.features.image.repository.MockImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ImageViewModelTest {

    // Add InstantTaskExecutorRule to test LiveData
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    lateinit var mockRepository: MockImageRepository

    // Create an instance of the ViewModel to be tested
    private lateinit var imageViewModel: ImageViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        mockRepository = MockImageRepository()
        imageViewModel = ImageViewModel(mockRepository)
    }

    @Test
    fun testLoadImages() = runTest {
        val testData = PagingData.from(listOf(Image(1234, ""), Image(4321, "")))

        mockRepository.setMockPagingData(testData)
        val testStateFlow = MutableStateFlow<PagingData<Image>>(PagingData.empty())

        val job =   launch(UnconfinedTestDispatcher(testScheduler)) {
            imageViewModel.imageLoadingStateFlow.collectLatest { pagingData ->
                testStateFlow.value = pagingData
            }
        }

        testScheduler.apply { advanceTimeBy(1); runCurrent() }

        assertNotNull(testStateFlow.value)
        job.cancel()
    }
}


