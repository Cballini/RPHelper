package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.ArmorPart
import com.rphelper.cecib.rphelper.enums.Elem

class Armor(var name: String, var part:ArmorPart, var def: Float, var weight: Float, var equip:Boolean) {
    var res: Elem = Elem.NOTHING
    var weak: Elem = Elem.NOTHING
    var immun: Elem = Elem.NOTHING
}