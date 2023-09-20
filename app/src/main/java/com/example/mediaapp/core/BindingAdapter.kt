package com.example.mediaapp.core

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("src")
fun loadImage(view: ImageView, imageUri:  Uri?) {
    imageUri?.let {
        Glide.with(view.context)
        .load( imageUri)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
    }
}