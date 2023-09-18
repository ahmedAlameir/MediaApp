package com.example.mediaapp.features.image.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mediaapp.R
import com.example.mediaapp.databinding.FragmentImageBinding


class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

}