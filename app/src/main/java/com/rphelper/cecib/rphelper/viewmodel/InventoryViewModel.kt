package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.PieceEquipment

class InventoryViewModel (val context: Context) : ViewModel(){
    var firebaseQuery = Services.getUserDatabase()
    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?>? {
        return firebaseQuery
    }

    val _inventory = MutableLiveData<Inventory>()
    val inventory : LiveData<Inventory> get() = _inventory
    init {
        _inventory.value = Inventory()
    }

    val _money = MutableLiveData<Int>()
    val money : LiveData<Int> get() = _money
    init {
        _money.value = 0
    }

    val _stuff = MutableLiveData<MutableList<Any>>()
    val stuff : LiveData<MutableList<Any>> get() = _stuff
    init {
        _stuff.value = ArrayList<Any>()
    }

    val _weight = MutableLiveData<Float>()
    val weight : LiveData<Float> get() = _weight
    init {
        _weight.value =getInventoryWeight()
    }

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Character()
    }

    val _equipment = MutableLiveData<Equipment>()
    val equipment : LiveData<Equipment> get() = _equipment
    init {
        _equipment.value = Equipment()
    }

    fun editInventory(){
        val inventory = Inventory(money.value!!, getWeapons(), getShields(), getArmors(), getJewels(), getItems())
        Services.editInventory(inventory)
    }

    fun getInventoryWeight():Float{
        var weight = 0F
        for (item in stuff.value!!){
            if (item is Stuff)  weight += item.weight
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
            if(equipment.value!!.leftHand.name.isNotEmpty()) stuff.value!!.add(equipment.value!!.leftHand)
            equipment.value!!.leftHand = weapon

        }
        else {
            if(equipment.value!!.rightHand.name.isNotEmpty()) stuff.value!!.add(equipment.value!!.rightHand)
            equipment.value!!.rightHand = weapon
        }
        editInventory()
        Services.editEquipment(equipment.value!!)
    }

    fun catalystToEquipment(weapon: Weapon){
        stuff.value!!.remove(weapon)
        weapon.equip = true

        if(equipment.value!!.catalyst.name.isNotEmpty()) stuff.value!!.add(equipment.value!!.catalyst)
        equipment.value!!.catalyst = weapon

        editInventory()
        Services.editEquipment(equipment.value!!)
    }

    fun shieldToEquipment(shield: Shield){
        stuff.value!!.remove(shield)
        shield.equip = true
        if(equipment.value!!.shield.name.isNotEmpty())  stuff.value!!.add(equipment.value!!.shield)
        equipment.value!!.shield = shield
        editInventory()
        Services.editEquipment(equipment.value!!)
    }

    fun armorToEquipment(armor: Armor){
        stuff.value!!.remove(armor)
        armor.equip = true
        when(armor.type){
            PieceEquipment.HAT->{
                if(equipment.value!!.hat.name.isNotEmpty()) stuff.value!!.add(equipment.value!!.hat)
                equipment.value!!.hat = armor
            }
            PieceEquipment.CHEST->{
                if(equipment.value!!.chest.name.isNotEmpty()) stuff.value!!.add(equipment.value!!.chest)
                equipment.value!!.chest = armor
            }
            PieceEquipment.GLOVES->{
                if(equipment.value!!.gloves.name.isNotEmpty()) stuff.value!!.add(equipment.value!!.gloves)
                equipment.value!!.gloves = armor
            }
            PieceEquipment.GREAVES->{
                if(equipment.value!!.greaves.name.isNotEmpty()) stuff.value!!.add(equipment.value!!.greaves)
                equipment.value!!.greaves = armor
            }
        }
        editInventory()
        Services.editEquipment(equipment.value!!)
    }

    fun getLeftHand() = equipment.value!!.leftHand
    fun getRightHand() = equipment.value!!.rightHand
    fun getCatalyst() = equipment.value!!.catalyst
    fun getShield() = equipment.value!!.shield
    fun getHat() = equipment.value!!.hat
    fun getChest() = equipment.value!!.chest
    fun getGloves() = equipment.value!!.gloves
    fun getGreaves() = equipment.value!!.greaves
}