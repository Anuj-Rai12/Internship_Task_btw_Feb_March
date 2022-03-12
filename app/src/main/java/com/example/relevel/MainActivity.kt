package com.example.relevel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.relevel.databinding.ActivityMainBinding
import com.example.relevel.model.MainVideoDataCls
import com.example.relevel.utils.ApiResponse
import com.example.relevel.viewmodels.MainViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModels by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    private fun getData() {
        viewModel.data.observe(this) {
            if (it is ApiResponse.Success) {
                it.data as MainVideoDataCls
            }
        }
    }
}