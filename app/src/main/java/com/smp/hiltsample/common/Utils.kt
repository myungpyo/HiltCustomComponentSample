package com.smp.hiltsample.common

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {
    private val mainHandler = Handler(Looper.getMainLooper())

    fun showSoftKeyboard(view: View) {
        mainHandler.postDelayed({
            (view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
                view.requestFocus()
                it.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }, 100)
    }
}