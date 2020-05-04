package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class IndicComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var indicDrop : ImageView
    var indicTitle : TextView
    var indicCurrent : EditText
    var indicMax : EditText
    var indicBonus : EditText
    var indicButtonsLayout : LinearLayout
    var indicReset : ImageView
    var indicEdit : ImageView
    var indicEditBonus : ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_indic, this, true)

        indicDrop = findViewById(R.id.indic_drop)
        indicTitle = findViewById(R.id.indic_title)
        indicCurrent = findViewById(R.id.indic_current)
        indicMax = findViewById(R.id.indic_max)
        indicBonus = findViewById(R.id.indic_bonus)
        indicButtonsLayout =findViewById(R.id.indic_buttons_layout)
        indicReset = findViewById(R.id.indic_reset)
        indicEdit = findViewById(R.id.indic_edit)
        indicEditBonus = findViewById(R.id.indic_edit_bonus)
    }
}