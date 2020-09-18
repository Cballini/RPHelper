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
            if ((equipment.hat.res.contains(Elem.FIRE) && equipment.chest.res.contains(Elem.FIRE)
                    && equipment.gloves.res.contains(Elem.FIRE) && equipment.greaves.res.contains(Elem.FIRE)) || pref!!.contains(Elem.FIRE.name)) res += Elem.FIRE.toString()
            if ((equipment.hat.res.contains(Elem.MAGIC) && equipment.chest.res.contains(Elem.MAGIC)
                    && equipment.gloves.res.contains(Elem.MAGIC) && equipment.greaves.res.contains(Elem.MAGIC)) || pref!!.contains(Elem.MAGIC.name)) {
                if(res.isNotEmpty())res += "\n"
                res +=  Elem.MAGIC.toString()
            }
            if ((equipment.hat.res.contains(Elem.LIGHTNING) && equipment.chest.res.contains(Elem.LIGHTNING)
                    && equipment.gloves.res.contains(Elem.LIGHTNING) && equipment.greaves.res.contains(Elem.LIGHTNING)) || pref!!.contains(Elem.LIGHTNING.name)) {
                if(res.isNotEmpty())res += "\n"
                res += Elem.LIGHTNING.toString()
            }
            if ((equipment.hat.res.contains(Elem.DARKNESS) && equipment.chest.res.contains(Elem.DARKNESS)
                    && equipment.gloves.res.contains(Elem.DARKNESS) && equipment.greaves.res.contains(Elem.DARKNESS)) || pref!!.contains(Elem.DARKNESS.name)){
                if(res.isNotEmpty())res += "\n"
                res += Elem.DARKNESS.toString()
            }
            if (equipment.hat.res.contains(Elem.ALL) && equipment.chest.res.contains(Elem.ALL)
                    && equipment.gloves.res.contains(Elem.ALL) && equipment.greaves.res.contains(Elem.ALL)){
                res = Elem.ALL.toString()
            }
        }
        return res
    }
    fun getWeak():String {
        var weak = "/"
        if (null != equipment.hat.weak && null != equipment.chest.weak && null != equipment.gloves.weak && null != equipment.greaves.weak){
            weak=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK,"")
            pref +=  " "  + context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK_TEMP,"")
            if ((equipment.hat.weak.contains(Elem.FIRE) || equipment.chest.weak.contains(Elem.FIRE)
                    || equipment.gloves.weak.contains(Elem.FIRE) || equipment.greaves.weak.contains(Elem.FIRE)) || pref!!.contains(Elem.FIRE.name)) weak += Elem.FIRE.toString()
        if ((equipment.hat.weak.contains(Elem.MAGIC) || equipment.chest.weak.contains(Elem.MAGIC)
                || equipment.gloves.weak.contains(Elem.MAGIC) || equipment.greaves.weak.contains(Elem.MAGIC)) || pref!!.contains(Elem.MAGIC.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.MAGIC.toString()
        }
        if ((equipment.hat.weak.contains(Elem.LIGHTNING) || equipment.chest.weak.contains(Elem.LIGHTNING)
                || equipment.gloves.weak.contains(Elem.LIGHTNING) || equipment.greaves.weak.contains(Elem.LIGHTNING)) || pref!!.contains(Elem.LIGHTNING.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak +=Elem.LIGHTNING.toString()
        }
        if ((equipment.hat.weak.contains(Elem.DARKNESS) || equipment.chest.weak.contains(Elem.DARKNESS)
                || equipment.gloves.weak.contains(Elem.DARKNESS) || equipment.greaves.weak.contains(Elem.DARKNESS)) || pref!!.contains(Elem.DARKNESS.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.DARKNESS.toString()
        }
            if (equipment.hat.weak.contains(Elem.ALL) || equipment.chest.weak.contains(Elem.ALL)
                    || equipment.gloves.weak.contains(Elem.ALL) || equipment.greaves.weak.contains(Elem.ALL)) weak += Elem.FIRE.toString()
    }
        return weak
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

    fun armorToInventory(type :String, armor: Armor){
        armor.equip = false
        inventory.armors.add(armor)
        Services.editInventory(inventory)
        when(type){
            context.getString(R.string.hat)-> equipment.hat.reinit()
            context.getString(R.string.chestplate)-> equipment.chest.reinit()
            context.getString(R.string.gloves)-> equipment.gloves.reinit()
            context.getString(R.string.greaves)-> equipment.greaves.reinit()
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