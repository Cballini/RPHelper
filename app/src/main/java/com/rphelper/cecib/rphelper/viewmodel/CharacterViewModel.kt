package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.*
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_CONST_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_LIFE_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_MANA_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_WEIGHT_MAX
import com.rphelper.cecib.rphelper.Preferences.PRIVATE_MODE
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Indic
import com.rphelper.cecib.rphelper.utils.FileUtils

class CharacterViewModel(val context: Context) : ViewModel(){

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Services.getCharacter(context)
    }

    val _weightMax = MutableLiveData<Int>()
    val weightMax : LiveData<Int> get() = _weightMax
    init {
        _weightMax.value = getWeightMax()
    }

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value = getWeight()
    }

    val _lifeMax = MutableLiveData<Int>()
    val lifeMax : LiveData<Int> get() = _lifeMax
    init {
        _lifeMax.value = getLifeMax()
    }

    val _manaMax = MutableLiveData<Int>()
    val manaMax : LiveData<Int> get() = _manaMax
    init {
        _manaMax.value = getManaMax()
    }

    val _constMax = MutableLiveData<Int>()
    val constMax : LiveData<Int> get() = _constMax
    init {
        _constMax.value = getConstMax()
    }

    val _speed = MutableLiveData<Float>()
    val speed : LiveData<Float> get() = _speed
    init {
        _speed.value = getSpeed()
    }

    val _diplo = MutableLiveData<Int>()
    val diplo : LiveData<Int> get() = _diplo
    init {
        _diplo.value = getDiplo()
    }

    val _psy = MutableLiveData<Int>()
    val psy : LiveData<Int> get() = _psy
    init {
        _psy.value = getPsy()
    }

    val _know = MutableLiveData<Int>()
    val know : LiveData<Int> get() = _know
    init {
        _know.value = getKnow()
    }

    val _push = MutableLiveData<Int>()
    val push : LiveData<Int> get() = _push
    init {
        _push.value = getPush()
    }

    val _sneak = MutableLiveData<Int>()
    val sneak : LiveData<Int> get() = _sneak
    init {
        _sneak.value = getSneak()
    }

    val _craft = MutableLiveData<Int>()
    val craft : LiveData<Int> get() = _craft
    init {
        _craft.value = getCraft()
    }

    fun getSpeed() = weightMax.value!! - weight.value!!
    fun getWeightMax() : Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_MODIFIER_WEIGHT_MAX, PRIVATE_MODE)
        val prefValue = sharedPref.getInt(PREF_MODIFIER_WEIGHT_MAX, 0)
        return 40 + _character.value!!.vigor + prefValue
    }
    fun getWeight():Float{
        val equipment = Services.getEquipment(context)
        return (equipment.leftHand.weight + equipment.rightHand.weight + equipment.catalyst.weight + equipment.shield.weight
        + equipment.hat.weight + equipment.chest.weight + equipment.gloves.weight + equipment.greaves.weight)
    }
    fun getLifeMax():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_MODIFIER_LIFE_MAX, PRIVATE_MODE)
        val prefValue = sharedPref.getInt(PREF_MODIFIER_LIFE_MAX, 0)
        return 200 + 20*character.value!!.vitality + prefValue
    }
    fun getManaMax() : Int {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_MODIFIER_MANA_MAX, PRIVATE_MODE)
        val prefValue = sharedPref.getInt(PREF_MODIFIER_MANA_MAX, 0)
        return 40 + 5*character.value!!.memory + prefValue
    }
    fun getConstMax():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_MODIFIER_CONST_MAX, PRIVATE_MODE)
        val prefValue = sharedPref.getInt(PREF_MODIFIER_CONST_MAX, 0)
        return 60 + 20*character.value!!.endurance + prefValue
    }
    fun getDiplo() =35 + _character.value!!.intelligence + _character.value!!.memory
    fun getPsy() = 35 + _character.value!!.faith + _character.value!!.dexterity
    fun getKnow() =30 + _character.value!!.intelligence + _character.value!!.memory
    fun getPush() =35 + _character.value!!.vigor + _character.value!!.strength
    fun getSneak() =35 + _character.value!!.dexterity + _character.value!!.endurance
    fun getCraft() =35 + _character.value!!.intelligence + _character.value!!.dexterity

    fun editCharacter(){
        Services.editCharacter(context, character.value!!)
        _character.value = Services.getCharacter(context)
        _lifeMax.value = getLifeMax()
        _manaMax.value = getManaMax()
        _constMax.value = getConstMax()
        _weightMax.value = getWeightMax()
        _weight.value = getWeight()
        _speed.value = getSpeed()
        _diplo.value = getDiplo()
        _psy.value = getPsy()
        _know.value = getKnow()
        _push.value = getPush()
        _sneak.value = getSneak()
        _craft.value = getCraft()
    }
}