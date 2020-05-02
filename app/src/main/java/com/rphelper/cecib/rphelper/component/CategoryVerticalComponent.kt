package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class CategoryVerticalComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){

    var indicTitle : TextView
    var indicCurrent : EditText
    var indicMax : EditText
    var indicEdit : ImageView
    var indicReload :ImageView

    var indicSpare : TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_category_vertical, this, true)

        indicTitle = findViewById(R.id.indic_title)
        indicCurrent = findViewById(R.id.indic_current)
        indicMax = findViewById(R.id.indic_max)
        indicEdit = findViewById(R.id.indic_edit)
        indicReload = findViewById(R.id.indic_reload)
        indicSpare= findViewById(R.id.indic_spare)
    }
}