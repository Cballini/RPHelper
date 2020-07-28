package com.rphelper.cecib.rphelper.component

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rphelper.cecib.rphelper.R

class LineItemComponent @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs){
    var lineItemIcon : ImageView
    var lineItemName : TextView
    var lineItemNote : TextView
    var lineItemEquip : LinearLayout
    var lineItemEquipImg : ImageView
    var lineItemWeightLayout :LinearLayout
    var lineItemWeight :TextView
    var lineItemQuantityLayout :LinearLayout
    var lineItemQuantity : TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.component_line_item, this, true)

        lineItemIcon = findViewById(R.id.line_item_icon)
        lineItemName = findViewById(R.id.line_item_name)
        lineItemNote = findViewById(R.id.line_item_note)
        lineItemEquip = findViewById(R.id.line_item_equip)
        lineItemEquipImg = findViewById(R.id.line_item_equip_img)
        lineItemWeightLayout = findViewById(R.id.line_item_weight_layout)
        lineItemWeight = findViewById(R.id.line_item_weight_txt)
        lineItemQuantityLayout = findViewById(R.id.line_item_quantity_layout)
        lineItemQuantity = findViewById(R.id.line_item_quantity_txt)
    }
}