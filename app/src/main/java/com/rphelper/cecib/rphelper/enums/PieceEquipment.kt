package com.rphelper.cecib.rphelper.enums

import android.content.Context
import com.rphelper.cecib.rphelper.R

enum class PieceEquipment(val id :Int) {
    NOTHING (0),
    HAT (1),
    CHEST (2),
    GLOVES (3),
    GREAVES (4);

    companion object {
        @JvmStatic
        fun getPieceString(piece : PieceEquipment, context: Context) : String{
            return when(piece){
                HAT -> context.getString(R.string.hat)
                CHEST -> context.getString(R.string.chestplate)
                GLOVES -> context.getString(R.string.gloves)
                GREAVES -> context.getString(R.string.greaves)
                else -> context.getString(R.string.armor)
            }
        }
    }
}