package com.rphelper.cecib.rphelper.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.EquipmentComponent
import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status
import kotlinx.android.synthetic.main.component_equipment.view.*


class EquipmentFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_equipment, container, false)

        /******** Left hand **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentType.text = getString(R.string.left_hand)
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipment_name.text = "Sceptre du roi"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFirstPanelTitle.text = getString(R.string.damages)
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFirstPanelTxt.text = "84"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentSecondPanelTitle.text = getString(R.string.boost)
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentSecondPanelTxt.text = "165"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentSecondLine.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentThirdPanelTitle.text = getString(R.string.status) //mettre direct status si y'a
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentThirdPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFourthPanelTitle.text = getString(R.string.affinity_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFourthPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusLayout.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusForTxt.text = Bonus.E.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusDexTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusIntTxt.text = Bonus.A.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusFoiTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentLargePanelLayout.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentLargePanelTxt.text = "215"
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentWeightTxt.text = "10"

        /******** Right hand **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentType.text = getString(R.string.right_hand)
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipment_name.text = "Rapière ++"
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFirstPanelTitle.text = getString(R.string.damages)
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFirstPanelTxt.text = "84"
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentSecondPanelTitle.text = getString(R.string.boost)
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentSecondPanelTxt.text = "165"
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentSecondLine.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentThirdPanelTitle.text = Status.BLEED.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentThirdPanelTxt.text = "0.4"
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFourthPanelTitle.text = getString(R.string.affinity_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFourthPanelTxt.text = Elem.FIRE.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusLayout.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusForTxt.text = Bonus.E.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusDexTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusIntTxt.text = Bonus.A.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusFoiTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentLargePanelLayout.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentLargePanelTxt.text = "215"
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentWeightTxt.text = "10"

        /******** Shield **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentType.text = getString(R.string.shield)
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipment_name.text = "Targe"
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTitle.text = getString(R.string.block)
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTxt.text = "0"
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTitle.text = getString(R.string.res)
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTxt.text = Elem.DARKNESS.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondLine.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentWeightTxt.text = "10"

        /******** Hat **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentType.text = getString(R.string.hat)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipment_name.text = "Sorcier"
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFirstPanelTxt.text = "1"
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentSecondPanelTxt.text = Elem.ALL.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentSecondLine.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentThirdPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFourthPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentWeightTxt.text = "1.4"

        /******** Chest **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentType.text = getString(R.string.chestplate)
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipment_name.text = "Sorcier"
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFirstPanelTxt.text = "1"
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentSecondPanelTxt.text = Elem.ALL.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentSecondLine.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentThirdPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFourthPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentWeightTxt.text = "1.4"

        /******** Gloves **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentType.text = getString(R.string.gloves)
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipment_name.text = "Sorcier"
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFirstPanelTxt.text = "1"
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentSecondPanelTxt.text = Elem.ALL.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentSecondLine.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentThirdPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFourthPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentWeightTxt.text = "1.4"

        /******** Greaves **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentType.text = getString(R.string.greaves)
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipment_name.text = "Sorcier"
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFirstPanelTxt.text = "1"
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentSecondPanelTxt.text = Elem.ALL.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentSecondLine.visibility = View.VISIBLE
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentThirdPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFourthPanelTxt.text = "/"
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentWeightTxt.text = "1.4"

        return view
    }


}
