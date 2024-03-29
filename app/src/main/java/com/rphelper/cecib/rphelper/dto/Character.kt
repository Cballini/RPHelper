package com.rphelper.cecib.rphelper.dto

data class Character(var name :String,
                     var race :String,
                     var origin :String,
                     var religion :String,
                     var level:Int,
                     var life :Indic,
                     var const :Indic,
                     var mana :Indic,
                     var vitality :Int,
                     var vigor :Int,
                     var strength :Int,
                     var dexterity :Int,
                     var endurance :Int,
                     var memory :Int,
                     var intelligence :Int,
                     var faith :Int,
                     var don:String){
    var characterDon: MutableList<String> = ArrayList()
    var deadlyWound : Int = 0

    constructor():this("", "", "", "", 0, Indic(0F,0),
            Indic(0F,0),Indic(0F,0), 0,0,0,0,0,0,0,0, "" )
}