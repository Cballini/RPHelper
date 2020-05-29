package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class RecapEquipmentComponent@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var recapName : TextView
    var recapNameTxt : TextView
    var recapInfo : TextView
    var recapInfoTxt : TextView
    var recapWeight : TextView
    var recapWeightTxt : TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_recap_equipment, this, true)

        recapName = findViewById(R.id.recap_name)
        recapNameTxt = findViewById(R.id.recap_name_txt)
        recapInfo = findViewById(R.id.recap_info)
        recapInfoTxt = findViewById(R.id.recap_info_txt)
        recapWeight = findViewById(R.id.recap_weight)
        recapWeightTxt = findViewById(R.id.recap_weight_txt)
    }
}