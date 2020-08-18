package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Fight
import com.rphelper.cecib.rphelper.utils.CalcUtils
import kotlin.math.absoluteValue

class FightViewModel(val context: Context) : ViewModel(){

    val _frost = MutableLiveData<Boolean>()
    val frost : LiveData<Boolean> get() = _frost
    init {
        _frost.value = Services.getFrost(context)
    }

    val _bleed = MutableLiveData<Int>()
    val bleed : LiveData<Int> get() = _bleed
    init {
        _bleed.value = Services.getBleed(context)
    }

    val _lastDamage = MutableLiveData<Int>()
    val lastDamage : LiveData<Int> get() = _lastDamage
    init {
        _lastDamage.value = getLastDamage()
    }

    val _posture = MutableLiveData<String>()
    val posture : LiveData<String> get() = _posture
    init {
        _posture.value = Services.getPosture(context)
    }

    val maxLife = CalcUtils.getLifeMax(context, Services.getCharacter(context))
    val maxMana = CalcUtils.getManaMax(context, Services.getCharacter(context))
    val maxConst = CalcUtils.getConstMax(context, Services.getCharacter(context))

    fun saveFight(){
        var damagesList = ArrayList<Int>()
        damagesList.add(lastDamage.value!!)
        val fight = Fight(damagesList, ArrayList(), ArrayList(), frost.value!!, bleed.value!!, posture.value!!)
        Services.editFight(context, fight)
        _frost.value = Services.getFrost(context)
        _bleed.value = Services.getBleed(context)
        _lastDamage.value = getLastDamage()
        _posture.value = Services.getPosture(context)
    }

    fun getDef():Int =CalcUtils.getDef(context)

    fun getBlock():Float{
        val equipment = Services.getEquipment(context)
        return equipment.shield.block
    }

    fun submit(damages:Int):Int{
        val char = Services.getCharacter(context)
        char.life.value -= damages
        Services.editCharacter(context, char)
        if(damages>=0) _lastDamage.value = damages
        saveFight()
        return char.life.value.toInt()
    }

    fun recoverLife(heal:Int) :Int = submit(-heal)
    fun recoverMana(heal:Int):Int{
        val char = Services.getCharacter(context)
        char.mana.value += heal
        Services.editCharacter(context, char)
        return char.mana.value.toInt()
    }
    fun recoverConst(heal:Int):Int{
        val char = Services.getCharacter(context)
        char.const.value += heal
        Services.editCharacter(context, char)
        return char.const.value.toInt()
    }

    fun attackOrBlock() : Int{
        val char = Services.getCharacter(context)
        char.const.value -= 80
        Services.editCharacter(context, char)
        return char.const.value.toInt()
    }

    fun dodge():Int{
        val char = Services.getCharacter(context)
        char.const.value -= 40
        Services.editCharacter(context, char)
        return char.const.value.toInt()
    }

    fun twin():Int{
        val char = Services.getCharacter(context)
        char.const.value -= 120
        Services.editCharacter(context, char)
        return char.const.value.toInt()
    }

    fun bleed(damages: Int):Int{
        val char = Services.getCharacter(context)
        char.life.value -= damages
        Services.editCharacter(context, char)
        _bleed.value = damages
        saveFight()
        return char.life.value.toInt()
    }

    fun getPoison():Int{
        val char = Services.getCharacter(context)
        val damages = (CalcUtils.getLifeMax(context, char)*0.05).toInt()
        char.life.value -= damages
        Services.editCharacter(context, char)
        return  return char.life.value.toInt()
    }

    fun frost() : Int{
        val char = Services.getCharacter(context)
        var maxConst = CalcUtils.getConstMax(context, char)
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_CONST_MAX, 0)
        val editor = sharedPref.edit()
        var value = 0
        if (_frost.value!!){ //no frost
            value = maxConst-prefValue.absoluteValue
            editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, value)
        }else{ //frost
            value = -(maxConst/2) + prefValue
            editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, value)
        }
        editor.apply()
        _frost.value = !frost.value!!
        Services.editCharacter(context, char)
        saveFight()
        return value
    }

    fun checkLife():Boolean{
        var check = false
        val char = Services.getCharacter(context)
        if(char.life.value<CalcUtils.getLifeMax(context, char)*0.2) check = true
        return check
    }
    fun checkConst(test : Int):Boolean{
        var check = false
        val char = Services.getCharacter(context)
        if(char.const.value<test) check = true
        return check
    }

    fun getLastDamage():Int{
        var last = 0
        val list = Services.getDamages(context)
        if (list.isNotEmpty()) last = list.get(0)
        return last
    }


}