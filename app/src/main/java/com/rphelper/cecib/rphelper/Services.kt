package com.rphelper.cecib.rphelper

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.utils.FileUtils
import org.json.JSONObject

object Services {

    var allSpells = ArrayList<Spell>()

    @JvmStatic
    fun getCharacter(context: Context): Character {
        return Gson().fromJson<Character>(FileUtils.readJsonFile(context,context.getString(R.string.file_character)), Character::class.java)
    }

    @JvmStatic
    fun getWeapon(context: Context, type : String): Weapon {
        val weaponString = JSONObject(FileUtils.readJsonFile(context,"equipment.json")).get(type).toString()
        return Gson().fromJson<Weapon>(weaponString, Weapon::class.java)
    }

    @JvmStatic
    fun getShield(context: Context): Shield {
        val shieldString = JSONObject(FileUtils.readJsonFile(context,"equipment.json")).get("shield").toString()
        return Gson().fromJson<Shield>(shieldString, Shield::class.java)
    }

    @JvmStatic
    fun getArmor(context: Context, type : String): Armor {
        val armorString = JSONObject(FileUtils.readJsonFile(context,"equipment.json")).get(type).toString()
        return Gson().fromJson<Armor>(armorString, Armor::class.java)
    }

    @JvmStatic
    fun getSpells(context: Context): ArrayList<Spell> {
        val sType = object : TypeToken<ArrayList<Spell>>() { }.type
        return Gson().fromJson<ArrayList<Spell>>(FileUtils.readJsonFile(context, "spells.json"), sType)
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
    fun getListOfNotEquipSpells(context: Context): ArrayList<Spell> {
        var notEquipSpells = ArrayList<Spell>()
        var spells = ArrayList<Spell>()
        if (allSpells.isNotEmpty()) spells = allSpells else {allSpells = getSpells(context); spells = allSpells}
        spells.let {
            for (spell in spells!!) {
                if (!spell.equip) notEquipSpells.add(spell)
            }
        }
        return notEquipSpells
    }

    @JvmStatic
    fun getMoney(context: Context): Int {
        val moneyString = JSONObject(FileUtils.readJsonFile(context,"inventory.json")).get("money").toString()
        return Gson().fromJson<Int>(moneyString, Int::class.java)
    }

    @JvmStatic
    fun getItems(context: Context): List<Item> {
        val objectsString = JSONObject(FileUtils.readJsonFile(context,"inventory.json")).get("item").toString()
        val sType = object : TypeToken<List<Item>>() { }.type
        return Gson().fromJson<List<Item>>(objectsString, sType)
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
}