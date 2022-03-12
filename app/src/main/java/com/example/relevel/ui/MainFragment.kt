package com.example.relevel.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.relevel.R
import com.example.relevel.databinding.MainFragmentsLayoutBinding
import com.example.relevel.utils.*
import com.example.relevel.viewmodels.MainViewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragments_layout) {
    private lateinit var binding: MainFragmentsLayoutBinding
    private val viewModel: MainViewModels by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = MainFragmentsLayoutBinding.bind(view)
        viewModel.events.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { res ->
                binding.root.showSandbar(res, Snackbar.LENGTH_INDEFINITE)
            }
        }

        getData()
        viewModel.videoItem.observe(viewLifecycleOwner){
            Log.i(TAG, "onViewCreated: ${it.size}")
        }
    }

    private fun getData() {
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is ApiResponse.Error -> {
                    hidePb()
                    Log.i(TAG, "getData: ${it.exception?.localizedMessage}\n\n\n ${it.exception}")
                    binding.delTxt.text = it.exception?.localizedMessage
                }
                is ApiResponse.Loading -> {
                    showPb()
                    binding.delTxt.text = it.data as String
                }
                is ApiResponse.Success -> {
                    hidePb()
                    binding.delTxt.text = "${it.data}"
                }
            }
        }
    }

    private fun showPb() = binding.pbLoading.show()

    private fun hidePb() = binding.pbLoading.hide()


}