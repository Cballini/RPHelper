package com.rphelper.cecib.rphelper.utils

import android.content.Context
import android.content.SharedPreferences
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Weapon
import kotlin.math.roundToInt

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
    fun getDef(context: Context) : Int {
        val character = Services.getCharacter(context)
        val equipment = Services.getEquipment(context)
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
    fun getDamages(context: Context):Int{
        val character = Services.getCharacter(context)
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DAMAGES, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_DAMAGES, 0)
        val sharedPref2: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DAMAGES_TEMP, Preferences.PRIVATE_MODE)
        val prefValue2 = sharedPref2.getInt(Preferences.PREF_MODIFIER_DAMAGES_TEMP, 0)
        return 90 + 2*character.strength + character.dexterity + prefValue + prefValue2
    }

    @JvmStatic
    fun getTotalDamage(weapon: Weapon, context: Context):Int{
        val character = Services.getCharacter(context)
        var dmg = getDamages(context) + weapon.damage
        dmg += (character.strength*weapon.bonusFor.value + character.dexterity*weapon.bonusDex.value).toInt()
        if (weapon.rapidFire) {
            val dmgTot = dmg
            dmg = dmgTot + dmgTot/2 + dmgTot/4
        }
        return dmg
    }

    @JvmStatic
    fun round1decimal(number : Float) = Math.round(number * 10.0) / 10.0
}