package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.*

open class MainViewModel(val context: Context) : ViewModel() {
    var firebaseQuery = Services.getUserDatabase()
    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?>? {
        return firebaseQuery
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

    val _fight = MutableLiveData<Fight>()
    val fight : LiveData<Fight> get() = _fight
    init {
        _fight.value = Fight()
    }

    val _allSpells = MutableLiveData< ArrayList<Spell>>()
    val allSpells : LiveData< ArrayList<Spell>> get() = _allSpells
    init {
        _allSpells.value = ArrayList<Spell>()
    }

    val _catalyst = MutableLiveData<Weapon>()
    val catalyst : LiveData<Weapon> get() = _catalyst
    init {
        _catalyst.value = Weapon()
    }

    val _inventory = MutableLiveData<Inventory>()
    val inventory : LiveData<Inventory> get() = _inventory
    init {
        _inventory.value = Inventory()
    }

    val _stuff = MutableLiveData<MutableList<Any>>()
    val stuff : LiveData<MutableList<Any>> get() = _stuff
    init {
        _stuff.value = ArrayList<Any>()
    }
}