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

class FightViewModel(val context: Context) : ViewModel(){

    var firebaseQuery = Services.getUserDatabase()
    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?>? {
        return firebaseQuery
    }

    val _fight = MutableLiveData<Fight>()
    val fight : LiveData<Fight> get() = _fight
    init {
        _fight.value = Fight()
    }

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Character()
    }

    fun getLifeMax() = CalcUtils.getLifeMax(context, character.value!!)
    fun getManaMax() = CalcUtils.getManaMax(context, character.value!!)
    fun getConstMax() = CalcUtils.getConstMax(context, character.value!!)

    fun saveFight(){
        Services.editFight(fight.value!!)
    }

    fun getDef():Int =CalcUtils.getDef(context, Character(), Equipment()) //TODO pass real character and equipment

    fun getBlock():Float{
        val equipment = Services.getJsonEquipment(context)
        return equipment.shield.block
    }

    fun submit(damages:Int):Int{
        character.value!!.life.value -= damages
        Services.editCharacter(character.value!!)
        if(damages>=0) {
            var damagesList = ArrayList<Int>()
            damagesList.add(damages)
            fight.value!!.damage = damagesList
        }
        saveFight()
        return character.value!!.life.value.toInt()
    }

    fun recoverLife(heal:Int) :Int {
        var totalLife = character.value!!.life.value + heal
        if (totalLife>getLifeMax()) totalLife = getLifeMax().toFloat()
        character.value!!.life.value = totalLife
        Services.editCharacter(character.value!!)
        return totalLife.toInt()
    }
    fun recoverMana(heal:Int):Int{
        var totalMana = character.value!!.mana.value + heal
        if (totalMana>getManaMax()) totalMana = getManaMax().toFloat()
        character.value!!.mana.value = totalMana
        Services.editCharacter(character.value!!)
        return totalMana.toInt()
    }
    fun recoverConst(heal:Int):Int{
        var totalConst = character.value!!.const.value + heal
        if (totalConst>getConstMax()) totalConst = getConstMax().toFloat()
        character.value!!.const.value = totalConst
        Services.editCharacter(character.value!!)
        return totalConst.toInt()
    }

    fun attackOrBlock() : Int{
        character.value!!.const.value -= 80
        Services.editCharacter(character.value!!)
        return character.value!!.const.value.toInt()
    }

    fun attack2Hands() : Int{
        character.value!!.const.value -= 40
        Services.editCharacter(character.value!!)
        return character.value!!.const.value.toInt()
    }

    fun dodge():Int{
        character.value!!.const.value -= 40
        Services.editCharacter(character.value!!)
        return character.value!!.const.value.toInt()
    }

    fun twin():Int{
        character.value!!.const.value -= 120
        Services.editCharacter(character.value!!)
        return character.value!!.const.value.toInt()
    }

    fun bleed(damages: Int):Int{
        character.value!!.life.value -= damages
        Services.editCharacter(character.value!!)
        fight.value!!.bleed = damages
        saveFight()
        return character.value!!.life.value.toInt()
    }

    fun getPoison():Int{
        val damages = (CalcUtils.getLifeMax(context, character.value!!)*0.05).toInt()
        character.value!!.life.value -= damages
        Services.editCharacter(character.value!!)
        return character.value!!.life.value.toInt()
    }

    fun frost() : Int{
        var maxConst = CalcUtils.getConstMaxWhithoutModif(character.value!!)
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX, Preferences.PRIVATE_MODE)
        val sharedPrefTemp: SharedPreferences = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, Preferences.PRIVATE_MODE)
        val prefValueTemp = sharedPrefTemp.getInt(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, 0)
        val editor = sharedPref.edit()
        var value = 0
        if (fight.value!!.frost){ //cancel frost
            editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, 0)
        }else{ //frost
            value = -((maxConst + prefValueTemp)/2)
            editor.putInt(Preferences.PREF_MODIFIER_CONST_MAX, value)
        }
        editor.apply()
        fight.value!!.frost = !fight.value!!.frost
        if (character.value!!.const.value>CalcUtils.getConstMax(context, character.value!!)){
            character.value!!.const.value = value.absoluteValue.toFloat()
            Services.editCharacter(character.value!!)
        }
        saveFight()
        return value
    }

    fun checkLife():Boolean{
        var check = false
        if(character.value!!.life.value<CalcUtils.getLifeMax(context, character.value!!)*0.2) check = true
        return check
    }
    fun checkConst(test : Int):Boolean{
        var check = false
        if(character.value!!.const.value<test) check = true
        return check
    }

    fun getLastDamage():Int{
        var last = 0
        val list = fight.value!!.damage
        if (list.isNotEmpty()) last = list.get(0)
        return last
    }


}