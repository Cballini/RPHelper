package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Elem

class Shield (name:String, weight:Float) : Stuff(name, weight){
    var block: Float = 1F
    var res = ArrayList<Elem>()
    var equip = true
    var inHand = false

    constructor():this("", 0F){
        equip = false
        inHand = false
    }

    fun reinit(){
        this.name = ""
        this.weight = 0F
        this.block = 1F
        this.res = ArrayList<Elem>()
    }

    fun getDescription() :String{
        var desc = ""
        if (block!=0F) desc = "blocage: " + block.toString()
        if(res.isNotEmpty()) {
            desc += ", resistance(s): "
            for (r in res){
                desc += r.name + " "
            }
        }
        return desc
    }
}