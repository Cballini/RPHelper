package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.utils.CalcUtils

class FightViewModel(val context: Context) :ViewModel(){

    fun getDef():Float{
        val character = Services.getCharacter(context)
        val equipment = Services.getEquipment(context)
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DEFENSE, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_DEFENSE, 0)
        var def = 50F + prefValue
        def += equipment.hat.def + equipment.chest.def + equipment.gloves.def + equipment.greaves.def
        def += (character.vitality/2 + character.memory/2 + character.endurance/2 + character.vigor
                + character.strength/2 + character.dexterity/2 + character.intelligence/2 + character.faith/2)
        return def
    }

    fun getBlock():Float{
        val equipment = Services.getEquipment(context)
        return equipment.shield.block
    }

    fun submit(damages:Int){
        val char = Services.getCharacter(context)
        char.life.value -= damages
        val maxLife = CalcUtils.getLifeMax(context, char)
        if (char.life.value>maxLife) char.life.value = maxLife.toFloat()
        Services.editCharacter(context, char)
    }

    fun recoverLife(heal:Int) = submit(-heal)
    fun recoverMana(heal:Int){
        val char = Services.getCharacter(context)
        char.mana.value += heal
        val maxMana = CalcUtils.getManaMax(context, char)
        if (char.mana.value>maxMana) char.mana.value = maxMana.toFloat()
        Services.editCharacter(context, char)
    }
    fun recoverConst(heal:Int){
        val char = Services.getCharacter(context)
        char.const.value += heal
        val maxConst = CalcUtils.getConstMax(context, char)
        if (char.const.value>maxConst) char.const.value = maxConst.toFloat()
        Services.editCharacter(context, char)
    }
}