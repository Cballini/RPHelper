package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.google.gson.Gson
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.utils.FileUtils

class CharacterViewModel(val context: Context) : ViewModel(){

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Gson().fromJson<Character>(getCharacterString(), Character::class.java)
    }

    val _speed = MutableLiveData<Float>()
    val speed : LiveData<Float> get() = _speed
    init {
        _speed.value = _character.value!!.weight!!.maxValue - _character.value!!.weight!!.value
    }

    val _diplo = MutableLiveData<Int>()
    val diplo : LiveData<Int> get() = _diplo
    init {
        _diplo.value = 35 + _character.value!!.intelligence + _character.value!!.memory
    }

    val _psy = MutableLiveData<Int>()
    val psy : LiveData<Int> get() = _psy
    init {
        _psy.value = 35 + _character.value!!.faith + _character.value!!.dexterity
    }

    val _know = MutableLiveData<Int>()
    val know : LiveData<Int> get() = _know
    init {
        _know.value = 30 + _character.value!!.intelligence + _character.value!!.memory
    }

    val _push = MutableLiveData<Int>()
    val push : LiveData<Int> get() = _push
    init {
        _push.value = 35 + _character.value!!.vigor + _character.value!!.strength
    }

    val _sneak = MutableLiveData<Int>()
    val sneak : LiveData<Int> get() = _sneak
    init {
        _sneak.value = 35 + _character.value!!.dexterity + _character.value!!.endurance
    }

    val _craft = MutableLiveData<Int>()
    val craft : LiveData<Int> get() = _craft
    init {
        _craft.value = 35 + _character.value!!.intelligence + _character.value!!.dexterity
    }

    fun getCharacterString():String{
        return FileUtils.readJsonAsset(context,"character.json")
    }
}