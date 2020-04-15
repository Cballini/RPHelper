package com.rphelper.cecib.rphelper.dto

class Spell (var name:String, var damage:Int, var mana:Int, var effect:String, var use:String, var useValue:Int, var equip:Boolean){
    var use2 : String? = null
    var useValue2 : Int = 0

    constructor(name:String, damage:Int, mana:Int, effect:String, use:String, useValue:Int, equip:Boolean, use2 :String, useValue2 :Int)
            :this(name, damage, mana, effect, use, useValue, equip){
        this.use2 = use2
        this.useValue2 = useValue2
    }
}

class Spells(var spells :List<Spell>)