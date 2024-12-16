package com.example.umc_wireframe.util

import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

internal fun View.hideSoftKeyboard() {
    val inputMethodManager = ContextCompat.getSystemService(context, InputMethodManager::class.java)
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}