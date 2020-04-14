package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class DamageComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var damageType :TextView
    var damageReceived :EditText
    var damageResult :TextView
    var damageButton :TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_damage_calc, this, true)

        damageType=findViewById(R.id.damage_type)
        damageReceived=findViewById(R.id.damage_received)
        damageResult=findViewById(R.id.damage_result)
        damageButton=findViewById(R.id.damage_button)
    }
}