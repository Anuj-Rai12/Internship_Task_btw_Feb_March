package com.example.relevel.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.relevel.R
import com.example.relevel.databinding.HomeSrcLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment(R.layout.home_src_layout) {
    private lateinit var binding:HomeSrcLayoutBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= HomeSrcLayoutBinding.bind(view)

    }
}