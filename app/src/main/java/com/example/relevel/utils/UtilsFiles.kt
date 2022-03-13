package com.example.relevel.utils

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar

const val TAG = "ANUJ"

class UtilsFiles {
    object ApiService {
        const val BASE_URL = "http://fatema.takatakind.com/"
        const val END_POINT = "app_api/index.php"
        const val VIDEO_LIST="video_item_list"
    }
//http://fatema.takatakind.com/app_api/index.php?p=showAllVideos
}

fun View.showSandbar(msg: String, length: Int = Snackbar.LENGTH_SHORT) {
    val snackBar = Snackbar.make(this, msg, length)
    snackBar.view.setBackgroundColor(Color.parseColor("#D50000"))
    snackBar.show()
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    //Internet connectivity check in Android Q
    val networks = connectivityManager.allNetworks
    var hasInternet = false
    if (networks.isNotEmpty()) {
        for (network in networks) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) hasInternet =
                true
        }
    }
    return hasInternet
}

fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}