package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.*

class InventoryViewModel (val context: Context) : ViewModel(){
    val _money = MutableLiveData<Int>()
    val money : LiveData<Int> get() = _money
    init {
        _money.value = Services.getMoney(context)
    }

    //TODO clean empty_inventory
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
}