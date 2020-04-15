package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status

class Weapon (var name:String, var damage:Float, var weight:Float){
    var bonusFor :Bonus = Bonus.NOTHING
    var bonusDex :Bonus = Bonus.NOTHING
    var bonusInt :Bonus = Bonus.NOTHING
    var bonusFoi :Bonus = Bonus.NOTHING
    var affinity : Elem = Elem.NOTHING
    var boost : Int = 0
    var status : Status = Status.NOTHING
    var statusValue : Float = 0F
    var hand = false
}