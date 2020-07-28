package com.rphelper.cecib.rphelper

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.utils.FileUtils
import org.json.JSONObject
import java.lang.Exception
import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.collections.ArrayList

object Services {

    var allSpells = ArrayList<Spell>()

    @JvmStatic
    fun getCharacter(context: Context): Character {
        return Gson().fromJson<Character>(FileUtils.readJsonFile(context,context.getString(R.string.file_character)), Character::class.java)
    }

    @JvmStatic
    fun getEquipment(context: Context): Equipment {
        return Gson().fromJson<Equipment>(FileUtils.readJsonFile(context,context.getString(R.string.file_equipment)), Equipment::class.java)
    }

    @JvmStatic
    fun getWeapon(context: Context, type : String): Weapon {
        val weaponString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_equipment))).get(type).toString()
        return Gson().fromJson<Weapon>(weaponString, Weapon::class.java)
    }

    @JvmStatic
    fun getShield(context: Context): Shield {
        val shieldString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_equipment))).get("shield").toString()
        return Gson().fromJson<Shield>(shieldString, Shield::class.java)
    }

    @JvmStatic
    fun getArmor(context: Context, type : String): Armor {
        val armorString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_equipment))).get(type).toString()
        return Gson().fromJson<Armor>(armorString, Armor::class.java)
    }

    @JvmStatic
    fun getSpells(context: Context): ArrayList<Spell> {
        val sType = object : TypeToken<ArrayList<Spell>>() { }.type
        return Gson().fromJson<ArrayList<Spell>>(FileUtils.readJsonFile(context, context.getString(R.string.file_spells)), sType)
    }

    @JvmStatic
    fun getListOfEquipSpells(context: Context): ArrayList<Spell> {
        var equipSpells = ArrayList<Spell>()
        var spells = ArrayList<Spell>()
        if (allSpells.isNotEmpty()) spells = allSpells else {allSpells = getSpells(context); spells = allSpells}
        spells.let {
            for (spell in spells!!) {
                if (spell.equip) equipSpells.add(spell)
            }
        }
        return equipSpells
    }

    @JvmStatic
    fun getListOfNotEquipSpells(context: Context): MutableList<Spell> {
        var notEquipSpells = mutableListOf<Spell>()
        var spells = mutableListOf<Spell>()
        if (allSpells.isNotEmpty()) spells = allSpells else {allSpells = getSpells(context); spells = allSpells}
        spells.let {
            for (spell in spells!!) {
                if (!spell.equip) notEquipSpells.add(spell)
            }
        }
        return notEquipSpells
    }

    @JvmStatic
    fun getInventory(context: Context): Inventory {
        return Gson().fromJson<Inventory>(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory)), Inventory::class.java)
    }

    @JvmStatic
    fun getMoney(context: Context): Int {
        val moneyString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory))).get("money").toString()
        return Gson().fromJson<Int>(moneyString, Int::class.java)
    }

    @JvmStatic
    fun getItems(context: Context): ArrayList<Item> {
        val objectsString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory))).get("item").toString()
        val sType = object : TypeToken<ArrayList<Item>>() { }.type
        return Gson().fromJson<ArrayList<Item>>(objectsString, sType)
    }

    @JvmStatic
    fun getJewels(context: Context): ArrayList<Jewel>? {
        var jewelObject :Any?
        var jewelString  =""
        try{
            jewelObject = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory))).get("jewels")
        }catch (e : Exception){
            jewelObject = null
        }
        jewelString = jewelObject?.let{jewelObject.toString()}?:run{""}
        val sType = object : TypeToken<ArrayList<Jewel>>() { }.type
        return Gson().fromJson<ArrayList<Jewel>>(jewelString, sType)?.let{Gson().fromJson<ArrayList<Jewel>>(jewelString, sType) }?:run{ArrayList<Jewel>()}
    }

    @JvmStatic
    fun getWeapons(context: Context): ArrayList<Weapon>? {
        var weaponObject :Any?
        var weaponString  =""
        try{
            weaponObject = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory))).get("weapons")
        }catch (e : Exception){
            weaponObject = null
        }
        weaponString = weaponObject?.let{weaponObject.toString()}?:run{""}
        val sType = object : TypeToken<ArrayList<Weapon>>() { }.type
        return Gson().fromJson<ArrayList<Weapon>>(weaponString, sType)?.let{Gson().fromJson<ArrayList<Weapon>>(weaponString, sType) }?:run{ArrayList<Weapon>()}
    }

    @JvmStatic
    fun getShields(context: Context): ArrayList<Shield>? {
        var shieldObject :Any?
        var shieldString  =""
        try{
            shieldObject = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory))).get("shields")
        }catch (e : Exception){
            shieldObject = null
        }
        shieldString = shieldObject?.let{shieldObject.toString()}?:run{""}
        val sType = object : TypeToken<ArrayList<Shield>>() { }.type
        return Gson().fromJson<ArrayList<Shield>>(shieldString, sType)?.let{Gson().fromJson<ArrayList<Shield>>(shieldString, sType) }?:run{ArrayList<Shield>()}
    }

    @JvmStatic
    fun getArmors(context: Context): ArrayList<Armor>? {
        var armorObject :Any?
        var armorString  =""
        try{
            armorObject = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_inventory))).get("armors")
        }catch (e : Exception){
            armorObject = null
        }
        armorString = armorObject?.let{armorObject.toString()}?:run{""}
        val sType = object : TypeToken<ArrayList<Armor>>() { }.type
        return Gson().fromJson<ArrayList<Armor>>(armorString, sType)?.let{Gson().fromJson<ArrayList<Armor>>(armorString, sType) }?:run{ArrayList<Armor>()}
    }

    @JvmStatic
    fun getStuff(context: Context) : ArrayList<Any>{
        var stuff = ArrayList<Any>()
        stuff.addAll(this!!.getWeapons(context)!!)
        stuff.addAll(this!!.getShields(context)!!)
        stuff.addAll(this!!.getArmors(context)!!)
        stuff.addAll(this!!.getJewels(context)!!)
        stuff.addAll(this!!.getItems(context)!!)
        return stuff
    }

    @JvmStatic
    fun getBleed(context: Context): Int {
        val bleedString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_fight))).get("bleed").toString()
        return Gson().fromJson<Int>(bleedString, Int::class.java)
    }

    @JvmStatic
    fun getFrost(context: Context): Boolean {
        val frostString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_fight))).get("frost").toString()
        return Gson().fromJson<Boolean>(frostString, Boolean::class.java)
    }

    @JvmStatic
    fun getDamages(context: Context): ArrayList<Int> {
        val frostString = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_fight))).get("damage").toString()
        val sType = object : TypeToken<ArrayList<Int>>() { }.type
        return Gson().fromJson<ArrayList<Int>>(frostString, sType)
    }

    @JvmStatic
    fun getPosture(context: Context): String? {
        var postureObject :Any?
        var postureString  =""
        try{
            postureObject = JSONObject(FileUtils.readJsonFile(context,context.getString(R.string.file_fight))).get("posture")
        }catch (e : Exception){
            postureObject = null
        }
        postureString = postureObject?.let{postureObject.toString()}?:run{""}
        return Gson().fromJson<String>(postureString, String::class.java)?.let{ Gson().fromJson<String>(postureString, String::class.java) }?:run{""}
    }

    /******** EDIT *******/

    @JvmStatic
    fun editCharacter(context: Context, character: Character) {
        val characterString = Gson().toJson(character)
        FileUtils.editJsonFile(context, context.getString(R.string.file_character), characterString)
    }

    @JvmStatic
    fun editEquipment(context: Context, equipment: Equipment) {
        val equipmentString = Gson().toJson(equipment)
        FileUtils.editJsonFile(context, context.getString(R.string.file_equipment), equipmentString)
    }

    @JvmStatic
    fun editSpells(context: Context, spells: List<Spell>) {
        val spellsString = Gson().toJson(spells)
        FileUtils.editJsonFile(context, context.getString(R.string.file_spells), spellsString)
        allSpells = getSpells(context)
    }

    @JvmStatic
    fun editInventory(context: Context, inventory : Inventory) {
        val inventoryString = Gson().toJson(inventory)
        FileUtils.editJsonFile(context, context.getString(R.string.file_inventory), inventoryString)
    }

    @JvmStatic
    fun editFight(context: Context, fight : Fight) {
        val fightString = Gson().toJson(fight)
        FileUtils.editJsonFile(context, context.getString(R.string.file_fight), fightString)
    }
}