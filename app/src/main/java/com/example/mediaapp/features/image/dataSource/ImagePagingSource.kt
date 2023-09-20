package com.example.mediaapp.features.image.dataSource
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mediaapp.features.image.dataModel.Image
import javax.inject.Inject


class ImagePagingSource @Inject constructor(private val context: Context) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        try {
            val page = params.key ?: 0
            val pageSize = params.loadSize

            val images = loadImages(page, pageSize)

            val nextKey = if (images.isEmpty()) null else page + pageSize

            return LoadResult.Page(
                data = images,
                prevKey = if (page == 0) null else page-pageSize,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(20)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(20)
        }
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
                images.add(Image(id, contentUri.toString()))
            }
        }
        cursor?.close()

        return images
    }

}

