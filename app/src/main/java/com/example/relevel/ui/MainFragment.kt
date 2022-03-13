package com.example.relevel.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.relevel.R
import com.example.relevel.adaptor.MainRecycleView
import com.example.relevel.databinding.MainFragmentsLayoutBinding
import com.example.relevel.model.MainVideoDataCls
import com.example.relevel.service.VideoPreLoadingService
import com.example.relevel.utils.*
import com.example.relevel.viewmodels.MainViewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragments_layout) {
    private lateinit var binding: MainFragmentsLayoutBinding
    private val viewModel: MainViewModels by viewModels()
    private lateinit var mainRecycleView: MainRecycleView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentsLayoutBinding.bind(view)
        viewModel.events.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { res ->
                binding.root.showSandbar(res, Snackbar.LENGTH_INDEFINITE)
            }
        }
        setUpData()
        getData()
        viewModel.videoItem.observe(viewLifecycleOwner) {
            Log.i(TAG, "onViewCreated: ${it.size}")
            if (it.isNotEmpty()) {
                startPreLoadingService(it)
            }
        }
    }

    private fun setUpData() {
        binding.viewPager.apply {
            orientation = ViewPager2.ORIENTATION_VERTICAL
            mainRecycleView = MainRecycleView(requireContext()) {

            }
            adapter = mainRecycleView
        }
    }

    private fun startPreLoadingService(list: ArrayList<String>) {
        val preloadingServiceIntent = Intent(context, VideoPreLoadingService::class.java)
        preloadingServiceIntent.putStringArrayListExtra(UtilsFiles.ApiService.VIDEO_LIST, list)
        context?.startService(preloadingServiceIntent)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getData() {
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Error -> {
                    hidePb()
                    Log.i(TAG, "getData: ${it.exception?.localizedMessage}\n\n\n ${it.exception}")
                    binding.root.showSandbar(it.exception?.localizedMessage!!,length = Snackbar.LENGTH_LONG)
                }
                is ApiResponse.Loading -> {
                    showPb()
                    binding.delTxt.text = it.data as String
                }
                is ApiResponse.Success -> {
                    hidePb()
                    if (it.data != null) {
                        binding.viewPager.show()
                        mainRecycleView.notifyDataSetChanged()
                        mainRecycleView.submitList((it.data as MainVideoDataCls).msg)
                    } else {
                        binding.root.showSandbar(
                            "Unknown Error Found Try Again",
                            length = Snackbar.LENGTH_LONG
                        )
                    }
                }
            }
        }
    }

    private fun showPb() = binding.pbLoading.show()

    private fun hidePb() {
        binding.pbLoading.hide()
        binding.delTxt.hide()
    }


}