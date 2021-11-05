package dev.yc.githubusersearcher.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val windowToken = window.decorView.windowToken
    imm.hideSoftInputFromWindow(windowToken, 0)
}