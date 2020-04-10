package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class CategoryHorizontalComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){

    var catTitle :TextView
    var catTxt :TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_category_horizontal, this, true)

        catTitle = findViewById(R.id.cat_title)
        catTxt = findViewById(R.id.cat_txt)
    }
}