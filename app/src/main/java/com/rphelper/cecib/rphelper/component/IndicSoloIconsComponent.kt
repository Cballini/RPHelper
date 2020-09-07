package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rphelper.cecib.rphelper.R

class IndicSoloIconsComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var indicSoloIconsDrop : ImageView
    var indicSoloIconsTitle : TextView

    var indicSoloIconsFire :ImageView
    var indicSoloIconsMagic :ImageView
    var indicSoloIconsLight :ImageView
    var indicSoloIconsDark :ImageView
    var indicSoloIconsPoison :ImageView
    var indicSoloIconsFrost :ImageView
    var indicSoloIconsBleed :ImageView

    var indicSoloIconsBonusFire :ImageView
    var indicSoloIconsBonusMagic :ImageView
    var indicSoloIconsBonusLight :ImageView
    var indicSoloIconsBonusDark :ImageView
    var indicSoloIconsBonusPoison :ImageView
    var indicSoloIconsBonusFrost :ImageView
    var indicSoloIconsBonusBleed :ImageView

    var indicSoloIconsButtonsLayout : LinearLayout
    var indicSoloIconsEditBonus : ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_indic_solo_icons, this, true)

        indicSoloIconsDrop = findViewById(R.id.indic_solo_icons_drop)
        indicSoloIconsTitle = findViewById(R.id.indic_solo_icons_title)

        indicSoloIconsFire = findViewById(R.id.indic_solo_icons_fire)
        indicSoloIconsMagic = findViewById(R.id.indic_solo_icons_magic)
        indicSoloIconsLight = findViewById(R.id.indic_solo_icons_light)
        indicSoloIconsDark = findViewById(R.id.indic_solo_icons_dark)
        indicSoloIconsPoison = findViewById(R.id.indic_solo_icons_poison)
        indicSoloIconsFrost = findViewById(R.id.indic_solo_icons_frost)
        indicSoloIconsBleed = findViewById(R.id.indic_solo_icons_bleed)

        indicSoloIconsBonusFire = findViewById(R.id.indic_solo_icons_bonus_fire)
        indicSoloIconsBonusMagic = findViewById(R.id.indic_solo_icons_bonus_magic)
        indicSoloIconsBonusLight = findViewById(R.id.indic_solo_icons_bonus_light)
        indicSoloIconsBonusDark = findViewById(R.id.indic_solo_icons_bonus_dark)
        indicSoloIconsBonusPoison = findViewById(R.id.indic_solo_icons_bonus_poison)
        indicSoloIconsBonusFrost = findViewById(R.id.indic_solo_icons_bonus_frost)
        indicSoloIconsBonusBleed = findViewById(R.id.indic_solo_icons_bonus_bleed)

        indicSoloIconsButtonsLayout =findViewById(R.id.indic_solo_icons_buttons_layout)
        indicSoloIconsEditBonus = findViewById(R.id.indic_solo_icons_edit_bonus)
    }
}