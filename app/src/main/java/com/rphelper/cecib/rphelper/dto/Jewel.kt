package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status

class Jewel(name:String, weight:Float) : Stuff(name, weight) {
    var maxLifeModifier : Int = 0
    var maxConstModifier : Int = 0
    var maxManaModifier : Int = 0
    var maxWeightModifier : Int = 0
    var damageModifier : Int = 0
    var defModifier : Int = 0
    var vitModifier : Int = 0
    var vigModifier : Int = 0
    var forModifier : Int = 0
    var dexModifier : Int = 0
    var endModifier : Int = 0
    var memModifier : Int = 0
    var intModifier : Int = 0
    var foiModifier : Int = 0
    var weakModifier = ArrayList<Elem>()
    var resModifier = ArrayList<Elem>()
    var immunModifier = ArrayList<Status>()
    var desc : String = ""
    var equip : Boolean = false

    constructor():this("", 0F)

    fun getDescription() : String{
        var desc  = ""
        if (this.maxLifeModifier !=0) desc += ", max vie :" + this.maxLifeModifier
        if (this.maxConstModifier !=0) desc += ", max const :" + this.maxConstModifier
        if (this.maxManaModifier !=0) desc += ", max mana :" + this.maxManaModifier
        if (this.maxWeightModifier !=0) desc += ", max poids :" + this.maxWeightModifier
        if (this.damageModifier !=0) desc += ", dégats :" + this.damageModifier
        if (this.defModifier !=0) desc += ", def :" + this.defModifier
        if (this.vitModifier !=0) desc += ", vit :" + this.vitModifier
        if (this.vigModifier !=0) desc += ", vig :" + this.vigModifier
        if (this.forModifier !=0) desc += ", for :" + this.forModifier
        if (this.dexModifier !=0) desc += ", dex :" + this.dexModifier
        if (this.endModifier !=0) desc += ", end :" + this.endModifier
        if (this.memModifier !=0) desc += ", mem :" + this.memModifier
        if (this.intModifier !=0) desc += ", int :" + this.intModifier
        if (this.foiModifier !=0) desc += ", foi :" + this.foiModifier
        if (this.weakModifier.isNotEmpty()){
            desc += ", faiblesse :"
            for (w in weakModifier) desc += " " + w.name
        }
        if (this.resModifier.isNotEmpty()){
            desc += ", res :"
            for (r in resModifier) desc += ", " + r.name
        }
        if (this.immunModifier.isNotEmpty()){
            desc += ", immun :"
            for (i in immunModifier) desc += ", " + i.name
        }
        return desc
    }
}