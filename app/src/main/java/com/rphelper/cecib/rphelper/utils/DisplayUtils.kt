package com.rphelper.cecib.rphelper.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.rphelper.cecib.rphelper.R

object DisplayUtils{
    @JvmStatic
    fun hideKeyboard(view: View, context:Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}