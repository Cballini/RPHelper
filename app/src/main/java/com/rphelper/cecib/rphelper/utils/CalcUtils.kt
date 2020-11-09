package com.rphelper.cecib.rphelper.utils

import android.content.Context
import android.content.SharedPreferences
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Weapon

object CalcUtils {
    @JvmStatic
    fun getLifeMax(context: Context, character :Character) : Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_LIFE_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_LIFE_MAX, 0)
        val sharedPref2: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_LIFE_MAX_TEMP, Preferences.PRIVATE_MODE)
        val prefValue2 = sharedPref2.getInt(Preferences.PREF_MODIFIER_LIFE_MAX_TEMP, 0)
        return 200 + 20*character.vitality + prefValue + prefValue2
    }

    @JvmStatic
    fun getWeightMax(context: Context, character :Character) : Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEIGHT_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_WEIGHT_MAX, 0)
        val sharedPref2: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEIGHT_MAX_TEMP, Preferences.PRIVATE_MODE)
        val prefValue2 = sharedPref2.getInt(Preferences.PREF_MODIFIER_WEIGHT_MAX_TEMP, 0)
        return 40 + character.vigor + prefValue + prefValue2
    }

    @JvmStatic
    fun getWeight(equipment:Equipment):Float{
        return round1decimal(equipment.leftHand.weight + equipment.rightHand.weight + equipment.catalyst.weight + equipment.shield.weight
                + equipment.hat.weight + equipment.chest.weight + equipment.gloves.weight + equipment.greaves.weight).toFloat()
    }

    @JvmStatic
    fun getSpeed(context: Context, character: Character, equipment: Equipment) = round1decimal(getWeightMax(context, character) - getWeight(equipment)).toFloat()

    @JvmStatic
    fun getManaMax(context: Context, character :Character) : Int {
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_MANA_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_MANA_MAX, 0)
        val sharedPref2: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_MANA_MAX_TEMP, Preferences.PRIVATE_MODE)
        val prefValue2 = sharedPref2.getInt(Preferences.PREF_MODIFIER_MANA_MAX_TEMP, 0)
        return 40 + 5*character.memory + prefValue + prefValue2
    }

    @JvmStatic
    fun getConstMax(context: Context, character :Character):Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_CONST_MAX, 0)
        val sharedPref2: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, Preferences.PRIVATE_MODE)
        val prefValue2 = sharedPref2.getInt(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, 0)
        return 60 + 20*character.endurance + prefValue + prefValue2
    }

    @JvmStatic
    fun getConstMaxWhithoutModif(character :Character):Int = 60 + 20*character.endurance

    @JvmStatic
    fun changeConstMaxModifier(context: Context, character: Character, prefValueTemp : Int) :Int{
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX, Preferences.PRIVATE_MODE)
        val editor = sharedPref.edit()
        var maxConst = CalcUtils.getConstMaxWhithoutModif(character)
        val value = -((maxConst + prefValueTemp)/2)
        editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, value)
        editor.apply()
        return value
    }


    @JvmStatic
    fun getDef(context: Context, character: Character, equipment: Equipment) : Int {
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DEFENSE, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_DEFENSE, 0)
        val sharedPref2: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DEFENSE_TEMP, Preferences.PRIVATE_MODE)
        val prefValue2 = sharedPref2.getInt(Preferences.PREF_MODIFIER_DEFENSE_TEMP, 0)
        var def = 50F + prefValue + prefValue2
        def += equipment.hat.def + equipment.chest.def + equipment.gloves.def + equipment.greaves.def
        def += (character.vitality.toFloat()/2 + character.memory.toFloat()/2 + character.endurance.toFloat()/2 + character.vigor
                + character.strength.toFloat()/2 + character.dexterity.toFloat()/2 + character.intelligence.toFloat()/2 + character.faith.toFloat()/2)
        return def.toInt()
    }

    @JvmStatic
    fun getDamages(context: Context, character: Character):Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DAMAGES, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_DAMAGES, 0)
        val sharedPref2: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DAMAGES_TEMP, Preferences.PRIVATE_MODE)
        val prefValue2 = sharedPref2.getInt(Preferences.PREF_MODIFIER_DAMAGES_TEMP, 0)
        return 90 + 2*character.strength + character.dexterity + prefValue + prefValue2
    }

    @JvmStatic
    fun getTotalDamage(weapon: Weapon, context: Context, character: Character):Int{
        var dmg = getDamages(context, character) + weapon.damage
        dmg += (character.strength*weapon.bonusFor.value + character.dexterity*weapon.bonusDex.value).toInt()
        if (weapon.rapidFire) {
            val dmgTot = dmg
            dmg = dmgTot + dmgTot/2 + dmgTot/4
        }
        return dmg
    }

    @JvmStatic
    fun reinitStuffPref(context: Context){
        reinitJewelPref(context, Preferences.PREF_MODIFIER_LIFE_MAX)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_MANA_MAX)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_CONST_MAX)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_WEIGHT_MAX)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_DAMAGES)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_DEFENSE)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_VIT)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_VIG)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_FOR)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_DEX)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_END)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_MEM)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_INT)
        reinitJewelPref(context, Preferences.PREF_MODIFIER_FOI)
    }

    fun reinitJewelPref(context: Context, pref : String){
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
        val editor = sharedPref.edit()
        editor.putInt(pref, 0)
        editor.apply()
    }

    @JvmStatic
    fun round1decimal(number : Float) = Math.round(number * 10.0) / 10.0
}