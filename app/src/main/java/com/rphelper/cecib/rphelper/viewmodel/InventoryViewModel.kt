package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.PieceEquipment

class InventoryViewModel (val context: Context) : ViewModel(){
    val _money = MutableLiveData<Int>()
    val money : LiveData<Int> get() = _money
    init {
        _money.value = Services.getMoney(context)
    }

    //TODO rename
    val _items = MutableLiveData<ArrayList<Any>>()
    val items : LiveData<ArrayList<Any>> get() = _items
    init {
        _items.value = Services.getStuff(context)
    }

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value =getInventoryWeight()
    }

    fun editInventory(){
        val inventory = Inventory(money.value!!, getWeapons(), getShields(), getArmors(), getJewels(), getItems())
        Services.editInventory(context, inventory)
        _money.value = Services.getMoney(context)
        _items.value = Services.getStuff(context)
        _weight.value =getInventoryWeight()
    }

    fun getInventoryWeight():Float{
        var weight = 0F
        for (item in items.value!!){
            if (item is Stuff)  weight += item.weight
        }
        return weight
    }

    fun getWeapons() :ArrayList<Weapon> = items.value!!.filter{ it is Weapon } as ArrayList<Weapon>
    fun getShields() :ArrayList<Shield> = items.value!!.filter { it is Shield } as ArrayList<Shield>
    fun getArmors() :ArrayList<Armor> = items.value!!.filter { it is Armor } as ArrayList<Armor>
    fun getJewels() :ArrayList<Jewel> = items.value!!.filter { it is Jewel } as ArrayList<Jewel>
    fun getItems() :ArrayList<Item> = items.value!!.filter { it is Item } as ArrayList<Item>

    fun weaponToEquipment(weapon: Weapon, isLeft : Boolean){
        items.value!!.remove(weapon)
        val equipment = Services.getEquipment(context)
        weapon.equip = true
        if(isLeft) {
            if(equipment.leftHand.name.isNotEmpty()) items.value!!.add(equipment.leftHand)
            equipment.leftHand = weapon

        }
        else {
            if(equipment.rightHand.name.isNotEmpty()) items.value!!.add(equipment.rightHand)
            equipment.rightHand = weapon
        }
        editInventory()
        Services.editEquipment(context, equipment)
    }

    fun catalystToEquipment(weapon: Weapon){
        items.value!!.remove(weapon)
        val equipment = Services.getEquipment(context)
        weapon.equip = true

        if(equipment.catalyst.name.isNotEmpty()) items.value!!.add(equipment.catalyst)
        equipment.catalyst = weapon

        editInventory()
        Services.editEquipment(context, equipment)
    }

    fun shieldToEquipment(shield: Shield){
        items.value!!.remove(shield)
        val equipment = Services.getEquipment(context)
        shield.equip = true
        if(equipment.shield.name.isNotEmpty())  items.value!!.add(equipment.shield)
        equipment.shield = shield
        editInventory()
        Services.editEquipment(context, equipment)
    }

    fun armorToEquipment(armor: Armor){
        items.value!!.remove(armor)
        val equipment = Services.getEquipment(context)
        armor.equip = true
        when(armor.type){
            PieceEquipment.HAT->{
                if(equipment.hat.name.isNotEmpty()) items.value!!.add(equipment.hat)
                equipment.hat = armor
            }
            PieceEquipment.CHEST->{
                if(equipment.chest.name.isNotEmpty()) items.value!!.add(equipment.chest)
                equipment.chest = armor
            }
            PieceEquipment.GLOVES->{
                if(equipment.gloves.name.isNotEmpty()) items.value!!.add(equipment.gloves)
                equipment.gloves = armor
            }
            PieceEquipment.GREAVES->{
                if(equipment.greaves.name.isNotEmpty()) items.value!!.add(equipment.greaves)
                equipment.greaves = armor
            }
        }
        editInventory()
        Services.editEquipment(context, equipment)
    }

    fun getLeftHand() = Services.getEquipment(context).leftHand
    fun getRightHand() = Services.getEquipment(context).rightHand
    fun getCatalyst() = Services.getEquipment(context).catalyst
    fun getShield() = Services.getEquipment(context).shield
    fun getHat() = Services.getEquipment(context).hat
    fun getChest() = Services.getEquipment(context).chest
    fun getGloves() = Services.getEquipment(context).gloves
    fun getGreaves() = Services.getEquipment(context).greaves
}