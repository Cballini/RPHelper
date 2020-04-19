package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Inventory
import com.rphelper.cecib.rphelper.dto.Item

class InventoryViewModel (val context: Context) : ViewModel(){
    val _money = MutableLiveData<Int>()
    val money : LiveData<Int> get() = _money
    init {
         _money.value = Services.getMoney(context)
    }

    val _items = MutableLiveData<ArrayList<Item>>()
    val items : LiveData<ArrayList<Item>> get() = _items
    init {
        _items.value = Services.getItems(context)
    }

    fun editInventory(){
        val inventory = Inventory(money.value!!, items.value!!)
        Services.editInventory(context, inventory)
        _money.value = Services.getMoney(context)
        _items.value = Services.getItems(context)
    }

}