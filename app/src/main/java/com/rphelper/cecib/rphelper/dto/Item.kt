package com.rphelper.cecib.rphelper.dto

class Item (var name:String, var quantity:Int, var effect:String, var equip:Boolean){
    var weight : Float = 0F
    /*var bonusStat
    var bonus*/

    constructor(name:String, quantity:Int, effect:String, equip:Boolean, weight: Float) : this(name, quantity, effect, equip) {
        this.weight = weight
    }
}