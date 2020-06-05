package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Fight
import com.rphelper.cecib.rphelper.utils.CalcUtils

class FightViewModel(val context: Context) :ViewModel(){

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

    fun submit(damages:Int){
        val char = Services.getCharacter(context)
        char.life.value -= damages
        Services.editCharacter(context, char)
        if(damages>=0) _lastDamage.value = damages
        saveFight()
    }

    fun recoverLife(heal:Int) = submit(-heal)
    fun recoverMana(heal:Int){
        val char = Services.getCharacter(context)
        char.mana.value += heal
        Services.editCharacter(context, char)
    }
    fun recoverConst(heal:Int){
        val char = Services.getCharacter(context)
        char.const.value += heal
        Services.editCharacter(context, char)
    }

    fun attackOrBlock() : Int{
        val char = Services.getCharacter(context)
        char.const.value -= 80
        Services.editCharacter(context, char)
        return 80
    }

    fun dodge():Int{
        val char = Services.getCharacter(context)
        char.const.value -= 40
        Services.editCharacter(context, char)
        return 30
    }

    fun twin():Int{
        val char = Services.getCharacter(context)
        char.const.value -= 120
        Services.editCharacter(context, char)
        return 120
    }

    fun bleed(damages: Int){
        val char = Services.getCharacter(context)
        char.life.value -= damages
        Services.editCharacter(context, char)
        _bleed.value = damages
        saveFight()
    }

    fun getPoison():Int{
        val char = Services.getCharacter(context)
        val damages = (CalcUtils.getLifeMax(context, char)*0.05).toInt()
        char.life.value -= damages
        Services.editCharacter(context, char)
        return damages
    }

    fun frost(){
        val char = Services.getCharacter(context)
        var maxConst = CalcUtils.getConstMax(context, char)
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_CONST_MAX, 0)
        val editor = sharedPref.edit()
        if (_frost.value!!){ //no frost
            var value = maxConst+prefValue
            editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, value)
        }else{ //frost
            var value = -(maxConst/2) + prefValue
            editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, value)
        }
        editor.apply()
        _frost.value = !frost.value!!
        Services.editCharacter(context, char)
        saveFight()
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