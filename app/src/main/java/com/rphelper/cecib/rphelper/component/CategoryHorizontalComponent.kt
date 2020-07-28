package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rphelper.cecib.rphelper.R

class CategoryHorizontalComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){

    var catTitle :TextView
    var catTxt :EditText

    init {
        LayoutInflater.from(context).inflate(R.layout.component_category_horizontal, this, true)

        catTitle = findViewById(R.id.cat_title)
        catTxt = findViewById(R.id.cat_txt)
    }
}