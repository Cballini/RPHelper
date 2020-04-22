package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status

class Armor(var name: String, var def: Float, var weight: Float) {
    var res = ArrayList<Elem>()
    var weak = ArrayList<Elem>()
    var immun = ArrayList<Status>()

    fun reinit(){
        this.name = ""
        this.def = 0F
        this.weight = 0F
        this.res = ArrayList<Elem>()
        this.weak = ArrayList<Elem>()
        this.immun = ArrayList<Status>()
    }

    fun getDescription() :String{
        var desc = ""
        desc = "defense: " + def.toString()
        if(res.isNotEmpty()){
            desc += ", resistance(s): "
            for(r in res){
                desc += r.name + " "
            }
        }
        if(immun.isNotEmpty()){
            desc += ", immunité(s): "
            for(i in immun){
                desc += i.name + " "
            }
        }
        if(weak.isNotEmpty()){
            desc += ", faiblesse(s): "
            for(w in weak){
                desc += w.name + " "
            }
        }
        return desc
    }
}