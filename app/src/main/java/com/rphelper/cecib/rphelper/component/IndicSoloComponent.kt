package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class IndicSoloComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var indicSoloDrop : ImageView
    var indicSoloTitle : TextView
    var indicSoloCurrent : TextView
    var indicSoloBonus : TextView
    var indicSoloButtonsLayout : LinearLayout
    var indicSoloEditBonus : ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_indic_solo, this, true)

        indicSoloDrop = findViewById(R.id.indic_solo_drop)
        indicSoloTitle = findViewById(R.id.indic_solo_title)
        indicSoloCurrent = findViewById(R.id.indic_solo_current)
        indicSoloBonus = findViewById(R.id.indic_solo_bonus)
        indicSoloButtonsLayout =findViewById(R.id.indic_solo_buttons_layout)
        indicSoloEditBonus = findViewById(R.id.indic_solo_edit_bonus)
    }
}