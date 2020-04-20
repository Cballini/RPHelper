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
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Shield
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status
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
        _damages.value = getDamages()
    }

    val _defense = MutableLiveData<Float>()
    val defense : LiveData<Float> get() = _defense
    init {
        _defense.value = getDef()
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

    fun getDamages():Int{
        val character = Services.getCharacter(context)
        return 90 + 2*character.strength + character.dexterity
    }
    fun getDef() : Float {
        val character = Services.getCharacter(context)
        var def = 50F
        def += hat.value!!.def + chest.value!!.def + gloves.value!!.def + greaves.value!!.def
        def += (character.vitality/2 + character.memory/2 + character.endurance/2 + character.vigor
                + character.strength/2 + character.dexterity/2 + character.intelligence/2 + character.faith/2)
        return def
    }
    fun getRes():String{
        var res = "/"
        if (null != hat.value!!.res && null != chest.value!!.res && null != gloves.value!!.res && null != greaves.value!!.res) {
            res=""
            if (hat.value!!.res.contains(Elem.FIRE) && chest.value!!.res.contains(Elem.FIRE)
                    && gloves.value!!.res.contains(Elem.FIRE) && greaves.value!!.res.contains(Elem.FIRE)) res += Elem.FIRE.toString()
            if (hat.value!!.res.contains(Elem.MAGIC) && chest.value!!.res.contains(Elem.MAGIC)
                    && gloves.value!!.res.contains(Elem.MAGIC) && greaves.value!!.res.contains(Elem.MAGIC)) {
                if(res.isNotEmpty())res += "\n"
                res +=  Elem.MAGIC.toString()
            }
            if (hat.value!!.res.contains(Elem.LIGHTNING) && chest.value!!.res.contains(Elem.LIGHTNING)
                    && gloves.value!!.res.contains(Elem.LIGHTNING) && greaves.value!!.res.contains(Elem.LIGHTNING)) {
                if(res.isNotEmpty())res += "\n"
                res += Elem.LIGHTNING.toString()
            }
            if (hat.value!!.res.contains(Elem.DARKNESS) && chest.value!!.res.contains(Elem.DARKNESS)
                    && gloves.value!!.res.contains(Elem.DARKNESS) && greaves.value!!.res.contains(Elem.DARKNESS)){
                if(res.isNotEmpty())res += "\n"
                res += Elem.DARKNESS.toString()
            }
            if (hat.value!!.res.contains(Elem.ALL) && chest.value!!.res.contains(Elem.ALL)
                    && gloves.value!!.res.contains(Elem.ALL) && greaves.value!!.res.contains(Elem.ALL)){
                res += Elem.ALL.toString()
            }
        }
        return res
    }
    fun getWeak():String {
        var weak = "/"
        if (null != hat.value!!.weak && null != chest.value!!.weak && null != gloves.value!!.weak && null != greaves.value!!.weak){
            weak=""
            if (hat.value!!.weak.contains(Elem.FIRE) && chest.value!!.weak.contains(Elem.FIRE)
                    && gloves.value!!.weak.contains(Elem.FIRE) && greaves.value!!.weak.contains(Elem.FIRE)) weak += Elem.FIRE.toString()
        if (hat.value!!.weak.contains(Elem.MAGIC) && chest.value!!.weak.contains(Elem.MAGIC)
                && gloves.value!!.weak.contains(Elem.MAGIC) && greaves.value!!.weak.contains(Elem.MAGIC)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.MAGIC.toString()
        }
        if (hat.value!!.weak.contains(Elem.LIGHTNING) && chest.value!!.weak.contains(Elem.LIGHTNING)
                && gloves.value!!.weak.contains(Elem.LIGHTNING) && greaves.value!!.weak.contains(Elem.LIGHTNING)){
            if (weak.isNotEmpty())weak += "\n"
            weak +=Elem.LIGHTNING.toString()
        }
        if (hat.value!!.weak.contains(Elem.DARKNESS) && chest.value!!.weak.contains(Elem.DARKNESS)
                && gloves.value!!.weak.contains(Elem.DARKNESS) && greaves.value!!.weak.contains(Elem.DARKNESS)){
            if (weak.isNotEmpty())weak += "\n"
            weak += Elem.DARKNESS.toString()
        }
            if (hat.value!!.weak.contains(Elem.ALL) && chest.value!!.weak.contains(Elem.ALL)
                    && gloves.value!!.weak.contains(Elem.ALL) && greaves.value!!.weak.contains(Elem.ALL)) weak += Elem.FIRE.toString()
    }
        return weak
    }

    fun getImmun():String{
        var immun = "/"
        if (null != hat.value!!.immun && null != chest.value!!.immun && null != gloves.value!!.immun && null != greaves.value!!.immun) {
            immun=""
            if (hat.value!!.immun.contains(Status.BLEED) && chest.value!!.immun.contains(Status.BLEED)
                    && gloves.value!!.immun.contains(Status.BLEED) && greaves.value!!.immun.contains(Status.BLEED)) immun += Status.BLEED.toString()
            if (hat.value!!.immun.contains(Status.FROST) && chest.value!!.immun.contains(Status.FROST)
                    && gloves.value!!.immun.contains(Status.FROST) && greaves.value!!.immun.contains(Status.FROST)){
                if (immun.isNotEmpty())immun += "\n"
                immun +=Status.FROST.toString()
            }
            if (hat.value!!.immun.contains(Status.POISON) && chest.value!!.immun.contains(Status.POISON)
                    && gloves.value!!.immun.contains(Status.POISON) && greaves.value!!.immun.contains(Status.POISON)){
                if (immun.isNotEmpty())immun += "\n"
                immun += Status.POISON.toString()
            }
        }
        return immun
    }

    fun getTotalDamage(weapon: Weapon):Int{
        val character = Services.getCharacter(context)
        var dmg = damages.value!! + weapon.damage
        dmg += (character.strength*weapon.bonusFor.value + character.dexterity*weapon.bonusDex.value).toInt()
        return dmg
    }

    fun editEquipment(){
        val equipment = Equipment(leftHand.value!!, rightHand.value!!, catalyst.value!!, shield.value!!, hat.value!!, chest.value!!, gloves.value!!, greaves.value!!)
        Services.editEquipment(context, equipment)
        _leftHand.value = Services.getWeapon(context, "leftHand")
        _rightHand.value = Services.getWeapon(context, "rightHand")
        _catalyst.value = Services.getWeapon(context, "catalyst")
        _shield.value = Services.getShield(context)
        _hat.value = Services.getArmor(context, "hat")
        _chest.value = Services.getArmor(context, "chest")
        _gloves.value = Services.getArmor(context, "gloves")
        _greaves.value = Services.getArmor(context, "greaves")
        _damages.value = getDamages()
        _defense.value = getDef()
        _res.value = getRes()
        _immun.value = getImmun()
        _weak.value = getWeak()
    }
}