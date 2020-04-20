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
}