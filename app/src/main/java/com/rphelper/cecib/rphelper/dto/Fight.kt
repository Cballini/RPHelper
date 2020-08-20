package com.rphelper.cecib.rphelper.dto

class Fight (var damage: ArrayList<Int>, var heal:ArrayList<Int>, var round:ArrayList<Round>, var frost :Boolean, var bleed :Int, var posture :String) {
    constructor():this(ArrayList<Int>(), ArrayList<Int>(), ArrayList<Round>(), false, 0, "")
}