package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rphelper.cecib.rphelper.R

class LineSpellComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    var lineSpellName: TextView
    var lineSpellNote: TextView
    var lineSpellDamageTxt: TextView
    var lineSpellManaTxt: TextView
    var lineSpellUse: TextView
    var lineSpellUse2: TextView
    var lineSpellUseTxt: TextView
    var lineSpellUseTxt2: TextView
    var lineSpellUseLayout2: LinearLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.component_line_spell, this, true)
        lineSpellName = findViewById(R.id.line_spell_name)
        lineSpellNote = findViewById(R.id.line_spell_note)
        lineSpellDamageTxt = findViewById(R.id.line_spell_damage_txt)
        lineSpellManaTxt = findViewById(R.id.line_spell_mana_txt)
        lineSpellUse = findViewById(R.id.line_spell_use)
        lineSpellUse2 = findViewById(R.id.line_spell_use2)
        lineSpellUseTxt = findViewById(R.id.line_spell_use_txt)
        lineSpellUseTxt2 = findViewById(R.id.line_spell_use_txt)
        lineSpellUseLayout2 = findViewById(R.id.line_spell_use_layout2)
    }
}