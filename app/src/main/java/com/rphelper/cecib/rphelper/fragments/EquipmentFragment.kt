package com.rphelper.cecib.rphelper.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.EquipmentComponent
import com.rphelper.cecib.rphelper.dto.Armor
import com.rphelper.cecib.rphelper.dto.Weapon
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
        initWeaponView(view, R.id.equipment_left_hand, getString(R.string.left_hand), viewModel.leftHand.value)

        /******** Right hand **********/
        initWeaponView(view, R.id.equipment_right_hand, getString(R.string.right_hand), viewModel.rightHand.value)

        /******** Shield **********/
        initShieldView(view)

        /******** Hat **********/
        initArmorView(view, R.id.equipment_hat, getString(R.string.hat), viewModel.hat.value)

        /******** Chest **********/
        initArmorView(view, R.id.equipment_chest, getString(R.string.chestplate), viewModel.chest.value)

        /******** Gloves **********/
        initArmorView(view, R.id.equipment_gloves, getString(R.string.gloves), viewModel.gloves.value)

        /******** Greaves **********/
        initArmorView(view, R.id.equipment_greaves, getString(R.string.greaves), viewModel.greaves.value)

        return view
    }

    fun initWeaponView(view :View, id :Int, type :String, weapon: Weapon?){
        weapon?.let {
            view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
            view!!.findViewById<EquipmentComponent>(id).equipment_name.text = weapon.name
            view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.damages_short)
            view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = weapon.damage.toString()
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTitle.text = getString(R.string.boost)
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = weapon.boost.toString()
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = if (null!=weapon.status && !weapon.status.equals(Status.NOTHING)) weapon.status.toString() else getString(R.string.status)
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = if (null!=weapon.statusValue && weapon.statusValue != 0F) weapon.statusValue.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.affinity_short)
            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTxt.text = if (null!=weapon.affinity && !weapon.affinity.equals(Elem.NOTHING)) weapon.affinity.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusForTxt.text = if (null!=weapon.bonusFor && !weapon.bonusFor.equals(Bonus.NOTHING)) weapon.bonusFor.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusDexTxt.text = if (null!=weapon.bonusDex && !weapon.bonusDex.equals(Bonus.NOTHING)) weapon.bonusDex.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusIntTxt.text = if (null!=weapon.bonusInt && !weapon.bonusInt.equals(Bonus.NOTHING)) weapon.bonusInt.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusFoiTxt.text = if (null!=weapon.bonusFoi && !weapon.bonusFoi.equals(Bonus.NOTHING)) weapon.bonusFoi.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentLargePanelTxt.text = "215" //TODO calc
            view!!.findViewById<EquipmentComponent>(id).equipmentWeightTxt.text = weapon.weight.toString()
        }
    }

    fun initShieldView(view:View){
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
    }

    fun initArmorView(view :View, id :Int, type :String, armor: Armor?){
        view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
        view!!.findViewById<EquipmentComponent>(id).equipment_name.text = armor!!.name
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.def_short)
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = armor!!.def.toString()
        view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTitle.text = getString(R.string.res_short)
        if (null!=armor!!.res && armor!!.res.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.res){ stringRes += res.toString() + " " }
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = stringRes
        }else{
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = getString(R.string.fai_short)
        if (null!=armor!!.weak && armor!!.weak.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.weak){ stringRes += res.toString() +"\n" }
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = stringRes
        }else{
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.immun_short)
        if (null!=armor!!.immun && armor!!.immun.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.immun){ stringRes += res.toString() +"\n" }
            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTxt.text = stringRes
        }else{
            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentBonusLayout.visibility = View.GONE
        view!!.findViewById<EquipmentComponent>(id).equipmentLargePanelLayout.visibility = View.GONE
        view!!.findViewById<EquipmentComponent>(id).equipmentWeightTxt.text = armor!!.weight.toString()
    }

}
