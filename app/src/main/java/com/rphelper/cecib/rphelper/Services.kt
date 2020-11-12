package com.rphelper.cecib.rphelper

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.utils.FileUtils
import com.rphelper.cecib.rphelper.utils.FirebaseQueryLiveData
import org.json.JSONObject
import java.lang.Exception
import kotlin.collections.ArrayList

object Services {

    @JvmStatic
    fun getUserDatabase():FirebaseQueryLiveData = FirebaseQueryLiveData(FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid))

    @JvmStatic
    fun getListOfEquipSpells(allSpells : ArrayList<Spell>): ArrayList<Spell> {
        var equipSpells = ArrayList<Spell>()
        var spells = ArrayList<Spell>()
        if (allSpells.isNotEmpty()) spells = allSpells
        spells.let {
            for (spell in spells!!) {
                if (spell.equip) equipSpells.add(spell)
            }
        }
        return equipSpells
    }

    @JvmStatic
    fun getListOfNotEquipSpells(allSpells : ArrayList<Spell>): MutableList<Spell> {
        var notEquipSpells = mutableListOf<Spell>()
        var spells = mutableListOf<Spell>()
        if (allSpells.isNotEmpty()) spells = allSpells
        spells.let {
            for (spell in spells!!) {
                if (!spell.equip) notEquipSpells.add(spell)
            }
        }
        return notEquipSpells
    }

    @JvmStatic
    fun getJsonCharacter(context: Context): Character {
        return Gson().fromJson<Character>(FileUtils.readJsonFile(context,context.getString(R.string.file_character)), Character::class.java)
    }

    @JvmStatic
    fun getJsonEquipment(context: Context): Equipment {
        return Gson().fromJson<Equipment>(FileUtils.readJsonFile(context,context.getString(R.string.file_equipment)), Equipment::class.java)
    }

    @JvmStatic
    fun getJsonWeapon(context: Context, type : String): Weapon {
        val weaponString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_equipment))).get(type).toString()
        return Gson().fromJson<Weapon>(weaponString, Weapon::class.java)
    }

    @JvmStatic
    fun getJsonSpells(context: Context): ArrayList<Spell> {
        val sType = object : TypeToken<ArrayList<Spell>>() { }.type
        return Gson().fromJson<ArrayList<Spell>>(FileUtils.readJsonFile(context, context.getString(R.string.file_spells)), sType)
    }

    @JvmStatic
    fun getJsonInventory(context: Context): Inventory {
        return Gson().fromJson<Inventory>(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory)), Inventory::class.java)
    }

    @JvmStatic
    fun getJsonFight(context: Context): Fight {
        return Gson().fromJson<Fight>(FileUtils.readJsonFile(context,context.getString(R.string.file_fight)), Fight::class.java)
    }

    @JvmStatic
    fun getLocalHistory(context: Context): ArrayList<String> {
        val sType = object : TypeToken<ArrayList<String>>() { }.type
        return Gson().fromJson<ArrayList<String>>(FileUtils.readJsonFile(context,context.getString(R.string.file_history)), sType)
    }


    /******** EDIT *******/
    @JvmStatic
    fun editCharacter(character: Character) = Firebase.database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/character").setValue(character)

    @JvmStatic
    fun editEquipment(equipment: Equipment) = Firebase.database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/equipment").setValue(equipment)

    @JvmStatic
    fun editSpells(spells: List<Spell>) = Firebase.database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/spells").setValue(spells)

    @JvmStatic
    fun editInventory(inventory : Inventory) = Firebase.database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/inventory").setValue(inventory)

    @JvmStatic
    fun editFight(fight: Fight) = Firebase.database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/fight").setValue(fight)

    @JvmStatic
    fun editLocalHistory(context: Context, entries: List<String>) {
        val entriesString = Gson().toJson(entries)
        FileUtils.editJsonFile(context, context.getString(R.string.file_history), entriesString)
    }
}