package com.example.mediaapp.features.image.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mediaapp.databinding.ImageItemBinding
import com.example.mediaapp.features.image.dataModel.Image


class ImageAdapter : PagingDataAdapter<Image, ImageAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = getItem(position)
        if (image != null) {
            holder.bind(image)
        }
    }

    inner class ImageViewHolder(private val binding: ImageItemBinding) :  RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
            // Load the image using Picasso or your preferred image-loading library
            Glide.with(itemView)
                .load(image.contentUri)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.roundedImageView)
        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }
}