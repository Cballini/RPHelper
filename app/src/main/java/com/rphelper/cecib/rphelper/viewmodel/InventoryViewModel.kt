package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.PieceEquipment

class InventoryViewModel (val context: Context, inventory: Inventory, stuff: MutableList<Any>, character: Character, equipment: Equipment) : ViewModel(){

    var inventory = inventory
    var character = character
    var equipment = equipment

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value =getInventoryWeight()
    }

    val _stuff = MutableLiveData<MutableList<Any>>()
    val stuff : LiveData<MutableList<Any>> get() = _stuff
    init {
        _stuff.value =stuff
    }

    fun editInventory(){
        val inventory = Inventory(inventory.money, getWeapons(), getShields(), getArmors(), getJewels(), getItems())
        Services.editInventory(inventory)
    }

    fun getInventoryWeight():Float{
        var weight = 0F
        stuff?.let {
            for (item in stuff.value!!) {
                if (item is Item) weight += (item.weight * item.quantity)
                else if (item is Stuff) weight += item.weight
            }
        }
        return weight
    }

    fun getWeapons() :ArrayList<Weapon> = stuff.value!!.filter{ it is Weapon } as ArrayList<Weapon>
    fun getShields() :ArrayList<Shield> = stuff.value!!.filter { it is Shield } as ArrayList<Shield>
    fun getArmors() :ArrayList<Armor> = stuff.value!!.filter { it is Armor } as ArrayList<Armor>
    fun getJewels() :ArrayList<Jewel> = stuff.value!!.filter { it is Jewel } as ArrayList<Jewel>
    fun getItems() :ArrayList<Item> = stuff.value!!.filter { it is Item } as ArrayList<Item>

    fun weaponToEquipment(weapon: Weapon, isLeft : Boolean){
        stuff.value!!.remove(weapon)
        weapon.equip = true
        if(isLeft) {
            if(equipment.leftHand.name.isNotEmpty()) stuff.value!!.add(equipment.leftHand)
            equipment.leftHand = weapon

        }
        else {
            if(equipment.rightHand.name.isNotEmpty()) stuff.value!!.add(equipment.rightHand)
            equipment.rightHand = weapon
        }
        editInventory()
        Services.editEquipment(equipment)
    }

    fun catalystToEquipment(weapon: Weapon){
        stuff.value!!.remove(weapon)
        weapon.equip = true

        if(equipment.catalyst.name.isNotEmpty()) stuff.value!!.add(equipment.catalyst)
        equipment.catalyst = weapon

        editInventory()
        Services.editEquipment(equipment)
    }

    fun shieldToEquipment(shield: Shield){
        stuff.value!!.remove(shield)
        shield.equip = true
        if(equipment.shield.name.isNotEmpty())  stuff.value!!.add(equipment.shield)
        equipment.shield = shield
        editInventory()
        Services.editEquipment(equipment)
    }

    fun armorToEquipment(armor: Armor){
        stuff.value!!.remove(armor)
        armor.equip = true
        when(armor.type){
            PieceEquipment.HAT->{
                if(equipment.hat.name.isNotEmpty()) stuff.value!!.add(equipment.hat)
                equipment.hat = armor
            }
            PieceEquipment.CHEST->{
                if(equipment.chest.name.isNotEmpty()) stuff.value!!.add(equipment.chest)
                equipment.chest = armor
            }
            PieceEquipment.GLOVES->{
                if(equipment.gloves.name.isNotEmpty()) stuff.value!!.add(equipment.gloves)
                equipment.gloves = armor
            }
            PieceEquipment.GREAVES->{
                if(equipment.greaves.name.isNotEmpty()) stuff.value!!.add(equipment.greaves)
                equipment.greaves = armor
            }
        }
        editInventory()
        Services.editEquipment(equipment)
    }

    fun getLeftHand() = equipment.leftHand
    fun getRightHand() = equipment.rightHand
    fun getCatalyst() = equipment.catalyst
    fun getShield() = equipment.shield
    fun getHat() = equipment.hat
    fun getChest() = equipment.chest
    fun getGloves() = equipment.gloves
    fun getGreaves() = equipment.greaves

    fun orderStuffByType(){
        val list = ArrayList<Any>()
        list.addAll(getWeapons())
        list.addAll(getShields())
        list.addAll(getArmors())
        list.addAll(getJewels())
        list.addAll(getItems())
        _stuff.value = list
    }

    fun orderStuffWeaponFirst(){
        orderStuffByType()
    }
    fun orderStuffShieldFirst(){
        val list = ArrayList<Any>()
        list.addAll(getShields())
        list.addAll(getWeapons())
        list.addAll(getArmors())
        list.addAll(getJewels())
        list.addAll(getItems())
        _stuff.value = list
    }
    fun orderStuffArmorFirst(){
        val list = ArrayList<Any>()
        list.addAll(getArmors())
        list.addAll(getWeapons())
        list.addAll(getShields())
        list.addAll(getJewels())
        list.addAll(getItems())
        _stuff.value = list
    }
    fun orderStuffJewelFirst(){
        val list = ArrayList<Any>()
        list.addAll(getJewels())
        list.addAll(getWeapons())
        list.addAll(getShields())
        list.addAll(getArmors())
        list.addAll(getItems())
        _stuff.value = list
    }
    fun orderStuffItemFirst(){
        val list = ArrayList<Any>()
        list.addAll(getItems())
        list.addAll(getWeapons())
        list.addAll(getShields())
        list.addAll(getArmors())
        list.addAll(getJewels())
        _stuff.value = list
    }

    fun orderStuffByEquip(){
        var equiped = ArrayList<Any>()
        var ordered = ArrayList<Any>()
        for(s in stuff.value!!){
            if (s is Jewel){
                if (s.equip) {
                    equiped.add(s)
                }else{
                    ordered.add(s)
                }
            }else{
                ordered.add(s)
            }
        }
        val list = ArrayList<Any>()
        list.addAll(equiped)
        list.addAll(ordered)
        _stuff.value = list
    }

    fun orderStuffByAlphabetical(){
        val list = stuff.value
        list!!.sortBy { (it as Stuff).name }
        _stuff.value = list
    }

    fun addOne(position: Int){
        (stuff.value!![position] as Item).quantity +=1
        editInventory()
    }

    fun removeOne(position: Int){
        (stuff.value!![position] as Item).quantity -=1
        editInventory()
    }
}