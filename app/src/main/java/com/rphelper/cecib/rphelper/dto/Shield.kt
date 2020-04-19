package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Elem

class Shield (var name:String, var weight:Float){
    var block: Float = 1F
    var res = ArrayList<Elem>()

    fun reinit(){
        this.name = ""
        this.weight = 0F
        this.block = 1F
        this.res = ArrayList<Elem>()
    }
}