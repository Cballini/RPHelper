package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status

class Weapon (var name:String, var damage:Float, var weight:Float, var equip:Boolean){
    var bonusFOR :Bonus = Bonus.NOTHING
    var bonusDEX :Bonus = Bonus.NOTHING
    var bonusINT :Bonus = Bonus.NOTHING
    var bonusFOI :Bonus = Bonus.NOTHING
    var affinity : Elem = Elem.NOTHING
    var boostMagic = 0
    var status : Status = Status.NOTHING
    var hand = false
}