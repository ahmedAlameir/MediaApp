package com.example.mediaapp.features.video.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mediaapp.R
import com.example.mediaapp.databinding.FragmentVideoBinding


class VideoFragment : Fragment() {

    private lateinit var binding: FragmentVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVideoBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

}