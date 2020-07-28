package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.PieceEquipment
import com.rphelper.cecib.rphelper.enums.Status
import com.rphelper.cecib.rphelper.utils.CalcUtils

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
        _hat.value!!.type = PieceEquipment.HAT
    }

    val _chest = MutableLiveData<Armor>()
    val chest : LiveData<Armor> get() = _chest
    init {
        _chest.value = Services.getArmor(context, "chest")
        _chest.value!!.type = PieceEquipment.CHEST
    }

    val _gloves = MutableLiveData<Armor>()
    val gloves : LiveData<Armor> get() = _gloves
    init {
        _gloves.value = Services.getArmor(context, "gloves")
        _gloves.value!!.type = PieceEquipment.GLOVES
    }

    val _greaves = MutableLiveData<Armor>()
    val greaves : LiveData<Armor> get() = _greaves
    init {
        _greaves.value = Services.getArmor(context, "greaves")
        _greaves.value!!.type = PieceEquipment.GREAVES
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
        _defense.value = CalcUtils.getDef(context)
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

    fun getDamages():Int = CalcUtils.getDamages(context)


    fun getDamagesBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DAMAGES_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_DAMAGES_TEMP, 0)
    }

    fun getDefBonus():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_DEFENSE_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getInt(Preferences.PREF_MODIFIER_DEFENSE_TEMP, 0)
    }

    fun getResBonus():String{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_RES_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getString(Preferences.PREF_MODIFIER_RES_TEMP, "")
    }
    fun getWeakBonus():String{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getString(Preferences.PREF_MODIFIER_WEAK_TEMP, "")
    }
    fun getImmunBonus():String{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN_TEMP, Preferences.PRIVATE_MODE)
        return sharedPref.getString(Preferences.PREF_MODIFIER_IMMUN_TEMP, "")
    }

    fun getRes():String{
        var res = "/"
        if (null != hat.value!!.res && null != chest.value!!.res && null != gloves.value!!.res && null != greaves.value!!.res) {
            res=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_RES, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_RES,"")
            pref += " " + context.getSharedPreferences(Preferences.PREF_MODIFIER_RES_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_RES_TEMP,"")
            if ((hat.value!!.res.contains(Elem.FIRE) && chest.value!!.res.contains(Elem.FIRE)
                    && gloves.value!!.res.contains(Elem.FIRE) && greaves.value!!.res.contains(Elem.FIRE)) || pref.contains(Elem.FIRE.name)) res += Elem.FIRE.toString()
            if ((hat.value!!.res.contains(Elem.MAGIC) && chest.value!!.res.contains(Elem.MAGIC)
                    && gloves.value!!.res.contains(Elem.MAGIC) && greaves.value!!.res.contains(Elem.MAGIC)) || pref.contains(Elem.MAGIC.name)) {
                if(res.isNotEmpty())res += "\n"
                res +=  Elem.MAGIC.toString()
            }
            if ((hat.value!!.res.contains(Elem.LIGHTNING) && chest.value!!.res.contains(Elem.LIGHTNING)
                    && gloves.value!!.res.contains(Elem.LIGHTNING) && greaves.value!!.res.contains(Elem.LIGHTNING)) || pref.contains(Elem.LIGHTNING.name)) {
                if(res.isNotEmpty())res += "\n"
                res += Elem.LIGHTNING.toString()
            }
            if ((hat.value!!.res.contains(Elem.DARKNESS) && chest.value!!.res.contains(Elem.DARKNESS)
                    && gloves.value!!.res.contains(Elem.DARKNESS) && greaves.value!!.res.contains(Elem.DARKNESS)) || pref.contains(Elem.DARKNESS.name)){
                if(res.isNotEmpty())res += "\n"
                res += Elem.DARKNESS.toString()
            }
            if (hat.value!!.res.contains(Elem.ALL) && chest.value!!.res.contains(Elem.ALL)
                    && gloves.value!!.res.contains(Elem.ALL) && greaves.value!!.res.contains(Elem.ALL)){
                res = Elem.ALL.toString()
            }
        }
        return res
    }
    fun getWeak():String {
        var weak = "/"
        if (null != hat.value!!.weak && null != chest.value!!.weak && null != gloves.value!!.weak && null != greaves.value!!.weak){
            weak=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK,"")
            pref +=  " "  + context.getSharedPreferences(Preferences.PREF_MODIFIER_WEAK_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_WEAK_TEMP,"")
            if ((hat.value!!.weak.contains(Elem.FIRE) || chest.value!!.weak.contains(Elem.FIRE)
                    || gloves.value!!.weak.contains(Elem.FIRE) || greaves.value!!.weak.contains(Elem.FIRE)) || pref.contains(Elem.FIRE.name)) weak += Elem.FIRE.toString()
        if ((hat.value!!.weak.contains(Elem.MAGIC) || chest.value!!.weak.contains(Elem.MAGIC)
                || gloves.value!!.weak.contains(Elem.MAGIC) || greaves.value!!.weak.contains(Elem.MAGIC)) || pref.contains(Elem.MAGIC.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.MAGIC.toString()
        }
        if ((hat.value!!.weak.contains(Elem.LIGHTNING) || chest.value!!.weak.contains(Elem.LIGHTNING)
                || gloves.value!!.weak.contains(Elem.LIGHTNING) || greaves.value!!.weak.contains(Elem.LIGHTNING)) || pref.contains(Elem.LIGHTNING.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak +=Elem.LIGHTNING.toString()
        }
        if ((hat.value!!.weak.contains(Elem.DARKNESS) || chest.value!!.weak.contains(Elem.DARKNESS)
                || gloves.value!!.weak.contains(Elem.DARKNESS) || greaves.value!!.weak.contains(Elem.DARKNESS)) || pref.contains(Elem.DARKNESS.name)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.DARKNESS.toString()
        }
            if (hat.value!!.weak.contains(Elem.ALL) || chest.value!!.weak.contains(Elem.ALL)
                    || gloves.value!!.weak.contains(Elem.ALL) || greaves.value!!.weak.contains(Elem.ALL)) weak += Elem.FIRE.toString()
    }
        return weak
    }

    fun getImmun():String{
        var immun = "/"
        if (null != hat.value!!.immun && null != chest.value!!.immun && null != gloves.value!!.immun && null != greaves.value!!.immun) {
            immun=""
            var pref = context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_IMMUN,"")
            pref += " " + context.getSharedPreferences(Preferences.PREF_MODIFIER_IMMUN_TEMP, Preferences.PRIVATE_MODE).getString(Preferences.PREF_MODIFIER_IMMUN_TEMP,"")
            if ((hat.value!!.immun.contains(Status.BLEED) && chest.value!!.immun.contains(Status.BLEED)
                    && gloves.value!!.immun.contains(Status.BLEED) && greaves.value!!.immun.contains(Status.BLEED)) || pref.contains(Status.BLEED.name)) immun += Status.BLEED.toString()
            if ((hat.value!!.immun.contains(Status.FROST) && chest.value!!.immun.contains(Status.FROST)
                    && gloves.value!!.immun.contains(Status.FROST) && greaves.value!!.immun.contains(Status.FROST)) || pref.contains(Status.FROST.name)){
                if (immun.isNotEmpty())immun += "\n"
                immun +=Status.FROST.toString()
            }
            if ((hat.value!!.immun.contains(Status.POISON) && chest.value!!.immun.contains(Status.POISON)
                    && gloves.value!!.immun.contains(Status.POISON) && greaves.value!!.immun.contains(Status.POISON)) || pref.contains(Status.POISON.name)){
                if (immun.isNotEmpty())immun += "\n"
                immun += Status.POISON.toString()
            }
        }
        return immun
    }

    fun getTotalDamage(weapon: Weapon):Int = CalcUtils.getTotalDamage(weapon, context)

    fun editEquipment(){
        val equipment = Equipment(leftHand.value!!, rightHand.value!!, catalyst.value!!, shield.value!!, hat.value!!, chest.value!!, gloves.value!!, greaves.value!!)
        Services.editEquipment(context, equipment)
        updateEquipment()
    }

    fun updateEquipment(){
        _leftHand.value = Services.getWeapon(context, "leftHand")
        _rightHand.value = Services.getWeapon(context, "rightHand")
        _catalyst.value = Services.getWeapon(context, "catalyst")
        _shield.value = Services.getShield(context)
        _hat.value = Services.getArmor(context, "hat")
        _chest.value = Services.getArmor(context, "chest")
        _gloves.value = Services.getArmor(context, "gloves")
        _greaves.value = Services.getArmor(context, "greaves")
        _damages.value = getDamages()
        _defense.value = CalcUtils.getDef(context)
        _res.value = getRes()
        _immun.value = getImmun()
        _weak.value = getWeak()
        _damagesBonus.value = getDamagesBonus()
        _defenseBonus.value = getDefBonus()
        _resBonus.value = getResBonus()
        _weakBonus.value = getWeakBonus()
        _immunBonus.value = getImmunBonus()
    }

    fun weaponToInventory(type :String, weapon: Weapon){
        val inventory = Services.getInventory(context)
        weapon.equip = false
        inventory.weapons.add(weapon)
        Services.editInventory(context, inventory)
        when(type){
            context.getString(R.string.left_hand)-> leftHand.value!!.reinit()
            context.getString(R.string.right_hand)-> rightHand.value!!.reinit()
            context.getString(R.string.catalyst)-> catalyst.value!!.reinit()
        }
        editEquipment()
    }

    fun shieldToInventory(shield: Shield){
        val inventory = Services.getInventory(context)
        shield.equip = false
        inventory.shields.add(shield)
        Services.editInventory(context, inventory)
        shield.reinit()
        editEquipment()
    }

    fun armorToInventory(type :String, armor: Armor){
        val inventory = Services.getInventory(context)
        armor.equip = false
        inventory.armors.add(armor)
        Services.editInventory(context, inventory)
        when(type){
            context.getString(R.string.hat)-> hat.value!!.reinit()
            context.getString(R.string.chestplate)-> chest.value!!.reinit()
            context.getString(R.string.gloves)-> gloves.value!!.reinit()
            context.getString(R.string.greaves)-> greaves.value!!.reinit()
        }
        editEquipment()
    }

}