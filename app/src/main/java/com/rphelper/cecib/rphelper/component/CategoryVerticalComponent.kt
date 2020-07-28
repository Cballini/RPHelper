package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rphelper.cecib.rphelper.R

class CategoryVerticalComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){

    var catVerticalTitle : TextView
    var catVerticalCurrent : EditText
    var catVerticalEdit : ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_category_vertical, this, true)

        catVerticalTitle = findViewById(R.id.cat_vertical_title)
        catVerticalCurrent = findViewById(R.id.cat_vertical_current)
        catVerticalEdit = findViewById(R.id.cat_vertical_edit)
    }
}