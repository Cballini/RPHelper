package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.utils.FileUtils

class SpellViewModel(val context: Context) : ViewModel() {

    val _firstEquipSpell = MutableLiveData<Spell>()
    val firstEquipSpell : LiveData<Spell> get() = _firstEquipSpell
    init {
        val listEquipSpells = getListOfEquipSpells()
        if (listEquipSpells.isNotEmpty()) _firstEquipSpell.value = listEquipSpells[0]
    }

    val _secondEquipSpell = MutableLiveData<Spell>()
    val secondEquipSpell : LiveData<Spell> get() = _secondEquipSpell
    init {
        val listEquipSpells = getListOfEquipSpells()
        if (listEquipSpells.size>1) _secondEquipSpell.value = listEquipSpells[1]
    }

    val _thirdEquipSpell = MutableLiveData<Spell>()
    val thirdEquipSpell : LiveData<Spell> get() = _thirdEquipSpell
    init {
        val listEquipSpells = getListOfEquipSpells()
        if (listEquipSpells.size>2) _thirdEquipSpell.value = listEquipSpells[2]
    }

    val _fourthEquipSpell = MutableLiveData<Spell>()
    val fourthEquipSpell : LiveData<Spell> get() = _fourthEquipSpell
    init {
        val listEquipSpells = getListOfEquipSpells()
        if (listEquipSpells.size == 4) _fourthEquipSpell.value = listEquipSpells[3]
    }

    val _knownSpells = MutableLiveData<List<Spell>>()
    val knownSpells : LiveData<List<Spell>> get() = _knownSpells
    init {
        _knownSpells.value = getListOfNotEquipSpells()
    }

    fun getListOfEquipSpells(): List<Spell> {
        var equipSpells = ArrayList<Spell>()
        val allSpells = getListOfSpells()
        allSpells.let {
            for (spell in allSpells!!) {
                if (spell.equip) equipSpells.add(spell)
            }
        }
        return equipSpells
    }

    fun getListOfNotEquipSpells(): List<Spell> {
        var notEquipSpells = ArrayList<Spell>()
        val allSpells = getListOfSpells()
        allSpells.let {
            for (spell in allSpells!!) {
                if (!spell.equip) notEquipSpells.add(spell)
            }
        }
        return notEquipSpells
    }

    fun getListOfSpells(): List<Spell>? {
        val sType = object : TypeToken<List<Spell>>() { }.type
        return Gson().fromJson<List<Spell>>(getSpellsString(), sType)
    }

    fun getSpellsString(): String {
        return FileUtils.readJsonAsset(context, "spells.json")
    }
}