package com.example.mediaapp.features.image.dataSource
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mediaapp.features.image.dataModel.Image
import javax.inject.Inject


class ImagePagingSource @Inject constructor(private val context: Context) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        try {
            val startPosition = params.key ?: 0
            val pageSize = params.loadSize

            val images = loadImages(startPosition, pageSize)

            val nextKey = if (images.isEmpty()) null else startPosition + pageSize

            return LoadResult.Page(
                data = images,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        // Define logic for refreshing data, if needed
        return null
    }

    private fun loadImages(startPosition: Int, pageSize: Int): List<Image> {
        val images = mutableListOf<Image>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC LIMIT $pageSize  OFFSET $pageSize "

        val queryUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI


        val cursor: Cursor? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Get All data in Cursor by sorting in DESC order
            context.contentResolver.query(
                queryUri,
                projection,
                Bundle().apply {

                    putInt(ContentResolver.QUERY_ARG_LIMIT, pageSize)
                    putInt(ContentResolver.QUERY_ARG_OFFSET, startPosition)
                    putStringArray(
                        ContentResolver.QUERY_ARG_SORT_COLUMNS,
                        arrayOf(MediaStore.Images.Media.DATE_ADDED)
                    )

                    putInt(
                        ContentResolver.QUERY_ARG_SORT_DIRECTION,
                        ContentResolver.QUERY_SORT_DIRECTION_DESCENDING
                    )

                }, null
            )
        } else {

            context.contentResolver.query(
                queryUri,
                projection,
                null,
                null,
                sortOrder
            )
        }
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)


                val contentUri = Uri.withAppendedPath(queryUri, id.toString())
                images.add(Image(id, contentUri))
            }
        }
        cursor?.close()

        return images
    }

}

