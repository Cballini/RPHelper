package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Elem

class Armor(var name: String, var def: Float, var weight: Float) {
    var res = ArrayList<Elem>()
    var weak = ArrayList<Elem>()
    var immun = ArrayList<Elem>()
}