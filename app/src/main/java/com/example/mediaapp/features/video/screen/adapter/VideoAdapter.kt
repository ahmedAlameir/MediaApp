package com.example.mediaapp.features.video.screen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mediaapp.databinding.ImageItemBinding
import com.example.mediaapp.databinding.VideoItemBinding
import com.example.mediaapp.features.image.dataModel.Image
import com.example.mediaapp.features.video.dataModel.Video


class VideoAdapter : PagingDataAdapter<Video, VideoAdapter.VideoViewHolder>(Video_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        if (video != null) {
            holder.bind(video)
        }
    }

    inner class VideoViewHolder(private val binding: VideoItemBinding) :  RecyclerView.ViewHolder(binding.root) {

        fun bind(video: Video) {
            binding.imageUri = video.thumbnailUri

        }
    }

    companion object {
        private val Video_COMPARATOR = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }
        }
    }
}