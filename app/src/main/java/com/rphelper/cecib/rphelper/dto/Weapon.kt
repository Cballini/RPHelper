package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status

class Weapon (var name:String, var damage:Int, var weight:Float){
    var bonusFor :Bonus = Bonus.NOTHING
    var bonusDex :Bonus = Bonus.NOTHING
    var bonusInt :Bonus = Bonus.NOTHING
    var bonusFoi :Bonus = Bonus.NOTHING
    var affinity : Elem = Elem.NOTHING
    var boost : Int = 0
    var status : Status = Status.NOTHING
    var statusValue : Float = 0F
    var rapidFire = false

    constructor():this("", 0, 0F)

    fun reinit(){
        this.name = ""
        this.damage = 0
        this.weight = 0F
        this.bonusFor = Bonus.NOTHING
        this.bonusDex = Bonus.NOTHING
        this.bonusInt = Bonus.NOTHING
        this.bonusFoi = Bonus.NOTHING
        this.affinity = Elem.NOTHING
        this.boost = 0
        this.status = Status.NOTHING
        this.statusValue = 0F
    }

    fun getDescription():String{
        var desc = ""
        if (damage!=0) desc = "dgt = " + damage.toString()
        if (boost!=0) desc = "boost = " + boost.toString()
        if(rapidFire) desc += ", rapid fire"
        if (!status.equals(Status.NOTHING)) desc += ", " + status.name + ": " + statusValue.toString()
        if (!affinity.equals(Elem.NOTHING)) desc += ", affinité :" + affinity.name
        if (!bonusFor.equals(Bonus.NOTHING)) desc += ", bonus FOR : " + bonusFor
        if (!bonusDex.equals(Bonus.NOTHING)) desc += ", bonus DEX : " + bonusDex
        if (!bonusInt.equals(Bonus.NOTHING)) desc += ", bonus INT : " + bonusInt
        if (!bonusFoi.equals(Bonus.NOTHING)) desc += ", bonus FOI : " + bonusFoi
        return desc
    }
}