package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.rphelper.cecib.rphelper.*
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.utils.CalcUtils

class SpellViewModel(val context: Context, character: Character, allSpells : ArrayList<Spell>, catalyst : Weapon) : ViewModel() {
    
    var character = character
    var allSpells = allSpells
    var catalyst = catalyst

    val _firstEquipSpell = MutableLiveData<Spell>()
    val firstEquipSpell : LiveData<Spell> get() = _firstEquipSpell
    init {
        getSpell1()
    }

    val _secondEquipSpell = MutableLiveData<Spell>()
    val secondEquipSpell : LiveData<Spell> get() = _secondEquipSpell
    init {
        getSpell2()
    }

    val _thirdEquipSpell = MutableLiveData<Spell>()
    val thirdEquipSpell : LiveData<Spell> get() = _thirdEquipSpell
    init {
        getSpell3()
    }

    val _fourthEquipSpell = MutableLiveData<Spell>()
    val fourthEquipSpell : LiveData<Spell> get() = _fourthEquipSpell
    init {
        getSpell4()
    }

    val _fifthEquipSpell = MutableLiveData<Spell>()
    val fifthEquipSpell : LiveData<Spell> get() = _fifthEquipSpell
    init {
        getSpell5()
    }

    val _sixthEquipSpell = MutableLiveData<Spell>()
    val sixthEquipSpell : LiveData<Spell> get() = _sixthEquipSpell
    init {
        getSpell6()
    }

    val _knownSpells = MutableLiveData<MutableList<Spell>>()
    val knownSpells : LiveData<MutableList<Spell>> get() = _knownSpells
    init {
        _knownSpells.value = getKnownSpells()
    }

    fun editSpells(){
        val spellsList = ArrayList<Spell>()
        spellsList.add(firstEquipSpell.value!!)
        spellsList.add(secondEquipSpell.value!!)
        spellsList.add(thirdEquipSpell.value!!)
        spellsList.add(fourthEquipSpell.value!!)
        spellsList.add(fifthEquipSpell.value!!)
        spellsList.add(sixthEquipSpell.value!!)
        spellsList.addAll(knownSpells.value!!)
        Services.editSpells(spellsList)
    }

    fun getSpell1(){
        if (Services.getListOfEquipSpells(allSpells).isNotEmpty()) _firstEquipSpell.value = Services.getListOfEquipSpells(allSpells)[0]
        else _firstEquipSpell.value = Spell()
    }
    fun getSpell2(){
        if (Services.getListOfEquipSpells(allSpells).size>1) _secondEquipSpell.value = Services.getListOfEquipSpells(allSpells)[1]
        else _secondEquipSpell.value = Spell()
    }
    fun getSpell3(){
        if (Services.getListOfEquipSpells(allSpells).size>2) _thirdEquipSpell.value = Services.getListOfEquipSpells(allSpells)[2]
        else _thirdEquipSpell.value = Spell()
    }
    fun getSpell4(){
        if (Services.getListOfEquipSpells(allSpells).size>3) _fourthEquipSpell.value = Services.getListOfEquipSpells(allSpells)[3]
        else _fourthEquipSpell.value = Spell()
    }
    fun getSpell5(){
        if (Services.getListOfEquipSpells(allSpells).size>4) _fifthEquipSpell.value = Services.getListOfEquipSpells(allSpells)[4]
        else _fifthEquipSpell.value = Spell()
    }
    fun getSpell6(){
        if (Services.getListOfEquipSpells(allSpells).size>5) _sixthEquipSpell.value = Services.getListOfEquipSpells(allSpells)[5]
        else _sixthEquipSpell.value = Spell()
    }

    fun getKnownSpells() = Services.getListOfNotEquipSpells(allSpells)

    fun getMaxEquipSpells():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_MEM, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_MEM, 0)
        val mem = character.memory + prefValue
        var max = NB_SPELLS_BASE_MEMORY
        if(mem>= MEMORY_FLOOR_1){
            max = NB_SPELLS_BASE_MEMORY+1
        }
        if(mem>= MEMORY_FLOOR_2){
            max = NB_SPELLS_BASE_MEMORY+2
        }
        if(mem>= MEMORY_FLOOR_3){
            max = NB_SPELLS_BASE_MEMORY+3
        }
        return  max
    }

    fun getTotalDamage(spell: Spell):Int{
        var dmg = 0
        if (catalyst.name.isNotEmpty() && spell.damage!=0){
            dmg = spell.damage + catalyst.boost
            dmg += (character.intelligence * catalyst.bonusInt.value + character.faith * catalyst.bonusFoi.value).toInt()
            if (spell.rapidFire) {
                val dmgTot = dmg
                dmg = dmgTot + dmgTot/2 + dmgTot/4
            }
        }
        return dmg
    }

    fun attack(spell: Spell){
        character.const.value -= COST_SPELL_CONST
        character.mana.value -= spell.mana
        Services.editCharacter(character)
    }

    fun checkMana():Boolean{
        var check = false
        if(character.mana.value< CalcUtils.getManaMax(context, character)*0.2) check = true
        return check
    }
}