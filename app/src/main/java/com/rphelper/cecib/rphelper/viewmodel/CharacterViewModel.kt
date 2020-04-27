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

    val _weightMax = MutableLiveData<Int>()
    val weightMax : LiveData<Int> get() = _weightMax
    init {
        _weightMax.value = CalcUtils.getWeightMax(context, character.value!!)
    }

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value = CalcUtils.getWeight(context, Services.getEquipment(context))
    }

    val _lifeMax = MutableLiveData<Int>()
    val lifeMax : LiveData<Int> get() = _lifeMax
    init {
        _lifeMax.value = CalcUtils.getLifeMax(context, character.value!!)
    }

    val _manaMax = MutableLiveData<Int>()
    val manaMax : LiveData<Int> get() = _manaMax
    init {
        _manaMax.value = CalcUtils.getManaMax(context, character.value!!)
    }

    val _constMax = MutableLiveData<Int>()
    val constMax : LiveData<Int> get() = _constMax
    init {
        _constMax.value = CalcUtils.getConstMax(context, character.value!!)
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
    fun getDiplo() =35 + _character.value!!.intelligence + _character.value!!.memory
    fun getPsy() = 35 + _character.value!!.faith + _character.value!!.dexterity
    fun getKnow() =30 + _character.value!!.intelligence + _character.value!!.memory
    fun getPush() =35 + _character.value!!.vigor + _character.value!!.strength
    fun getSneak() =35 + _character.value!!.dexterity + _character.value!!.endurance
    fun getCraft() =35 + _character.value!!.intelligence + _character.value!!.dexterity

    fun editCharacter(){
        Services.editCharacter(context, character.value!!)
        _character.value = Services.getCharacter(context)
        _lifeMax.value = CalcUtils.getLifeMax(context, character.value!!)
        _manaMax.value = CalcUtils.getManaMax(context, character.value!!)
        _constMax.value = CalcUtils.getConstMax(context, character.value!!)
        _weightMax.value = CalcUtils.getWeightMax(context, character.value!!)
        _weight.value = CalcUtils.getWeight(context, Services.getEquipment(context))
        _speed.value = CalcUtils.getSpeed(context, character.value!!, Services.getEquipment(context))
        _diplo.value = getDiplo()
        _psy.value = getPsy()
        _know.value = getKnow()
        _push.value = getPush()
        _sneak.value = getSneak()
        _craft.value = getCraft()
    }
}