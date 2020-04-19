package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Armor
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Shield
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.utils.FileUtils
import org.json.JSONObject

class EquipmentViewModel (val context: Context) : ViewModel(){

    val _leftHand = MutableLiveData<Weapon>()
    val leftHand : LiveData<Weapon> get() = _leftHand
    init {
        _leftHand.value = Services.getWeapon(context, "leftHand")
    }

    val _rightHand = MutableLiveData<Weapon>()
    val rightHand : LiveData<Weapon> get() = _rightHand
    init {
        _rightHand.value = Services.getWeapon(context, "rightHand")
    }

    val _catalyst = MutableLiveData<Weapon>()
    val catalyst : LiveData<Weapon> get() = _catalyst
    init {
        _catalyst.value = Services.getWeapon(context, "catalyst")
    }

    val _shield = MutableLiveData<Shield>()
    val shield : LiveData<Shield> get() = _shield
    init {
         _shield.value = Services.getShield(context)
    }

    val _hat = MutableLiveData<Armor>()
    val hat : LiveData<Armor> get() = _hat
    init {
         _hat.value = Services.getArmor(context, "hat")
    }

    val _chest = MutableLiveData<Armor>()
    val chest : LiveData<Armor> get() = _chest
    init {
        _chest.value = Services.getArmor(context, "chest")
    }

    val _gloves = MutableLiveData<Armor>()
    val gloves : LiveData<Armor> get() = _gloves
    init {
        _gloves.value = Services.getArmor(context, "gloves")
    }

    val _greaves = MutableLiveData<Armor>()
    val greaves : LiveData<Armor> get() = _greaves
    init {
        _greaves.value = Services.getArmor(context, "greaves")
    }

    val _damages = MutableLiveData<Int>()
    val damages : LiveData<Int> get() = _damages
    init {
        _damages.value = 0 //Todo calcul
    }

    val _defense = MutableLiveData<Int>()
    val defense : LiveData<Int> get() = _defense
    init {
        _defense.value = 0 //Todo calcul
    }

    val _res = MutableLiveData<String>()
    val res : LiveData<String> get() = _res
    init {
        _res.value = "" //Todo concat list res
    }
    val _immun = MutableLiveData<String>()
    val immun : LiveData<String> get() = _immun
    init {
        _immun.value = "" //Todo concat list immun
    }
    val _weak = MutableLiveData<String>()
    val weak : LiveData<String> get() = _weak
    init {
        _weak.value = "" //Todo concat list weaknesses
    }

    fun editEquipment(){
        val equipment = Equipment(leftHand.value!!, rightHand.value!!, shield.value!!, hat.value!!, chest.value!!, gloves.value!!, greaves.value!!)
        Services.editEquipment(context, equipment)
        _leftHand.value = Services.getWeapon(context, "leftHand")
        _rightHand.value = Services.getWeapon(context, "rightHand")
        _catalyst.value = Services.getWeapon(context, "catalyst")
        _shield.value = Services.getShield(context)
        _hat.value = Services.getArmor(context, "hat")
        _chest.value = Services.getArmor(context, "chest")
        _gloves.value = Services.getArmor(context, "gloves")
        _greaves.value = Services.getArmor(context, "greaves")
    }
}