package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.dto.Armor
import com.rphelper.cecib.rphelper.dto.Shield
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.utils.FileUtils
import org.json.JSONObject

class EquipmentViewModel (val context: Context) : ViewModel(){

    val _leftHand = MutableLiveData<Weapon>()
    val leftHand : LiveData<Weapon> get() = _leftHand
    init {
        val leftHandString = JSONObject(getEquipmentString()).get("leftHand").toString()
        if(!leftHandString.equals(context.getString(R.string.empty_json))) _leftHand.value = Gson().fromJson<Weapon>(leftHandString, Weapon::class.java)
    }

    val _rightHand = MutableLiveData<Weapon>()
    val rightHand : LiveData<Weapon> get() = _rightHand
    init {
        val rightHandString = JSONObject(getEquipmentString()).get("rightHand").toString()
        if(!rightHandString.equals(context.getString(R.string.empty_json))) _rightHand.value = Gson().fromJson<Weapon>(rightHandString, Weapon::class.java)
    }

    val _shield = MutableLiveData<Shield>()
    val shield : LiveData<Shield> get() = _shield
    init {
        val shieldString = JSONObject(getEquipmentString()).get("shield").toString()
        if(!shieldString.equals(context.getString(R.string.empty_json))) _shield.value = Gson().fromJson<Shield>(shieldString, Shield::class.java)
    }

    val _hat = MutableLiveData<Armor>()
    val hat : LiveData<Armor> get() = _hat
    init {
        val hatString = JSONObject(getEquipmentString()).get("hat").toString()
        if(!hatString.equals(context.getString(R.string.empty_json))) _hat.value = Gson().fromJson<Armor>(hatString, Armor::class.java)
    }

    val _chest = MutableLiveData<Armor>()
    val chest : LiveData<Armor> get() = _chest
    init {
        val chestString = JSONObject(getEquipmentString()).get("chest").toString()
        if(!chestString.equals(context.getString(R.string.empty_json))) _chest.value = Gson().fromJson<Armor>(chestString, Armor::class.java)
    }

    val _gloves = MutableLiveData<Armor>()
    val gloves : LiveData<Armor> get() = _gloves
    init {
        val glovesString = JSONObject(getEquipmentString()).get("gloves").toString()
        if(!glovesString.equals(context.getString(R.string.empty_json))) _gloves.value = Gson().fromJson<Armor>(glovesString, Armor::class.java)
    }

    val _greaves = MutableLiveData<Armor>()
    val greaves : LiveData<Armor> get() = _greaves
    init {
        val greavesString = JSONObject(getEquipmentString()).get("greaves").toString()
        if(!greavesString.equals(context.getString(R.string.empty_json))) _greaves.value = Gson().fromJson<Armor>(greavesString, Armor::class.java)
    }

    fun getEquipmentString():String{
        return FileUtils.readJsonAsset(context,"equipment.json")
    }
}