package com.rphelper.cecib.rphelper.dto

data class Inventory (val money :Int, val weapons:ArrayList<Weapon>, val shields:ArrayList<Shield>, val armors:ArrayList<Armor>, val jewels: ArrayList<Jewel>,   val item :ArrayList<Item>){

    constructor():this(0,ArrayList<Weapon>(), ArrayList<Shield>(), ArrayList<Armor>(), ArrayList<Jewel>(), ArrayList<Item>())
}