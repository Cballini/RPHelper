package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.PieceEquipment
import com.rphelper.cecib.rphelper.enums.Status
import com.rphelper.cecib.rphelper.utils.CalcUtils

class EquipmentViewModel (val context: Context, character: Character, equipment: Equipment, inventory: Inventory) : ViewModel(){

    var character = character
    var equipment = equipment
    var inventory = inventory

    val _damages = MutableLiveData<Int>()
    val damages : LiveData<Int> get() = _damages
    init {
        _damages.value = getDamages()
    }

    val _damagesBonus = MutableLiveData<Int>()
    val damagesBonus : LiveData<Int> get() = _damagesBonus
    init {
        _damagesBonus.value = getDamagesBonus()
    }

    val _defense = MutableLiveData<Int>()
    val defense : LiveData<Int> get() = _defense
    init {
        _defense.value = getDef()
    }

    val _defenseBonus = MutableLiveData<Int>()
    val defenseBonus : LiveData<Int> get() = _defenseBonus
    init {
        _defenseBonus.value = getDefBonus()
    }

    val _res = MutableLiveData<String>()
    val res : LiveData<String> get() = _res
    init {
        _res.value = getRes()
    }
    val _immun = MutableLiveData<String>()
    val immun : LiveData<String> get() = _immun
    init {
        _immun.value = getImmun()
    }
    val _weak = MutableLiveData<String>()
    val weak : LiveData<String> get() = _weak
    init {
        _weak.value = getWeak()
    }

    val _resBonus = MutableLiveData<String>()
    val resBonus : LiveData<String> get() = _resBonus
    init {
        _resBonus.value = getResBonus()
    }
    val _immunBonus = MutableLiveData<String>()
    val immunBonus : LiveData<String> get() = _immunBonus
    init {
        _immunBonus.value = getImmunBonus()
    }
    val _weakBonus = MutableLiveData<String>()
    val weakBonus : LiveData<String> get() = _weakBonus
    init {
        _weakBonus.value = getWeakBonus()
    }

    fun getDamages():Int = CalcUtils.getDamages(context, character)


