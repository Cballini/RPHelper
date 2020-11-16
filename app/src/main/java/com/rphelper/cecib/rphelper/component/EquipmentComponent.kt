package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rphelper.cecib.rphelper.R

class EquipmentComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    var equipmentTypeLayout : LinearLayout
    var equipmentType : TextView

    var equipmentNameLayout : LinearLayout
    var equipmentName : TextView
    var equipmentInHand : CheckBox

    var equipmentFirstPanel :LinearLayout
    var equipmentFirstPanelTitle : TextView
    var equipmentFirstPanelTxt : TextView
    var equipmentSecondPanel :LinearLayout
    var equipmentSecondPanelTitle : TextView
    var equipmentSecondPanelFire : ImageView
    var equipmentSecondPanelMagic : ImageView
    var equipmentSecondPanelLight : ImageView
    var equipmentSecondPanelDark : ImageView

    var equipmentSecondLine : LinearLayout
    var equipmentThirdPanel :LinearLayout
    var equipmentThirdPanelTitle : TextView
    var equipmentThirdPanelTxt : TextView
    var equipmentThirdPanelPoison : ImageView
    var equipmentThirdPanelFrost : ImageView
    var equipmentThirdPanelBleed : ImageView
    var equipmentFourthPanel : LinearLayout
    var equipmentFourthPanelTitle : TextView
    var equipmentFourthPanelFire : ImageView
    var equipmentFourthPanelMagic : ImageView
    var equipmentFourthPanelLight : ImageView
    var equipmentFourthPanelDark : ImageView

    var equipmentBonusLayout : LinearLayout
    var equipmentBonusForLayout : LinearLayout
    var equipmentBonusForTxt : TextView
    var equipmentBonusDexLayout : LinearLayout
    var equipmentBonusDexTxt : TextView
    var equipmentBonusIntLayout : LinearLayout
    var equipmentBonusIntTxt : TextView
    var equipmentBonusFoiLayout : LinearLayout
    var equipmentBonusFoiTxt : TextView

    var equipmentLargePanelLayout : LinearLayout
    var equipmentLargePanelTxt : TextView

    var equipmentWeightTxt : TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_equipment, this, true)
        equipmentTypeLayout = findViewById(R.id.equipment_type_layout)
        equipmentType = findViewById(R.id.equipment_type)

        equipmentNameLayout = findViewById(R.id.equipment_name_layout)
        equipmentName = findViewById(R.id.equipment_name)
        equipmentInHand = findViewById(R.id.equipment_in_hand_checkbox)

        equipmentFirstPanel = findViewById(R.id.equipment_panel_first_panel)
        equipmentFirstPanelTitle = findViewById(R.id.equipment_panel_first_panel_title)
        equipmentFirstPanelTxt = findViewById(R.id.equipment_panel_first_panel_txt)
        equipmentSecondPanel = findViewById(R.id.equipment_panel_second_panel)
        equipmentSecondPanelTitle = findViewById(R.id.equipment_panel_second_panel_title)
        equipmentSecondPanelFire = findViewById(R.id.equipment_panel_second_panel_fire)
        equipmentSecondPanelMagic = findViewById(R.id.equipment_panel_second_panel_magic)
        equipmentSecondPanelLight = findViewById(R.id.equipment_panel_second_panel_light)
        equipmentSecondPanelDark = findViewById(R.id.equipment_panel_second_panel_dark)

        equipmentSecondLine = findViewById(R.id.equipment_panel_second_line)
        equipmentThirdPanel = findViewById(R.id.equipment_panel_third_panel)
        equipmentThirdPanelTitle = findViewById(R.id.equipment_panel_third_panel_title)
        equipmentThirdPanelTxt = findViewById(R.id.equipment_panel_third_panel_txt)
        equipmentThirdPanelPoison = findViewById(R.id.equipment_panel_third_panel_poison)
        equipmentThirdPanelFrost = findViewById(R.id.equipment_panel_third_panel_frost)
        equipmentThirdPanelBleed = findViewById(R.id.equipment_panel_third_panel_bleed)
        equipmentFourthPanel = findViewById(R.id.equipment_panel_fourth_panel)
        equipmentFourthPanelTitle = findViewById(R.id.equipment_panel_fourth_panel_title)
        equipmentFourthPanelFire = findViewById(R.id.equipment_panel_fourth_panel_fire)
        equipmentFourthPanelMagic = findViewById(R.id.equipment_panel_fourth_panel_magic)
        equipmentFourthPanelLight = findViewById(R.id.equipment_panel_fourth_panel_light)
        equipmentFourthPanelDark = findViewById(R.id.equipment_panel_fourth_panel_dark)

        equipmentBonusLayout = findViewById(R.id.equipment_bonus_layout)
        equipmentBonusForLayout = findViewById(R.id.equipment_bonus_for_layout)
        equipmentBonusForTxt = findViewById(R.id.equipment_bonus_for_txt)
        equipmentBonusDexLayout = findViewById(R.id.equipment_bonus_dex_layout)
        equipmentBonusDexTxt = findViewById(R.id.equipment_bonus_dex_txt)
        equipmentBonusIntLayout = findViewById(R.id.equipment_bonus_int_layout)
        equipmentBonusIntTxt = findViewById(R.id.equipment_bonus_int_txt)
        equipmentBonusFoiLayout = findViewById(R.id.equipment_bonus_foi_layout)
        equipmentBonusFoiTxt = findViewById(R.id.equipment_bonus_foi_txt)

        equipmentLargePanelLayout= findViewById(R.id.equipment_large_panel)
        equipmentLargePanelTxt = findViewById(R.id.equipment_large_panel_txt)

        equipmentWeightTxt = findViewById(R.id.equipment_weight_txt)
    }
}