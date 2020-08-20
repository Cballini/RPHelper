package com.rphelper.cecib.rphelper.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.utils.CalcUtils

class SpellViewModel(val context: Context) : ViewModel() {

    var firebaseQuery = Services.getUserDatabase()
    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?>? {
        return firebaseQuery
    }

    val _catalyst = MutableLiveData<Weapon>()
    val catalyst : LiveData<Weapon> get() = _catalyst
    init {
        _catalyst.value = Weapon()
    }

    val _character = MutableLiveData<Character>()
    val character : LiveData<Character> get() = _character
    init {
        _character.value = Character()
    }

    val _allSpells = MutableLiveData< ArrayList<Spell>>()
    val allSpells : LiveData< ArrayList<Spell>> get() = _allSpells
    init {
        _allSpells.value = ArrayList<Spell>()
    }

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
        if (Services.getListOfEquipSpells(allSpells.value!!).isNotEmpty()) _firstEquipSpell.value = Services.getListOfEquipSpells(allSpells.value!!)[0]
        else _firstEquipSpell.value = Spell()
    }
    fun getSpell2(){
        if (Services.getListOfEquipSpells(allSpells.value!!).size>1) _secondEquipSpell.value = Services.getListOfEquipSpells(allSpells.value!!)[1]
        else _secondEquipSpell.value = Spell()
    }
    fun getSpell3(){
        if (Services.getListOfEquipSpells(allSpells.value!!).size>2) _thirdEquipSpell.value = Services.getListOfEquipSpells(allSpells.value!!)[2]
        else _thirdEquipSpell.value = Spell()
    }
    fun getSpell4(){
        if (Services.getListOfEquipSpells(allSpells.value!!).size>3) _fourthEquipSpell.value = Services.getListOfEquipSpells(allSpells.value!!)[3]
        else _fourthEquipSpell.value = Spell()
    }
    fun getSpell5(){
        if (Services.getListOfEquipSpells(allSpells.value!!).size>4) _fifthEquipSpell.value = Services.getListOfEquipSpells(allSpells.value!!)[4]
        else _fifthEquipSpell.value = Spell()
    }
    fun getSpell6(){
        if (Services.getListOfEquipSpells(allSpells.value!!).size>5) _sixthEquipSpell.value = Services.getListOfEquipSpells(allSpells.value!!)[5]
        else _sixthEquipSpell.value = Spell()
    }

    fun getKnownSpells() = Services.getListOfNotEquipSpells(allSpells.value!!)

    fun getMaxEquipSpells():Int{
        val sharedPref: SharedPreferences = context.getSharedPreferences(Preferences.PREF_MODIFIER_MEM, Preferences.PRIVATE_MODE)
        val prefValue = sharedPref.getInt(Preferences.PREF_MODIFIER_MEM, 0)
        val mem = character.value!!.memory + prefValue
        var max = 3
        if(mem>=15){
            max = 4
        }
        if(mem>=25){
            max = 5
        }
        if(mem>=35){
            max = 6
        }
        return  max
    }

    fun getTotalDamage(spell: Spell):Int{
        var dmg = 0
        val cata = Services.getJsonWeapon(context, "catalyst")
        val character = Services.getJsonCharacter(context)
        if (cata.name.isNotEmpty() && spell.damage!=0){
            dmg = spell.damage + cata.boost
            dmg += (character.intelligence * cata.bonusInt.value + character.faith * cata.bonusFoi.value).toInt()
            if (spell.rapidFire) {
                val dmgTot = dmg
                dmg = dmgTot + dmgTot/2 + dmgTot/4
            }
        }
        return dmg
    }

    fun attack(spell: Spell){
        character.value!!.const.value -= 20
        character.value!!.mana.value -= spell.mana
        Services.editCharacter(character.value!!)
    }

    fun checkMana():Boolean{
        var check = false
        if(character.value!!.mana.value< CalcUtils.getManaMax(context, character.value!!)*0.2) check = true
        return check
    }
}