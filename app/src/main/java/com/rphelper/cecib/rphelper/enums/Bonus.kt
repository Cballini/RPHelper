package com.rphelper.cecib.rphelper.enums

enum class Bonus (val value: Float){
    S(3F),
    A(2.5F),
    B(2F),
    C(1.5F),
    D(1F),
    E(0.5F),
    NOTHING(0F);

    companion object{
        @JvmStatic
        fun getListStringBonus(): ArrayList<String> {
            var list = ArrayList<String>()
            list.add("/")
            list.add(Bonus.S.name)
            list.add(Bonus.A.name)
            list.add(Bonus.B.name)
            list.add(Bonus.C.name)
            list.add(Bonus.D.name)
            list.add(Bonus.E.name)
            return list
        }

        @JvmStatic
        fun compareBetterBonus(bonusIni : Bonus, bonusNew: Bonus) = bonusNew.value-bonusIni.value>=0
    }
}
