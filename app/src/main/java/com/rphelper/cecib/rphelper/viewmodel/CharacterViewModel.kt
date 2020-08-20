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
import com.rphelper.cecib.rphelper.utils.CalcUtils


class CharacterViewModel(val context: Context) : ViewModel(){

    var firebaseQuery = Services.getUserDatabase()
    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?>? {
        return firebaseQuery
    }

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Character()
    }

    val _equipment = MutableLiveData<Equipment>()
    val equipment : LiveData<Equipment> get() = _equipment
    init {
        _equipment.value = Equipment()
    }

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value = 0F
    }

    val _weightMax = MutableLiveData<Int>()
    val weightMax : LiveData<Int> get() = _weightMax
    init {
        _weightMax.value = 0
    }

    val _weightBonus = MutableLiveData<Int>()
    val weightBonus : LiveData<Int> get() = _weightBonus
    init {
        _weightBonus.value = getWeightBonus()
    }

    val _lifeMax = MutableLiveData<Int>()
    val lifeMax : LiveData<Int> get() = _lifeMax
    init {
        _lifeMax.value = 0
    }

    val _lifeBonus = MutableLiveData<Int>()
    val lifeBonus : LiveData<Int> get() = _lifeBonus
    init {
        _lifeBonus.value = getLifeBonus()
    }

    val _manaMax = MutableLiveData<Int>()
    val manaMax : LiveData<Int> get() = _manaMax
    init {
        _manaMax.value = 0
    }

    val _manaBonus = MutableLiveData<Int>()
    val manaBonus : LiveData<Int> get() = _manaBonus
    init {
        _manaBonus.value = getManaBonus()
    }

    val _constMax = MutableLiveData<Int>()
    val constMax : LiveData<Int> get() = _constMax
    init {
        _constMax.value = 0
    }

    val _constBonus = MutableLiveData<Int>()
    val constBonus : LiveData<Int> get() = _constBonus
    init {
        _constBonus.value = getConstBonus()
    }

    val _speed = MutableLiveData<Float>()
    val speed : LiveData<Float> get() = _speed
    init {
        _speed.value = 0F
    }

    val _diplo = MutableLiveData<Int>()
    val diplo : LiveData<Int> get() = _diplo
    init {
        _diplo.value = 0
    }

    val _psy = MutableLiveData<Int>()
    val psy : LiveData<Int> get() = _psy
    init {
        _psy.value = 0
    }

    val _know = MutableLiveData<Int>()
    val know : LiveData<Int> get() = _know
    init {
        _know.value = 0
    }

    val _push = MutableLiveData<Int>()
    val push : LiveData<Int> get() = _push
    init {
        _push.value = 0
    }

    val _sneak = MutableLiveData<Int>()
    val sneak : LiveData<Int> get() = _sneak
    init {
        _sneak.value = 0
    }

    val _craft = MutableLiveData<Int>()
    val craft : LiveData<Int> get() = _craft
    init {
        _craft.value = 0
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

    fun getSpeed() = CalcUtils.getSpeed(context, character.value!!, equipment.value!!)

    fun getDiplo() =35 + character.value!!.intelligence + _character.value!!.memory
    fun getPsy() = 35 + _character.value!!.faith + _character.value!!.dexterity
    fun getKnow() =30 + _character.value!!.intelligence + _character.value!!.memory
    fun getPush() =35 + _character.value!!.vigor + _character.value!!.strength
    fun getSneak() =35 + _character.value!!.dexterity + _character.value!!.endurance
    fun getCraft() =35 + _character.value!!.intelligence + _character.value!!.dexterity

    fun getLifeMax():Int{
        val max = CalcUtils.getLifeMax(context, character.value!!)
        if(character.value!!.life.value>max){ character.value!!.life.value = max.toFloat(); Services.editCharacter(character.value!!)}
        if(character.value!!.life.value<0){ character.value!!.life.value = 0F; Services.editCharacter(character.value!!)}
        return max
    }

    fun getConstMax():Int{
        val max = CalcUtils.getConstMax(context, character.value!!)
        if(character.value!!.const.value>max){ character.value!!.const.value = max.toFloat(); Services.editCharacter(character.value!!)}
        if(character.value!!.const.value<0) {character.value!!.const.value = 0F; Services.editCharacter(character.value!!)}
        return max
    }
    fun getManaMax():Int{
        val max = CalcUtils.getManaMax(context, character.value!!)
        if(character.value!!.mana.value>max) {character.value!!.mana.value = max.toFloat(); Services.editCharacter(character.value!!)}
        if(character.value!!.mana.value<0){ character.value!!.mana.value = 0F; Services.editCharacter(character.value!!)}
        return max
    }

    fun getWeight() = CalcUtils.getWeight(equipment.value!!)

    fun getWeightMax():Int{
        val max = CalcUtils.getWeightMax(context, character.value!!)
        if(weight.value!!>max) _weight.value = max.toFloat()
        return max
    }

    fun editCharacter(){
        Services.editCharacter(character.value!!)
    }

    fun updateCharacterBonus(){
        _weightBonus.value = getWeightBonus()
        _weightMax.value = getWeightMax()
        _lifeBonus.value = getLifeBonus()
        _lifeMax.value = getLifeMax()
        _manaBonus.value = getManaBonus()
        _manaMax.value = getManaMax()
        _constBonus.value = getConstBonus()
        _constMax.value = getConstMax()
    }
}