    fun getDamagesBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DAMAGES_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_DAMAGES_TEMP, 0)
    }

    fun getDef() = CalcUtils.getDef(context, character, equipment)

    fun getDefBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DEFENSE_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_DEFENSE_TEMP, 0)
    }

    fun getResBonus():String{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_RES_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getString(Preferences.PREF_MODIFIER_RES_TEMP, "")!!
    }
    fun getWeakBonus():String{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getString(Preferences.PREF_MODIFIER_WEAK_TEMP, "")!!
    }
    fun getImmunBonus():String{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getString(Preferences.PREF_MODIFIER_IMMUN_TEMP, "")!!
    }

    fun getRes():String{
        var res = "/"
        if (null != equipment.hat.res && null != equipment.chest.res && null != equipment.gloves.res && null != equipment.greaves.res) {
            res=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_RES, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_RES,"")
            pref += " " + context.getSharedPreferences(Preferences.PREF_MODIFIER_RES_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_RES_TEMP,"")

            if (checkRes(equipment, Elem.FIRE, pref!!)){
                res += Elem.FIRE.toString()
            }
            if (checkRes(equipment, Elem.MAGIC, pref!!)){
                res += Elem.MAGIC.toString()
            }
            if (checkRes(equipment, Elem.DARKNESS, pref!!)){
                res += Elem.DARKNESS.toString()
            }
            if (checkRes(equipment, Elem.LIGHTNING, pref!!)){
                res += Elem.LIGHTNING.toString()
            }
            if (checkRes(equipment, Elem.ALL, pref!!)){
                res = Elem.FIRE.toString() + Elem.MAGIC.toString() + Elem.LIGHTNING.toString() + Elem.DARKNESS.toString()
            }
        }
        return res
    }

    fun checkRes(equipment: Equipment, elem:Elem, pref:String) : Boolean{
        var hasRes = false
        if(pref.contains(elem.name)) {
            hasRes = true
        }
        else{
            if (equipment.hat.res.contains(elem) || equipment.hat.res.contains(Elem.ALL)) {
                if (equipment.chest.res.contains(elem) || equipment.chest.res.contains(Elem.ALL)) {
                    if (equipment.gloves.res.contains(elem) || equipment.gloves.res.contains(Elem.ALL)) {
                        if (equipment.greaves.res.contains(elem) || equipment.greaves.res.contains(Elem.ALL)) {
                            hasRes = true
                        }
                    }
                }
            }
        }
        return hasRes
    }

    fun getWeak():String {
        var weak = "/"
        if (null != equipment.hat.weak && null != equipment.chest.weak && null != equipment.gloves.weak && null != equipment.greaves.weak) {
            weak = ""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK, "")
            pref += " " + context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK_TEMP, "")
            if (checkWeak(equipment, Elem.FIRE, pref!!)) {
                weak += Elem.FIRE.toString()
            }
            if (checkWeak(equipment, Elem.MAGIC, pref!!)) {
                weak += Elem.MAGIC.toString()
            }
            if (checkWeak(equipment, Elem.DARKNESS, pref!!)) {
                weak += Elem.DARKNESS.toString()
            }
            if (checkWeak(equipment, Elem.LIGHTNING, pref!!)) {
                weak += Elem.LIGHTNING.toString()
            }
            if (checkWeak(equipment, Elem.ALL, pref!!)) {
                weak = Elem.FIRE.toString() + Elem.MAGIC.toString() + Elem.LIGHTNING.toString() + Elem.DARKNESS.toString()
            }
        }
        return weak
    }

    fun checkWeak(equipment: Equipment, elem:Elem, pref:String) : Boolean{
        var hasWeak = false
        if(pref.contains(elem.name)) {
            hasWeak = true
        }
        else{
            if (equipment.hat.weak.contains(elem) || equipment.hat.weak.contains(Elem.ALL)) {
                if (equipment.chest.weak.contains(elem) || equipment.chest.weak.contains(Elem.ALL)) {
                    if (equipment.gloves.weak.contains(elem) || equipment.gloves.weak.contains(Elem.ALL)) {
                        if (equipment.greaves.weak.contains(elem) || equipment.greaves.weak.contains(Elem.ALL)) {
                            hasWeak = true
                        }
                    }
                }
            }
        }
        return hasWeak
    }

    fun getImmun():String{
        var immun = "/"
        if (null != equipment.hat.immun && null != equipment.chest.immun && null != equipment.gloves.immun && null != equipment.greaves.immun) {
            immun=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_IMMUN,"")
            pref += " " + context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_IMMUN_TEMP,"")
            if ((equipment.hat.immun.contains(Status.BLEED) && equipment.chest.immun.contains(Status.BLEED)
                    && equipment.gloves.immun.contains(Status.BLEED) && equipment.greaves.immun.contains(Status.BLEED)) || pref!!.contains(Status.BLEED.name)) immun += Status.BLEED.toString()
            if ((equipment.hat.immun.contains(Status.FROST) && equipment.chest.immun.contains(Status.FROST)
                    && equipment.gloves.immun.contains(Status.FROST) && equipment.greaves.immun.contains(Status.FROST)) || pref!!.contains(Status.FROST.name)){
                if (immun.isNotEmpty())immun += "\n"
                immun +=Status.FROST.toString()
            }
            if ((equipment.hat.immun.contains(Status.POISON) && equipment.chest.immun.contains(Status.POISON)
                    && equipment.gloves.immun.contains(Status.POISON) && equipment.greaves.immun.contains(Status.POISON)) || pref!!.contains(Status.POISON.name)){
                if (immun.isNotEmpty())immun += "\n"
                immun += Status.POISON.toString()
            }
        }
        return immun
    }

    fun getTotalDamage(weapon: Weapon):Int = CalcUtils.getTotalDamage(weapon, context, character)

    fun editEquipment() = Services.editEquipment(equipment)

    fun weaponToInventory(type :String, weapon: Weapon){
        weapon.equip = false
        inventory.weapons.add(weapon)
        Services.editInventory(inventory)
        when(type){
            context.getString(R.string.left_hand)-> equipment.leftHand.reinit()
            context.getString(R.string.right_hand)-> equipment.rightHand.reinit()
            context.getString(R.string.catalyst)-> equipment.catalyst.reinit()
        }
        editEquipment()
    }

    fun shieldToInventory(shield: Shield){
        shield.equip = false
        inventory.shields.add(shield)
        Services.editInventory(inventory)
        shield.reinit()
        editEquipment()
    }

    fun armorToInventory(type :PieceEquipment, armor: Armor){
        armor.equip = false
        armor.type = type
        inventory.armors.add(armor)
        Services.editInventory(inventory)
        when(type){
            PieceEquipment.HAT-> equipment.hat.reinit()
            PieceEquipment.CHEST-> equipment.chest.reinit()
            PieceEquipment.GLOVES-> equipment.gloves.reinit()
            PieceEquipment.GREAVES-> equipment.greaves.reinit()
        }
        editEquipment()
    }

    fun updateEquipmentBonus(){
        _damagesBonus.value = getDamagesBonus()
        _damages.value = getDamages()
        _defenseBonus.value = getDefBonus()
        _defense.value = getDef()
        _resBonus.value = getResBonus()
        _res.value = getRes()
        _weakBonus.value = getWeakBonus()
        _weak.value = getWeak()
        _immunBonus.value = getImmunBonus()
        _immun.value = getImmun()
    }

}