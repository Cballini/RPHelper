package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class IndicComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){

    var indicTitle : TextView
    var indicCurrent : TextView
    var indicMax : TextView
    var indicEdit : ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_indic, this, true)

        indicTitle = findViewById(R.id.indic_title)
        indicCurrent = findViewById(R.id.indic_current)
        indicMax = findViewById(R.id.indic_max)
        indicEdit = findViewById(R.id.indic_edit)
    }
}