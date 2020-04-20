package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.rphelper.cecib.rphelper.Services

class FightViewModel(val context: Context) :ViewModel(){

    fun getDef():Float{
        val character = Services.getCharacter(context)
        val equipment = Services.getEquipment(context)
        var def = 50F
        def += equipment.hat.def + equipment.chest.def + equipment.gloves.def + equipment.greaves.def
        def += (character.vitality/2 + character.memory/2 + character.endurance/2 + character.vigor
                + character.strength/2 + character.dexterity/2 + character.intelligence/2 + character.faith/2)
        return def
    }

    fun getBlock():Float{
        val equipment = Services.getEquipment(context)
        return equipment.shield.block
    }

    fun submit(damages:Float){
        if (damages>0) {
            val char = Services.getCharacter(context)
            char.life.value -= damages.toInt()
            Services.editCharacter(context, char)
        }
    }
}