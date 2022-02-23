package com.example.relevel.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.relevel.R
import com.example.relevel.databinding.HomeSrcLayoutBinding
import com.example.relevel.utils.*
import com.example.relevel.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment(R.layout.home_src_layout) {
    private lateinit var binding: HomeSrcLayoutBinding
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).hide()
        activity?.changeStatusBarColor()
        binding = HomeSrcLayoutBinding.bind(view)
        viewModel.eventHandle.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { response ->
                connectionFailed()
                customMsg(response)
            }
        }

        getData()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getData() {
        viewModel.getAllData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseSealed.Error -> {
                    val err = it.exception?.localizedMessage
                    Log.i(TAG, "getData: $err")
                    connectionFailed()
                    customMsg(err)
                }
                is ResponseSealed.Loading -> {
                    hideWidgets()
                    binding.titleTxt.text = it.data?.toString()
                }
                is ResponseSealed.Success -> {
                    showWidgets()
                    customMsg(
                        "Connected",
                        length = Snackbar.LENGTH_SHORT,
                        color = R.color.green_color
                    )
                    Log.i(TAG, "getData: ${it.data}")
                }
            }
        }
    }

    private fun showWidgets() {
        binding.mainRecycleView.show()
        binding.pbLoading.hide()
        binding.titleTxt.hide()
    }


    private fun hideWidgets() {
        binding.mainRecycleView.hide()
        binding.pbLoading.show()
        binding.titleTxt.show()
        updateSize(150, 150)
    }

    private fun connectionFailed() {
        binding.mainRecycleView.hide()
        binding.titleTxt.hide()
        binding.pbLoading.show()
        binding.pbLoading.setAnimation(R.raw.page_not_found)
        updateSize(500, 500)
    }

    private fun updateSize(w: Int, h: Int) {
        binding.pbLoading.layoutParams.height = h
        binding.pbLoading.layoutParams.width = w
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun customMsg(
        err: String?,
        length: Int = Snackbar.LENGTH_INDEFINITE,
        color: Int = R.color.red_color
    ) {
        binding.root.showSandbar(
            msg = err ?: "UnKnown Error",
            length = length,
            activity?.getColorInt(color)
        )
    }

}