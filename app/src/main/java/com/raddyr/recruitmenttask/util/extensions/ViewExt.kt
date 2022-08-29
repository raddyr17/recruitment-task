package com.raddyr.recruitmenttask.util.extensions

import android.R.color.holo_red_light
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.raddyr.recruitmenttask.R


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(R.string.close) { }
        .setActionTextColor(ContextCompat.getColor(this.context, holo_red_light))
        .show()
}