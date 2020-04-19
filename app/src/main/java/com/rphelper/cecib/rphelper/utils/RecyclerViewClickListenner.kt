package com.rphelper.cecib.rphelper.utils

import android.view.View

interface RecyclerViewClickListener {
    fun onItemClicked(position: Int, v: View)
}