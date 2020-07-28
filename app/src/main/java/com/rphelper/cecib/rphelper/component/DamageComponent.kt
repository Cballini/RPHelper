package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.utils.MultiRowsRadioGroup

class DamageComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var damageTitle : TextView
    var damageRadioGroup : MultiRowsRadioGroup
    var damageButton1 : RadioButton
    var damageButton2 : RadioButton
    var damageButton3 : RadioButton
    var damageButton4 : RadioButton
    var damageButton5 : RadioButton
    var damageButtonSecondLine : LinearLayout
    var damageReceived :EditText
    var damageEqual :TextView
    var damageResult :TextView
    var damageSubmit :Button

    init {
        LayoutInflater.from(context).inflate(R.layout.component_damage_calc, this, true)
        damageTitle=findViewById(R.id.damage_title)
        damageRadioGroup=findViewById(R.id.damage_radio_group)
        damageButton1=findViewById(R.id.damage_button1)
        damageButton2=findViewById(R.id.damage_button2)
        damageButton3=findViewById(R.id.damage_button3)
        damageButton4=findViewById(R.id.damage_button4)
        damageButton5=findViewById(R.id.damage_button5)
        damageButtonSecondLine = findViewById(R.id.damage_button_second_line)
        damageReceived=findViewById(R.id.damage_received)
        damageEqual=findViewById(R.id.damage_equal)
        damageResult=findViewById(R.id.damage_result)
        damageSubmit=findViewById(R.id.damage_submit)
    }
}