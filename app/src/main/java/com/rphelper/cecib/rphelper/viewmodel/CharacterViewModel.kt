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
import com.rphelper.cecib.rphelper.utils.CalcUtils
import com.rphelper.cecib.rphelper.utils.FileUtils

class CharacterViewModel(val context: Context) : ViewModel(){

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Services.getCharacter(context)
    }

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value = CalcUtils.getWeight(context, Services.getEquipment(context))
    }

    val _weightMax = MutableLiveData<Int>()
    val weightMax : LiveData<Int> get() = _weightMax
    init {
        _weightMax.value = getWeightMax()
    }

    val _weightBonus = MutableLiveData<Int>()
    val weightBonus : LiveData<Int> get() = _weightBonus
    init {
        _weightBonus.value = getWeightBonus()
    }

    val _lifeMax = MutableLiveData<Int>()
    val lifeMax : LiveData<Int> get() = _lifeMax
    init {
        _lifeMax.value = getLifeMax()
    }

    val _lifeBonus = MutableLiveData<Int>()
    val lifeBonus : LiveData<Int> get() = _lifeBonus
    init {
        _lifeBonus.value = getLifeBonus()
    }

    val _manaMax = MutableLiveData<Int>()
    val manaMax : LiveData<Int> get() = _manaMax
    init {
        _manaMax.value = getManaMax()
    }

    val _manaBonus = MutableLiveData<Int>()
    val manaBonus : LiveData<Int> get() = _manaBonus
    init {
        _manaBonus.value = getManaBonus()
    }

    val _constMax = MutableLiveData<Int>()
    val constMax : LiveData<Int> get() = _constMax
    init {
        _constMax.value = getConstMax()
    }

    val _constBonus = MutableLiveData<Int>()
    val constBonus : LiveData<Int> get() = _constBonus
    init {
        _constBonus.value = getConstBonus()
    }

    val _speed = MutableLiveData<Float>()
    val speed : LiveData<Float> get() = _speed
    init {
        _speed.value = CalcUtils.getSpeed(context, character.value!!, Services.getEquipment(context))
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

    fun getLifeBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_LIFE_MAX_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_LIFE_MAX_TEMP, 0)
    }
    fun getConstBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_CONST_MAX_TEMP, 0)
    }
    fun getManaBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_MANA_MAX_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_MANA_MAX_TEMP, 0)
    }
    fun getWeightBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEIGHT_MAX_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_WEIGHT_MAX_TEMP, 0)
    }
    fun getDiplo() =35 + _character.value!!.intelligence + _character.value!!.memory
    fun getPsy() = 35 + _character.value!!.faith + _character.value!!.dexterity
    fun getKnow() =30 + _character.value!!.intelligence + _character.value!!.memory
    fun getPush() =35 + _character.value!!.vigor + _character.value!!.strength
    fun getSneak() =35 + _character.value!!.dexterity + _character.value!!.endurance
    fun getCraft() =35 + _character.value!!.intelligence + _character.value!!.dexterity

    fun getLifeMax():Int{
        val max = CalcUtils.getLifeMax(context, character.value!!)
        if(character.value!!.life.value>max){ character.value!!.life.value = max.toFloat(); Services.editCharacter(context, character.value!!)}
        if(character.value!!.life.value<0){ character.value!!.life.value = 0F; Services.editCharacter(context, character.value!!)}
        return max
    }

    fun getConstMax():Int{
        val max = CalcUtils.getConstMax(context, character.value!!)
        if(character.value!!.const.value>max){ character.value!!.const.value = max.toFloat(); Services.editCharacter(context, character.value!!)}
        if(character.value!!.const.value<0) {character.value!!.const.value = 0F; Services.editCharacter(context, character.value!!)}
        return max
    }
    fun getManaMax():Int{
        val max = CalcUtils.getManaMax(context, character.value!!)
        if(character.value!!.mana.value>max) {character.value!!.mana.value = max.toFloat(); Services.editCharacter(context, character.value!!)}
        if(character.value!!.mana.value<0){ character.value!!.mana.value = 0F; Services.editCharacter(context, character.value!!)}
        return max
    }

    fun getWeightMax():Int{
        val max = CalcUtils.getWeightMax(context, character.value!!)
        if(weight.value!!>max) _weight.value = max.toFloat()
        return max
    }

    fun editCharacter(){
        Services.editCharacter(context, character.value!!)
        updateCharacter()
    }

    fun updateCharacter(){
        _character.value = Services.getCharacter(context)
        _lifeMax.value = getLifeMax()
        _manaMax.value = getManaMax()
        _constMax.value = getConstMax()
        _weight.value = CalcUtils.getWeight(context, Services.getEquipment(context))
        _weightMax.value = getWeightMax()
        _speed.value = CalcUtils.getSpeed(context, character.value!!, Services.getEquipment(context))
        _weightBonus.value = getWeightBonus()
        _lifeBonus.value = getLifeBonus()
        _manaBonus.value = getManaBonus()
        _constBonus.value = getConstBonus()
        _diplo.value = getDiplo()
        _psy.value = getPsy()
        _know.value = getKnow()
        _push.value = getPush()
        _sneak.value = getSneak()
        _craft.value = getCraft()
    }
}