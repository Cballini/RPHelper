package com.rphelper.cecib.rphelper.utils

import android.content.Context
import android.content.SharedPreferences
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import kotlin.math.roundToInt

object CalcUtils {
    @JvmStatic
    fun getLifeMax(context: Context, character :Character) : Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_LIFE_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_LIFE_MAX, 0)
        return 200 + 20*character.vitality + prefValue
    }

    @JvmStatic
    fun getWeightMax(context: Context, character :Character) : Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEIGHT_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_WEIGHT_MAX, 0)
        return 40 + character.vigor + prefValue
    }

    @JvmStatic
    fun getWeight(context: Context, equipment:Equipment):Float{
        return (equipment.leftHand.weight + equipment.rightHand.weight + equipment.catalyst.weight + equipment.shield.weight
                + equipment.hat.weight + equipment.chest.weight + equipment.gloves.weight + equipment.greaves.weight)
    }

    @JvmStatic
    fun getSpeed(context: Context, character: Character, equipment: Equipment) = round1decimal(getWeightMax(context, character) - getWeight(context, equipment)).toFloat()

    @JvmStatic
    fun getManaMax(context: Context, character :Character) : Int {
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_MANA_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_MANA_MAX, 0)
        return 40 + 5*character.memory + prefValue
    }

    @JvmStatic
    fun getConstMax(context: Context, character :Character):Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_CONST_MAX, 0)
        return 60 + 20*character.endurance + prefValue
    }

    @JvmStatic
    fun round1decimal(number : Float) = Math.round(number * 10.0) / 10.0
}