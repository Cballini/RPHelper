package com.rphelper.cecib.rphelper.dto

import com.rphelper.cecib.rphelper.enums.Elem

class Shield (var name:String, var weight:Float, var equip:Boolean){
    var block = 1
    var res : Elem = Elem.NOTHING
}