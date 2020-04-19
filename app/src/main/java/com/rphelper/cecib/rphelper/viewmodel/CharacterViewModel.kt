package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.*
import android.content.Context
import com.google.gson.Gson
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

    val _speed = MutableLiveData<Float>()
    val speed : LiveData<Float> get() = _speed
    init {
        _speed.value = getSpeed()
    }

    val _weightMax = MutableLiveData<Int>()
    val weightMax : LiveData<Int> get() = _weightMax
    init {
        _weightMax.value = getWeightMax()
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

    fun getSpeed() =_character.value!!.weight!!.maxValue - _character.value!!.weight!!.value
    fun getWeightMax()= 40 + _character.value!!.vigor
    fun getDiplo() =35 + _character.value!!.intelligence + _character.value!!.memory
    fun getPsy() = 35 + _character.value!!.faith + _character.value!!.dexterity
    fun getKnow() =30 + _character.value!!.intelligence + _character.value!!.memory
    fun getPush() =35 + _character.value!!.vigor + _character.value!!.strength
    fun getSneak() =35 + _character.value!!.dexterity + _character.value!!.endurance
    fun getCraft() =35 + _character.value!!.intelligence + _character.value!!.dexterity

    fun editCharacter(){
        Services.editCharacter(context, character.value!!)
        _character.value = Services.getCharacter(context)
        _speed.value = getSpeed()
        _weightMax.value = getWeightMax()
        _diplo.value = getDiplo()
        _psy.value = getPsy()
        _know.value = getKnow()
        _push.value = getPush()
        _sneak.value = getSneak()
        _craft.value = getCraft()
    }
}