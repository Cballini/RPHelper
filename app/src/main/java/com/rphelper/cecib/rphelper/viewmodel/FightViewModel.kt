package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Fight
import com.rphelper.cecib.rphelper.utils.CalcUtils
import kotlin.math.absoluteValue

class FightViewModel(val context: Context, fight: Fight, character: Character, equipment: Equipment) : ViewModel(){

    var fight = fight
    var character = character
    var equipment = equipment

    fun getLifeMax() = CalcUtils.getLifeMax(context, character)
    fun getManaMax() = CalcUtils.getManaMax(context, character)
    fun getConstMax() = CalcUtils.getConstMax(context, character)

    fun saveFight(){
        Services.editFight(fight)
    }

    fun getDef():Int =CalcUtils.getDef(context, character, equipment)

    fun getBlock():Float{
        val equipment = Services.getJsonEquipment(context)
        return equipment.shield.block
    }

    fun submit(damages:Int):Int{
        character.life.value -= damages
        Services.editCharacter(character)
        if(damages>=0) {
            var damagesList = ArrayList<Int>()
            damagesList.add(damages)
            fight.damage = damagesList
        }
        saveFight()
        return character.life.value.toInt()
    }

    fun recoverLife(heal:Int) :Int {
        var totalLife = character.life.value + heal
        if (totalLife>getLifeMax()) totalLife = getLifeMax().toFloat()
        character.life.value = totalLife
        Services.editCharacter(character)
        return totalLife.toInt()
    }
    fun recoverMana(heal:Int):Int{
        var totalMana = character.mana.value + heal
        if (totalMana>getManaMax()) totalMana = getManaMax().toFloat()
        character.mana.value = totalMana
        Services.editCharacter(character)
        return totalMana.toInt()
    }
    fun recoverConst(heal:Int):Int{
        var totalConst = character.const.value + heal
        if (totalConst>getConstMax()) totalConst = getConstMax().toFloat()
        character.const.value = totalConst
        Services.editCharacter(character)
        return totalConst.toInt()
    }

    fun attackOrBlock() : Int{
        character.const.value -= 80
        Services.editCharacter(character)
        return character.const.value.toInt()
    }

    fun attack2Hands() : Int{
        character.const.value -= 40
        Services.editCharacter(character)
        return character.const.value.toInt()
    }

    fun dodge():Int{
        character.const.value -= 40
        Services.editCharacter(character)
        return character.const.value.toInt()
    }

    fun twin():Int{
        character.const.value -= 120
        Services.editCharacter(character)
        return character.const.value.toInt()
    }

    fun bleed(damages: Int):Int{
        character.life.value -= damages
        Services.editCharacter(character)
        fight.bleed = damages
        saveFight()
        return character.life.value.toInt()
    }

    fun getPoison():Int{
        val damages = (CalcUtils.getLifeMax(context, character)*0.05).toInt()
        character.life.value -= damages
        Services.editCharacter(character)
        return character.life.value.toInt()
    }

    fun frost() : Int{
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX, Preferences.PRIVATE_MODE)
        val sharedPrefTemp: SharedPreferences = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, Preferences.PRIVATE_MODE)
        val prefValueTemp = sharedPrefTemp.getInt(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, 0)
        val editor = sharedPref.edit()
        var value = 0
        if (fight.frost){ //cancel frost
            editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, 0)
            editor.apply()
        }else{ //frost
            value = CalcUtils.changeConstMaxModifier(context, character, prefValueTemp)
        }

        fight.frost = !fight.frost
        if (character.const.value>CalcUtils.getConstMax(context, character)){
            character.const.value = value.absoluteValue.toFloat()
            Services.editCharacter(character)
        }
        saveFight()
        return value
    }

    fun checkLife():Boolean{
        var check = false
        if(character.life.value<CalcUtils.getLifeMax(context, character)*0.2) check = true
        return check
    }
    fun checkConst(test : Int):Boolean{
        var check = false
        if(character.const.value<test) check = true
        return check
    }

    fun getLastDamage():Int{
        var last = 0
        val list = fight.damage
        if (list.isNotEmpty()) last = list.get(0)
        return last
    }


}