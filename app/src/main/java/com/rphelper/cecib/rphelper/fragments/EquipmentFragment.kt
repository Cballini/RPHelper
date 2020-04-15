package com.rphelper.cecib.rphelper.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.EquipmentComponent
import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status
import com.rphelper.cecib.rphelper.viewmodel.EquipmentViewModel
import kotlinx.android.synthetic.main.component_equipment.view.*


class EquipmentFragment : Fragment() {

    private lateinit var viewModel : EquipmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_equipment, container, false)

        viewModel = EquipmentViewModel(context!!)

        view.findViewById<TextView>(R.id.equipment_button_attack).setOnClickListener {  }
        view.findViewById<TextView>(R.id.equipment_button_block).setOnClickListener {  }
        view.findViewById<TextView>(R.id.equipment_button_esc).setOnClickListener {  }

        /******** Left hand **********/
        viewModel.leftHand.value!!?.let {
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentType.text = getString(R.string.left_hand)
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipment_name.text = viewModel.leftHand.value!!.name
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFirstPanelTitle.text = getString(R.string.damages_short)
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFirstPanelTxt.text = viewModel.leftHand.value!!.damage.toString()
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentSecondPanelTitle.text = getString(R.string.boost)
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentSecondPanelTxt.text = viewModel.leftHand.value!!.boost.toString()
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentThirdPanelTitle.text = if (null!=viewModel.leftHand.value!!.status && !viewModel.leftHand.value!!.status.equals(Status.NOTHING)) viewModel.leftHand.value!!.status.toString() else getString(R.string.status)
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentThirdPanelTxt.text = if (null!=viewModel.leftHand.value!!.statusValue && viewModel.leftHand.value!!.statusValue != 0F) viewModel.leftHand.value!!.statusValue.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFourthPanelTitle.text = getString(R.string.affinity_short)
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentFourthPanelTxt.text = if (null!=viewModel.leftHand.value!!.affinity && !viewModel.leftHand.value!!.affinity.equals(Elem.NOTHING)) viewModel.leftHand.value!!.affinity.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusForTxt.text = if (null!=viewModel.leftHand.value!!.bonusFor && !viewModel.leftHand.value!!.bonusFor.equals(Bonus.NOTHING)) viewModel.leftHand.value!!.bonusFor.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusDexTxt.text = if (null!=viewModel.leftHand.value!!.bonusDex && !viewModel.leftHand.value!!.bonusDex.equals(Bonus.NOTHING)) viewModel.leftHand.value!!.bonusDex.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusIntTxt.text = if (null!=viewModel.leftHand.value!!.bonusInt && !viewModel.leftHand.value!!.bonusInt.equals(Bonus.NOTHING)) viewModel.leftHand.value!!.bonusInt.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentBonusFoiTxt.text = if (null!=viewModel.leftHand.value!!.bonusFoi && !viewModel.leftHand.value!!.bonusFoi.equals(Bonus.NOTHING)) viewModel.leftHand.value!!.bonusFoi.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentLargePanelTxt.text = "215" //TODO calc
            view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentWeightTxt.text = viewModel.leftHand.value!!.weight.toString()
        }

        /******** Right hand **********/
        viewModel.rightHand.value!!?.let {
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentType.text = getString(R.string.right_hand)
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipment_name.text = viewModel.rightHand.value!!.name
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFirstPanelTitle.text = getString(R.string.damages_short)
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFirstPanelTxt.text = viewModel.rightHand.value!!.damage.toString()
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentSecondPanelTitle.text = getString(R.string.boost)
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentSecondPanelTxt.text = viewModel.rightHand.value!!.boost.toString()
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentThirdPanelTitle.text = if (null!=viewModel.rightHand.value!!.status && !viewModel.rightHand.value!!.status.equals(Status.NOTHING)) viewModel.rightHand.value!!.status.toString() else getString(R.string.status)
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentThirdPanelTxt.text = if (null!=viewModel.rightHand.value!!.statusValue && viewModel.rightHand.value!!.statusValue != 0F) viewModel.rightHand.value!!.statusValue.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFourthPanelTitle.text = getString(R.string.affinity_short)
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentFourthPanelTxt.text = if (null!=viewModel.rightHand.value!!.affinity && !viewModel.rightHand.value!!.affinity.equals(Elem.NOTHING)) viewModel.rightHand.value!!.affinity.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusForTxt.text = if (null!=viewModel.rightHand.value!!.bonusFor && !viewModel.rightHand.value!!.bonusFor.equals(Bonus.NOTHING)) viewModel.rightHand.value!!.bonusFor.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusDexTxt.text = if (null!=viewModel.rightHand.value!!.bonusDex && !viewModel.rightHand.value!!.bonusDex.equals(Bonus.NOTHING)) viewModel.rightHand.value!!.bonusDex.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusIntTxt.text = if (null!=viewModel.rightHand.value!!.bonusInt && !viewModel.rightHand.value!!.bonusInt.equals(Bonus.NOTHING)) viewModel.rightHand.value!!.bonusInt.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentBonusFoiTxt.text = if (null!=viewModel.rightHand.value!!.bonusFoi && !viewModel.rightHand.value!!.bonusFoi.equals(Bonus.NOTHING)) viewModel.rightHand.value!!.bonusFoi.toString() else "/"
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentLargePanelTxt.text = "215" //TODO calc
            view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentWeightTxt.text = viewModel.rightHand.value!!.weight.toString()
        }

        /******** Shield **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentType.text = getString(R.string.shield)
        viewModel.shield.value?.let {
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipment_name.text = viewModel.shield.value!!.name
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTitle.text = getString(R.string.block_short)
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTxt.text = viewModel.shield.value!!.block.toString()
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTitle.text = getString(R.string.res_short)
            if (null!=viewModel.shield.value!!.res && viewModel.shield.value!!.res.isNotEmpty()) {
                var stringRes = ""
                for (res in viewModel.shield.value!!.res){ stringRes += res.toString() +"\n" }
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTxt.text = stringRes
            }else{
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTxt.text = ""
            }
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondLine.visibility = View.GONE
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentBonusLayout.visibility = View.GONE
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentLargePanelLayout.visibility = View.GONE
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentWeightTxt.text = viewModel.shield.value!!.weight.toString()
        }?:run {
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipment_name.text = ""
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTitle.text = getString(R.string.block_short)
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTxt.text = ""
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTitle.text = getString(R.string.res_short)
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTxt.text = ""
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondLine.visibility = View.GONE
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentBonusLayout.visibility = View.GONE
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentLargePanelLayout.visibility = View.GONE
            view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentWeightTxt.text = "0"
        }

        /******** Hat **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentType.text = getString(R.string.hat)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipment_name.text = viewModel.hat.value!!.name
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFirstPanelTxt.text = viewModel.hat.value!!.def.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        if (null!=viewModel.hat.value!!.res && viewModel.hat.value!!.res.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.hat.value!!.res){ stringRes += res.toString() + " " }
            view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentSecondPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentSecondPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        if (null!=viewModel.hat.value!!.weak && viewModel.hat.value!!.weak.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.hat.value!!.weak){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentThirdPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentThirdPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        if (null!=viewModel.hat.value!!.immun && viewModel.hat.value!!.immun.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.hat.value!!.immun){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFourthPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentFourthPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentWeightTxt.text = viewModel.hat.value!!.weight.toString()

        /******** Chest **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentType.text = getString(R.string.chestplate)
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipment_name.text = viewModel.chest.value!!.name
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFirstPanelTxt.text = viewModel.chest.value!!.def.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        if (null!=viewModel.chest.value!!.res && viewModel.chest.value!!.res.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.chest.value!!.res){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentSecondPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentSecondPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        if (null!=viewModel.chest.value!!.weak && viewModel.chest.value!!.weak.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.chest.value!!.weak){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentThirdPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentThirdPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        if (null!=viewModel.chest.value!!.immun && viewModel.chest.value!!.immun.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.chest.value!!.immun){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFourthPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentFourthPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentWeightTxt.text = viewModel.chest.value!!.weight.toString()

        /******** Gloves **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentType.text = getString(R.string.gloves)
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipment_name.text = viewModel.gloves.value!!.name
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFirstPanelTxt.text = viewModel.gloves.value!!.def.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        if (null!=viewModel.gloves.value!!.res && viewModel.gloves.value!!.res.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.gloves.value!!.res){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentSecondPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentSecondPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        if (null!=viewModel.gloves.value!!.weak && viewModel.gloves.value!!.weak.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.gloves.value!!.weak){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentThirdPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentThirdPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        if (null!=viewModel.gloves.value!!.immun && viewModel.gloves.value!!.immun.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.gloves.value!!.immun){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFourthPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentFourthPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentWeightTxt.text = viewModel.gloves.value!!.weight.toString()

        /******** Greaves **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentType.text = getString(R.string.greaves)
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipment_name.text = viewModel.greaves.value!!.name
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFirstPanelTxt.text = viewModel.greaves.value!!.def.toString()
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        if (null!=viewModel.greaves.value!!.res && viewModel.greaves.value!!.res.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.greaves.value!!.res){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentSecondPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentSecondPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        if (null!=viewModel.greaves.value!!.weak && viewModel.greaves.value!!.weak.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.greaves.value!!.weak){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentThirdPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentThirdPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        if (null!=viewModel.greaves.value!!.immun && viewModel.greaves.value!!.immun.isNotEmpty()) {
            var stringRes = ""
            for (res in viewModel.greaves.value!!.immun){ stringRes += res.toString() +"\n" }
            view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFourthPanelTxt.text = stringRes
        }else{
            view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentFourthPanelTxt.text = "/"
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentBonusLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentLargePanelLayout.visibility = View.GONE
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentWeightTxt.text = viewModel.greaves.value!!.weight.toString()

        return view
    }


}
