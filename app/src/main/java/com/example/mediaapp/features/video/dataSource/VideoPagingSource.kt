package com.example.mediaapp.features.video.dataSource
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mediaapp.features.video.dataModel.Video


class VideoPagingSource(private val context: Context) : PagingSource<Int, Video>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        try {
            val startPosition = params.key ?: 0
            val pageSize = params.loadSize

            val videos = loadVideos(startPosition, pageSize)

            val nextKey = if (videos.isEmpty()) null else startPosition + pageSize

            return LoadResult.Page(
                data = videos,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Video>): Int? {
        // Define logic for refreshing data, if needed
        return null
    }

    private fun loadVideos(startPosition: Int, pageSize: Int): List<Video> {
        val videos = mutableListOf<Video>()

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA
        )

        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC LIMIT $pageSize OFFSET $pageSize  "

        val queryUri: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI



        val cursor: Cursor? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
            val idColumn = it.getColumnIndexOrThrow(MediaStore.Video.Media._ID)

            while (it.moveToNext()) {
                val videoId = it.getLong(idColumn)
                val contentUri = Uri.withAppendedPath(queryUri, videoId.toString())
                videos.add(Video(videoId,contentUri))
            }
        }
        cursor?.close()

        return videos
    }

}

