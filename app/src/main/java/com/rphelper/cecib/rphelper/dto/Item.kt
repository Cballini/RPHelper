package com.rphelper.cecib.rphelper.dto

class Item ( name:String, var quantity:Int, var effect:String, weight: Float) : Stuff(name, weight){
    var equip : Boolean = false
    /*var bonusStat
    var bonus*/

    constructor():this("", 0, "", 0F)
}