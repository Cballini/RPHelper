package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class DamageComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var damageTitle : TextView
    var damageButton1 : RadioButton
    var damageButton2 : RadioButton
    var damageButton3 : RadioButton
    var damageButton4 : RadioButton
    var damageReceived :EditText
    var damageResult :TextView
    var damageSubmit :Button

    init {
        LayoutInflater.from(context).inflate(R.layout.component_damage_calc, this, true)
        damageTitle=findViewById(R.id.damage_title)
        damageButton1=findViewById(R.id.damage_button1)
        damageButton2=findViewById(R.id.damage_button2)
        damageButton3=findViewById(R.id.damage_button3)
        damageButton4=findViewById(R.id.damage_button4)
        damageReceived=findViewById(R.id.damage_received)
        damageResult=findViewById(R.id.damage_result)
        damageSubmit=findViewById(R.id.damage_submit)
    }
}