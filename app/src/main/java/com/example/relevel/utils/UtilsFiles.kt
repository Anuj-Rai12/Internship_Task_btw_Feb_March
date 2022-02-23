package com.example.relevel.utils

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.relevel.R
import com.google.android.material.snackbar.Snackbar

object AllConstString {
    const val Base = "https://api.bolkarapp.com/"
    const val Get_All_Data = "live/room.json"
    fun getImageProfileUrl(u: String) = "https://cdn1.bolkarapp.com/uploads/dp/$u.jpg"
}

fun View.showSandbar(msg: String, length: Int = Snackbar.LENGTH_SHORT, color: Int? = null) {
    val snackBar = Snackbar.make(this, msg, length)
    color?.let {
        snackBar.view.setBackgroundColor(it)
    }
    snackBar.show()
}


fun View.hide() {
    this.isVisible = false
}

fun View.show() {
    this.isVisible = true
}


@RequiresApi(Build.VERSION_CODES.M)
fun Activity.getColorInt(color: Int): Int {
    return resources.getColor(color, null)
}


@RequiresApi(Build.VERSION_CODES.M)
fun Activity.changeStatusBarColor(color: Int = R.color.white) {
    this.window?.statusBarColor = resources.getColor(color, null)
}

fun AppCompatActivity.hide() {
    this.supportActionBar!!.hide()
}

/*
fun AppCompatActivity.show() {
    this.supportActionBar!!.show()
}
*/


