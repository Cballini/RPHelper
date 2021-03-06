package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rphelper.cecib.rphelper.R

class SpellComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var spellPlaceLayout : LinearLayout
    var spellPlace : TextView
    var spellName : TextView
    var spellDamage : TextView
    var spellMana : TextView
    var spellTotal : TextView
    var spellEffect : TextView
    var spellUse : TextView
    var spellUseValue : TextView
    var spellUseLayout2 : LinearLayout
    var spellUse2 : TextView
    var spellUseValue2 : TextView

    var spellButton : Button

    init {
        LayoutInflater.from(context).inflate(R.layout.component_spell, this, true)
        spellPlaceLayout = findViewById(R.id.spell_place_layout)
        spellPlace = findViewById(R.id.spell_place)
        spellName = findViewById(R.id.spell_name)
        spellDamage = findViewById(R.id.spell_damage_value)
        spellMana = findViewById(R.id.spell_mana_value)
        spellTotal = findViewById(R.id.spell_total_value)
        spellEffect = findViewById(R.id.spell_effect)
        spellUse = findViewById(R.id.spell_use)
        spellUseValue = findViewById(R.id.spell_use_value)
        spellUseLayout2 = findViewById(R.id.spell_use_layout2)
        spellUse2 = findViewById(R.id.spell_use2)
        spellUseValue2 = findViewById(R.id.spell_use_value2)

        spellButton = findViewById(R.id.spell_button)
    }
}