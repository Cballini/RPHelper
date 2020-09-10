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

class EquipmentViewModel (val context: Context) : ViewModel(){

    var firebaseQuery = Services.getUserDatabase()
    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?>? {
        return firebaseQuery
    }

    val _equipment = MutableLiveData<Equipment>()
    val equipment : LiveData<Equipment> get() = _equipment
    init {
        _equipment.value = Equipment()
    }

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Character()
    }

    val _inventory = MutableLiveData<Inventory>()
    val inventory : LiveData<Inventory> get() = _inventory
    init {
        _inventory.value = Inventory()
    }

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

    fun getDamages():Int = CalcUtils.getDamages(context, character.value!!)


    fun getDamagesBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DAMAGES_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_DAMAGES_TEMP, 0)
    }

    fun getDef() = CalcUtils.getDef(context, character.value!!, equipment.value!!)

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
        if (null != equipment.value!!.hat.res && null != equipment.value!!.chest.res && null != equipment.value!!.gloves.res && null != equipment.value!!.greaves.res) {
            res=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_RES, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_RES,"")
            pref += " " + context.getSharedPreferences(Preferences.PREF_MODIFIER_RES_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_RES_TEMP,"")
            if ((equipment.value!!.hat.res.contains(Elem.FIRE) && equipment.value!!.chest.res.contains(Elem.FIRE)
                    && equipment.value!!.gloves.res.contains(Elem.FIRE) && equipment.value!!.greaves.res.contains(Elem.FIRE)) || pref!!.contains(Elem.FIRE.name)) res += Elem.FIRE.toString()
            if ((equipment.value!!.hat.res.contains(Elem.MAGIC) && equipment.value!!.chest.res.contains(Elem.MAGIC)
                    && equipment.value!!.gloves.res.contains(Elem.MAGIC) && equipment.value!!.greaves.res.contains(Elem.MAGIC)) || pref!!.contains(Elem.MAGIC.name)) {
                if(res.isNotEmpty())res += "\n"
                res +=  Elem.MAGIC.toString()
            }
            if ((equipment.value!!.hat.res.contains(Elem.LIGHTNING) && equipment.value!!.chest.res.contains(Elem.LIGHTNING)
                    && equipment.value!!.gloves.res.contains(Elem.LIGHTNING) && equipment.value!!.greaves.res.contains(Elem.LIGHTNING)) || pref!!.contains(Elem.LIGHTNING.name)) {
                if(res.isNotEmpty())res += "\n"
                res += Elem.LIGHTNING.toString()
            }
            if ((equipment.value!!.hat.res.contains(Elem.DARKNESS) && equipment.value!!.chest.res.contains(Elem.DARKNESS)
                    && equipment.value!!.gloves.res.contains(Elem.DARKNESS) && equipment.value!!.greaves.res.contains(Elem.DARKNESS)) || pref!!.contains(Elem.DARKNESS.name)){
                if(res.isNotEmpty())res += "\n"
                res += Elem.DARKNESS.toString()
            }
            if (equipment.value!!.hat.res.contains(Elem.ALL) && equipment.value!!.chest.res.contains(Elem.ALL)
                    && equipment.value!!.gloves.res.contains(Elem.ALL) && equipment.value!!.greaves.res.contains(Elem.ALL)){
                res = Elem.ALL.toString()
            }
        }
        return res
    }
    fun getWeak():String {
        var weak = "/"
        if (null != equipment.value!!.hat.weak && null != equipment.value!!.chest.weak && null != equipment.value!!.gloves.weak && null != equipment.value!!.greaves.weak){
            weak=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK,"")
            pref +=  " "  + context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK_TEMP,"")
            if ((equipment.value!!.hat.weak.contains(Elem.FIRE) || equipment.value!!.chest.weak.contains(Elem.FIRE)
                    || equipment.value!!.gloves.weak.contains(Elem.FIRE) || equipment.value!!.greaves.weak.contains(Elem.FIRE)) || pref!!.contains(Elem.FIRE.name)) weak += Elem.FIRE.toString()
        if ((equipment.value!!.hat.weak.contains(Elem.MAGIC) || equipment.value!!.chest.weak.contains(Elem.MAGIC)
                || equipment.value!!.gloves.weak.contains(Elem.MAGIC) || equipment.value!!.greaves.weak.contains(Elem.MAGIC)) || pref!!.contains(Elem.MAGIC.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.MAGIC.toString()
        }
        if ((equipment.value!!.hat.weak.contains(Elem.LIGHTNING) || equipment.value!!.chest.weak.contains(Elem.LIGHTNING)
                || equipment.value!!.gloves.weak.contains(Elem.LIGHTNING) || equipment.value!!.greaves.weak.contains(Elem.LIGHTNING)) || pref!!.contains(Elem.LIGHTNING.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak +=Elem.LIGHTNING.toString()
        }
        if ((equipment.value!!.hat.weak.contains(Elem.DARKNESS) || equipment.value!!.chest.weak.contains(Elem.DARKNESS)
                || equipment.value!!.gloves.weak.contains(Elem.DARKNESS) || equipment.value!!.greaves.weak.contains(Elem.DARKNESS)) || pref!!.contains(Elem.DARKNESS.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.DARKNESS.toString()
        }
            if (equipment.value!!.hat.weak.contains(Elem.ALL) || equipment.value!!.chest.weak.contains(Elem.ALL)
                    || equipment.value!!.gloves.weak.contains(Elem.ALL) || equipment.value!!.greaves.weak.contains(Elem.ALL)) weak += Elem.FIRE.toString()
    }
        return weak
    }

    fun getImmun():String{
        var immun = "/"
        if (null != equipment.value!!.hat.immun && null != equipment.value!!.chest.immun && null != equipment.value!!.gloves.immun && null != equipment.value!!.greaves.immun) {
            immun=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_IMMUN,"")
            pref += " " + context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_IMMUN_TEMP,"")
            if ((equipment.value!!.hat.immun.contains(Status.BLEED) && equipment.value!!.chest.immun.contains(Status.BLEED)
                    && equipment.value!!.gloves.immun.contains(Status.BLEED) && equipment.value!!.greaves.immun.contains(Status.BLEED)) || pref!!.contains(Status.BLEED.name)) immun += Status.BLEED.toString()
            if ((equipment.value!!.hat.immun.contains(Status.FROST) && equipment.value!!.chest.immun.contains(Status.FROST)
                    && equipment.value!!.gloves.immun.contains(Status.FROST) && equipment.value!!.greaves.immun.contains(Status.FROST)) || pref!!.contains(Status.FROST.name)){
                if (immun.isNotEmpty())immun += "\n"
                immun +=Status.FROST.toString()
            }
            if ((equipment.value!!.hat.immun.contains(Status.POISON) && equipment.value!!.chest.immun.contains(Status.POISON)
                    && equipment.value!!.gloves.immun.contains(Status.POISON) && equipment.value!!.greaves.immun.contains(Status.POISON)) || pref!!.contains(Status.POISON.name)){
                if (immun.isNotEmpty())immun += "\n"
                immun += Status.POISON.toString()
            }
        }
        return immun
    }

    fun getTotalDamage(weapon: Weapon):Int = CalcUtils.getTotalDamage(weapon, context, character.value!!)

    fun editEquipment() = Services.editEquipment(equipment.value!!)

    fun weaponToInventory(type :String, weapon: Weapon){
        weapon.equip = false
        inventory.value!!.weapons.add(weapon)
        Services.editInventory(inventory.value!!)
        when(type){
            context.getString(R.string.left_hand)-> equipment.value!!.leftHand.reinit()
            context.getString(R.string.right_hand)-> equipment.value!!.rightHand.reinit()
            context.getString(R.string.catalyst)-> equipment.value!!.catalyst.reinit()
        }
        editEquipment()
    }

    fun shieldToInventory(shield: Shield){
        shield.equip = false
        inventory.value!!.shields.add(shield)
        Services.editInventory(inventory.value!!)
        shield.reinit()
        editEquipment()
    }

    fun armorToInventory(type :String, armor: Armor){
        armor.equip = false
        inventory.value!!.armors.add(armor)
        Services.editInventory(inventory.value!!)
        when(type){
            context.getString(R.string.hat)-> equipment.value!!.hat.reinit()
            context.getString(R.string.chestplate)-> equipment.value!!.chest.reinit()
            context.getString(R.string.gloves)-> equipment.value!!.gloves.reinit()
            context.getString(R.string.greaves)-> equipment.value!!.greaves.reinit()
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