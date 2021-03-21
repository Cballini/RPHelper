package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rphelper.cecib.rphelper.R

class DonComponent@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    var donTitle:TextView
    var donRecycler:RecyclerView
    var donAdd: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_don, this, true)

        donTitle = findViewById(R.id.don_title)
        donRecycler = findViewById(R.id.don_recycler)
        donAdd = findViewById(R.id.don_add)
    }
}