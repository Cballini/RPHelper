package com.rphelper.cecib.rphelper.dto

data class Spell (var name:String, var damage:Int, var mana:Int, var effect:String, var use:String, var useValue:Int, var equip:Boolean){
    var use2 : String = ""
    var useValue2 : Int = 0

    constructor(name:String, damage:Int, mana:Int, effect:String, use:String, useValue:Int, equip:Boolean, use2 :String, useValue2 :Int)
            :this(name, damage, mana, effect, use, useValue, equip){
        this.use2 = use2
        this.useValue2 = useValue2
    }

    constructor():this("", 0, 0, "", "", 0, true)

    constructor(spell: Spell):this(spell.name, spell.damage, spell.mana, spell.effect, spell.use, spell.useValue, spell.equip, spell.use2, spell.useValue2)
}

