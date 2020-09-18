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
    var stuff = stuff
    var character = character
    var equipment = equipment

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value =getInventoryWeight()
    }

    fun editInventory(){
        val inventory = Inventory(inventory.money, getWeapons(), getShields(), getArmors(), getJewels(), getItems())
        Services.editInventory(inventory)
    }

    fun getInventoryWeight():Float{
        var weight = 0F
        for (item in stuff){
            if (item is Stuff)  weight += item.weight
        }
        return weight
    }

    fun getWeapons() :ArrayList<Weapon> = stuff.filter{ it is Weapon } as ArrayList<Weapon>
    fun getShields() :ArrayList<Shield> = stuff.filter { it is Shield } as ArrayList<Shield>
    fun getArmors() :ArrayList<Armor> = stuff.filter { it is Armor } as ArrayList<Armor>
    fun getJewels() :ArrayList<Jewel> = stuff.filter { it is Jewel } as ArrayList<Jewel>
    fun getItems() :ArrayList<Item> = stuff.filter { it is Item } as ArrayList<Item>

    fun weaponToEquipment(weapon: Weapon, isLeft : Boolean){
        stuff.remove(weapon)
        weapon.equip = true
        if(isLeft) {
            if(equipment.leftHand.name.isNotEmpty()) stuff.add(equipment.leftHand)
            equipment.leftHand = weapon

        }
        else {
            if(equipment.rightHand.name.isNotEmpty()) stuff.add(equipment.rightHand)
            equipment.rightHand = weapon
        }
        editInventory()
        Services.editEquipment(equipment)
    }

    fun catalystToEquipment(weapon: Weapon){
        stuff.remove(weapon)
        weapon.equip = true

        if(equipment.catalyst.name.isNotEmpty()) stuff.add(equipment.catalyst)
        equipment.catalyst = weapon

        editInventory()
        Services.editEquipment(equipment)
    }

    fun shieldToEquipment(shield: Shield){
        stuff.remove(shield)
        shield.equip = true
        if(equipment.shield.name.isNotEmpty())  stuff.add(equipment.shield)
        equipment.shield = shield
        editInventory()
        Services.editEquipment(equipment)
    }

    fun armorToEquipment(armor: Armor){
        stuff.remove(armor)
        armor.equip = true
        when(armor.type){
            PieceEquipment.HAT->{
                if(equipment.hat.name.isNotEmpty()) stuff.add(equipment.hat)
                equipment.hat = armor
            }
            PieceEquipment.CHEST->{
                if(equipment.chest.name.isNotEmpty()) stuff.add(equipment.chest)
                equipment.chest = armor
            }
            PieceEquipment.GLOVES->{
                if(equipment.gloves.name.isNotEmpty()) stuff.add(equipment.gloves)
                equipment.gloves = armor
            }
            PieceEquipment.GREAVES->{
                if(equipment.greaves.name.isNotEmpty()) stuff.add(equipment.greaves)
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
}