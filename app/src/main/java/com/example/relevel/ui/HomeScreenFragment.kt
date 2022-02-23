package com.example.relevel.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.relevel.R
import com.example.relevel.adaptor.MainAdaptor
import com.example.relevel.databinding.HomeSrcLayoutBinding
import com.example.relevel.model.users.CilentDataResponse
import com.example.relevel.utils.*
import com.example.relevel.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment(R.layout.home_src_layout) {
    private lateinit var binding: HomeSrcLayoutBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mainAdaptor: MainAdaptor

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).hide()
        activity?.changeStatusBarColor()
        binding = HomeSrcLayoutBinding.bind(view)
        binding.bottomBarLayout.apply {
            leaveRoom.text =
                "${AllConstString.getEmojiByUnicode(AllConstString.emg_victory)} ${getText(R.string.lev_txt)}"
            inviteBtn.text = getString(R.string.invite_txt)
        }
        viewModel.eventHandle.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { response ->
                connectionFailed()
                customMsg(response)
            }
        }
        setRecycleView()
        getData()
    }

    private fun setRecycleView() {
        binding.mainRecycleView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            mainAdaptor = MainAdaptor {
                Log.i(TAG, "setRecycleView: $it")
            }
            adapter = mainAdaptor
        }
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
                    it.data?.let { res ->
                        val item = res as CilentDataResponse
                        binding.versionTxt.text = item.data.version.toString()
                        setUpToolBar(item)
                        setData(item)

                        return@let
                    } ?: customMsg(err = "Failed To Load Data")
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(item: CilentDataResponse) {
        viewModel.getSealedClass(item, requireActivity(), getString(R.string.aud_title)).apply {
            mainAdaptor.notifyDataSetChanged()
            mainAdaptor.submitList(this)
        }
    }

    private fun setUpToolBar(response: CilentDataResponse) {
        binding.toolbarLayout.apply {
            totalPublic.text = response.data.totalMembers.toString()
            hostImgView.loadImage(AllConstString.getImageProfileUrl(response.data.host.u))
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