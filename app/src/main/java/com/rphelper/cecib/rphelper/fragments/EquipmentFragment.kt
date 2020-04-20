package com.rphelper.cecib.rphelper.fragments


import android.app.Dialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.component.EquipmentComponent
import com.rphelper.cecib.rphelper.dto.Armor
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status
import com.rphelper.cecib.rphelper.utils.KeyboardUtils
import com.rphelper.cecib.rphelper.viewmodel.EquipmentViewModel
import kotlinx.android.synthetic.main.component_category_horizontal.view.*
import kotlinx.android.synthetic.main.component_equipment.view.*


class EquipmentFragment : Fragment() {

    private lateinit var viewModel : EquipmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_equipment, container, false)

        viewModel = EquipmentViewModel(context!!)

        initViewDisabled(view)

        view.findViewById<Button>(R.id.equipment_button_attack).setOnClickListener {
            viewModel.attackOrBlock()
        }
        view.findViewById<Button>(R.id.equipment_button_block).setOnClickListener { viewModel.attackOrBlock()  }
        view.findViewById<Button>(R.id.equipment_button_dodge).setOnClickListener { viewModel.dodge()  }

        /********** Stats **********/
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_damages).cat_title.text = getString(R.string.damages)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_damages).catTxt.setEnabled(false)
        viewModel.damages.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_damages).catTxt.setText(it.toString())
        })
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_defense).cat_title.text = getString(R.string.def)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_defense).catTxt.setEnabled(false)
        viewModel.defense.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_defense).catTxt.setText(it.toString())
        })
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_res).cat_title.text = getString(R.string.res)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_res).catTxt.setEnabled(false)
        viewModel.res.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_res).catTxt.setText(it.toString())
        })
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_immun).cat_title.text = getString(R.string.immun)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_immun).catTxt.setEnabled(false)
        viewModel.immun.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_immun).catTxt.setText(it.toString())
        })
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_weak).cat_title.text = getString(R.string.fai)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_weak).catTxt.setEnabled(false)
        viewModel.weak.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_weak).catTxt.setText(it.toString())
        })

        /******** Left hand **********/
        viewModel.leftHand.observe(viewLifecycleOwner, Observer {
            initWeaponView(view, R.id.equipment_left_hand, getString(R.string.left_hand), it) })

        /******** Right hand **********/
        viewModel.rightHand.observe(viewLifecycleOwner, Observer {
            initWeaponView(view, R.id.equipment_right_hand, getString(R.string.right_hand), it)
        })

        /******** Catalyst **********/
        viewModel.catalyst.observe(viewLifecycleOwner, Observer {
            initWeaponView(view, R.id.equipment_catalyst, getString(R.string.catalyst), it)
        })

        /******** Shield **********/
        initShieldView(view)

        /******** Hat **********/
        viewModel.hat.observe(viewLifecycleOwner, Observer {
            initArmorView(view, R.id.equipment_hat, getString(R.string.hat), it)
        })

        /******** Chest **********/
        viewModel.chest.observe(viewLifecycleOwner, Observer {
            initArmorView(view, R.id.equipment_chest, getString(R.string.chestplate), it)
        })

        /******** Gloves **********/
        viewModel.gloves.observe(viewLifecycleOwner, Observer {
        initArmorView(view, R.id.equipment_gloves, getString(R.string.gloves), it)})

        /******** Greaves **********/
        viewModel.greaves.observe(viewLifecycleOwner, Observer {
        initArmorView(view, R.id.equipment_greaves, getString(R.string.greaves), it)})


        /*********** EDIT **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.left_hand), viewModel.leftHand.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentTypeLayout.setOnClickListener {
        editWeapon(getString(R.string.right_hand), viewModel.rightHand.value!!)}
        view.findViewById<EquipmentComponent>(R.id.equipment_catalyst).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.catalyst), viewModel.catalyst.value!!)
        }
        editShield(view)
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.hat), viewModel.hat.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentTypeLayout.setOnClickListener {
        editArmor(getString(R.string.chestplate), viewModel.chest.value!!)}
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentTypeLayout.setOnClickListener {
        editArmor(getString(R.string.gloves), viewModel.gloves.value!!)}
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentTypeLayout.setOnClickListener {
        editArmor(getString(R.string.greaves), viewModel.greaves.value!!)}

        return view
    }

    fun initWeaponView(view :View, id :Int, type :String, weapon: Weapon?){
        weapon?.let {
            view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
            view!!.findViewById<EquipmentComponent>(id).equipment_name.text = weapon.name
            if(type.equals(context!!.getString(R.string.catalyst))) {
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanel.visibility = View.GONE
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanel.visibility = View.VISIBLE
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTitle.text = getString(R.string.boost)
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = weapon.boost.toString()
                view.findViewById<EquipmentComponent>(id).equipmentSecondLine.visibility = View.GONE
                view.findViewById<EquipmentComponent>(id).equipmentLargePanelLayout.visibility = View.GONE
            }else{
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanel.visibility = View.VISIBLE
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.damages)
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = weapon.damage.toString()
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanel.visibility = View.GONE
                view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = if (null!=weapon.status && !weapon.status.equals(Status.NOTHING)) weapon.status.toString() else getString(R.string.status)
                view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = if (null!=weapon.statusValue && weapon.statusValue != 0F) weapon.statusValue.toString() else "/"
                view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.affinity)
                view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTxt.text = if (null!=weapon.affinity && !weapon.affinity.equals(Elem.NOTHING)) weapon.affinity.toString() else "/"
                view.findViewById<EquipmentComponent>(id).equipmentSecondLine.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentLargePanelLayout.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentLargePanelTxt.text = viewModel.getTotalDamage(weapon).toString()
            }
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusForTxt.text = if (null!=weapon.bonusFor && !weapon.bonusFor.equals(Bonus.NOTHING)) weapon.bonusFor.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusDexTxt.text = if (null!=weapon.bonusDex && !weapon.bonusDex.equals(Bonus.NOTHING)) weapon.bonusDex.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusIntTxt.text = if (null!=weapon.bonusInt && !weapon.bonusInt.equals(Bonus.NOTHING)) weapon.bonusInt.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusFoiTxt.text = if (null!=weapon.bonusFoi && !weapon.bonusFoi.equals(Bonus.NOTHING)) weapon.bonusFoi.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentWeightTxt.text = weapon.weight.toString()
        }
    }

    fun initShieldView(view:View){
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentType.text = getString(R.string.shield)
        viewModel.shield.observe(viewLifecycleOwner, Observer {
            it?.let {
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipment_name.text = it!!.name
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTitle.text = getString(R.string.block)
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTxt.text = it!!.block.toString()
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTitle.text = getString(R.string.res)
                if (null != it!!.res && it!!.res.isNotEmpty()) {
                    var stringRes = ""
                    for (res in it!!.res) {
                        stringRes += res.toString() + "\n"
                    }
                    view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTxt.text = stringRes
                } else {
                    view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTxt.text = ""
                }
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondLine.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentBonusLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentLargePanelLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentWeightTxt.text = it!!.weight.toString()
            } ?: run {
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipment_name.text = ""
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTitle.text = getString(R.string.block)
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTxt.text = ""
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTitle.text = getString(R.string.res)
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTxt.text = ""
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondLine.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentBonusLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentLargePanelLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentWeightTxt.text = "0"
            }
        })
    }

    fun initArmorView(view :View, id :Int, type :String, armor: Armor?){
        view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
        view!!.findViewById<EquipmentComponent>(id).equipment_name.text = armor!!.name
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.def)
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = armor!!.def.toString()
        view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTitle.text = getString(R.string.res)
        if (null!=armor!!.res && armor!!.res.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.res){ stringRes += res.toString() + " " }
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = stringRes
        }else{
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = getString(R.string.fai)
        if (null!=armor!!.weak && armor!!.weak.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.weak){ stringRes += res.toString() +"\n" }
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = stringRes
        }else{
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.immun)
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

    fun editWeapon( type: String, weapon: Weapon){

            val dialog = Dialog(activity)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setContentView(R.layout.popup_edit_weapon)
            dialog.findViewById<TextView>(R.id.weapon_type).text = type
            dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setAdapter(ArrayAdapter<String>(context
                    , android.R.layout.simple_list_item_1, getListStringBonus()));
            dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setAdapter(ArrayAdapter<String>(context
                    , android.R.layout.simple_list_item_1, getListStringBonus()));
            dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setAdapter(ArrayAdapter<String>(context
                    , android.R.layout.simple_list_item_1, getListStringBonus()));
            dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).setAdapter(ArrayAdapter<String>(context
                    , android.R.layout.simple_list_item_1, getListStringBonus()));
            fillWeaponEdit(dialog, weapon)
            dialog.findViewById<EditText>(R.id.weapon_name_txt).setSelection(dialog.findViewById<EditText>(R.id.weapon_name_txt).text.length)
            dialog.findViewById<ImageView>(R.id.weapon_cancel_button).setOnClickListener { dialog.dismiss() }
            //TODO desequiper
            dialog.findViewById<TextView>(R.id.weapon_delete_button).setOnClickListener {
                weapon.reinit()
                viewModel.editEquipment()
                dialog.dismiss()
            }
            dialog.findViewById<TextView>(R.id.weapon_save_button).setOnClickListener {
                weapon.name = dialog.findViewById<EditText>(R.id.weapon_name_txt).text.toString()
                if (dialog.findViewById<EditText>(R.id.weapon_damage_txt).text.toString().isNotEmpty()){
                    weapon.damage = dialog.findViewById<EditText>(R.id.weapon_damage_txt).text.toString().toInt()
                }else{
                    weapon.damage = 0
                }
                if (dialog.findViewById<EditText>(R.id.weapon_boost_txt).text.toString().isNotEmpty()){
                    weapon.boost = dialog.findViewById<EditText>(R.id.weapon_boost_txt).text.toString().toInt()
                }else{
                    weapon.boost = 0
                }
                if (dialog.findViewById<EditText>(R.id.weapon_status_proc_txt).text.toString().isNotEmpty()){
                    weapon.statusValue = dialog.findViewById<EditText>(R.id.weapon_status_proc_txt).text.toString().toFloat()
                }else{
                    weapon.statusValue = 0F
                }
                weapon.status = when(true){
                    dialog.findViewById<CheckBox>(R.id.weapon_status_bleed).isChecked -> Status.BLEED
                    dialog.findViewById<CheckBox>(R.id.weapon_status_poison).isChecked -> Status.POISON
                    dialog.findViewById<CheckBox>(R.id.weapon_status_frost).isChecked -> Status.FROST
                    else -> Status.NOTHING
                }
                weapon.affinity = when(true){
                    dialog.findViewById<CheckBox>(R.id.weapon_affinity_fire).isChecked -> Elem.FIRE
                    dialog.findViewById<CheckBox>(R.id.weapon_affinity_dark).isChecked -> Elem.DARKNESS
                    dialog.findViewById<CheckBox>(R.id.weapon_affinity_light).isChecked -> Elem.LIGHTNING
                    dialog.findViewById<CheckBox>(R.id.weapon_affinity_magic).isChecked -> Elem.MAGIC
                    else -> Elem.NOTHING
                }
                weapon.bonusFor = when(true){
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                    else -> Bonus.NOTHING
                }
                weapon.bonusDex = when(true){
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                    else -> Bonus.NOTHING
                }
                weapon.bonusInt = when(true){
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                    else -> Bonus.NOTHING
                }
                weapon.bonusFoi = when(true){
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                    dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                    else -> Bonus.NOTHING
                }

                if (dialog.findViewById<EditText>(R.id.weapon_weight_txt).text.toString().isNotEmpty()){
                    weapon.weight = dialog.findViewById<EditText>(R.id.weapon_weight_txt).text.toString().toFloat()
                }else{
                    weapon.weight = 0F
                }
                viewModel.editEquipment()
                dialog.dismiss()
            }

            dialog .show()
    }

    fun fillWeaponEdit(dialog: Dialog, weapon: Weapon?){
        if(!weapon!!.name.isNullOrEmpty()){
            dialog.findViewById<EditText>(R.id.weapon_name_txt).setText(weapon.name)
            dialog.findViewById<EditText>(R.id.weapon_damage_txt).setText(weapon.damage.toString())
            dialog.findViewById<EditText>(R.id.weapon_boost_txt).setText(weapon.boost.toString())
            dialog.findViewById<EditText>(R.id.weapon_status_proc_txt).setText(weapon.statusValue.toString())
            when(weapon.status){
                Status.BLEED -> dialog.findViewById<CheckBox>(R.id.weapon_status_bleed).isChecked = true
                Status.POISON -> dialog.findViewById<CheckBox>(R.id.weapon_status_poison).isChecked = true
                Status.FROST -> dialog.findViewById<CheckBox>(R.id.weapon_status_frost).isChecked = true
            }
            when(weapon.affinity){
                Elem.MAGIC -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_magic).isChecked = true
                Elem.FIRE -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_fire).isChecked = true
                Elem.DARKNESS -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_dark).isChecked = true
                Elem.LIGHTNING -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_light).isChecked = true
            }
            when(weapon.bonusFor){
                Bonus.S -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(1)
                Bonus.A -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(2)
                Bonus.B -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(3)
                Bonus.C -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(4)
                Bonus.D -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(5)
                Bonus.E -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(6)
            }
            when(weapon.bonusDex){
                Bonus.S -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(1)
                Bonus.A -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(2)
                Bonus.B -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(3)
                Bonus.C -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(4)
                Bonus.D -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(5)
                Bonus.E -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(6)
            }
            when(weapon.bonusInt){
                Bonus.S -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(1)
                Bonus.A -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(2)
                Bonus.B -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(3)
                Bonus.C -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(4)
                Bonus.D -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(5)
                Bonus.E -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(6)
            }
            when(weapon.bonusFoi){
                Bonus.S -> dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).setSelection(1)
                Bonus.A -> dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).setSelection(2)
                Bonus.B -> dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).setSelection(3)
                Bonus.C -> dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).setSelection(4)
                Bonus.D -> dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).setSelection(5)
                Bonus.E -> dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).setSelection(6)
            }
            dialog.findViewById<EditText>(R.id.weapon_weight_txt).setText(weapon.weight.toString())
        }
    }

    fun editShield(view: View){
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentTypeLayout.setOnClickListener {
            val dialog = Dialog(activity)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setContentView(R.layout.popup_edit_shield)
            dialog.findViewById<TextView>(R.id.shield_type).text = getString(R.string.shield)
            fillShieldEdit(dialog)
            dialog.findViewById<EditText>(R.id.shield_name_txt).setSelection(dialog.findViewById<EditText>(R.id.shield_name_txt).text.length)
            dialog.findViewById<ImageView>(R.id.shield_cancel_button).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<TextView>(R.id.shield_delete_button).setOnClickListener {
                viewModel.shield.value?.let { viewModel.shield.value!!.reinit() }
                viewModel.editEquipment()
                dialog.dismiss()
            }
            dialog.findViewById<TextView>(R.id.shield_save_button).setOnClickListener {
                viewModel.shield.value!!.name = dialog.findViewById<EditText>(R.id.shield_name_txt).text.toString()
                if (dialog.findViewById<EditText>(R.id.shield_block_txt).text.toString().isNotEmpty()){
                    viewModel.shield.value!!.block = dialog.findViewById<EditText>(R.id.shield_block_txt).text.toString().toFloat()
                }else{
                    viewModel.shield.value!!.block = 0F
                }

                viewModel.shield.value!!.res?.let{  viewModel.shield.value!!.res.clear()}?:run{viewModel.shield.value!!.res = ArrayList<Elem>()}
                if (dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked && dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked
                        && dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked && dialog.findViewById<CheckBox>(R.id.shield_res_magic).isChecked){
                    viewModel.shield.value!!.res.add(Elem.ALL)
                }else {
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked) {
                        viewModel.shield.value!!.res.add(Elem.FIRE)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked) {
                        viewModel.shield.value!!.res.add(Elem.DARKNESS)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked) {
                        viewModel.shield.value!!.res.add(Elem.LIGHTNING)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_magic).isChecked) {
                        viewModel.shield.value!!.res.add(Elem.MAGIC)
                    }
                }

                if (dialog.findViewById<EditText>(R.id.shield_weight_txt).text.toString().isNotEmpty()){
                    viewModel.shield.value!!.weight = dialog.findViewById<EditText>(R.id.shield_weight_txt).text.toString().toFloat()
                }else{
                    viewModel.shield.value!!.weight = 0F
                }
                viewModel.editEquipment()
                dialog.dismiss()
            }

            dialog .show()
        }
    }

    fun fillShieldEdit(dialog: Dialog){
        viewModel.shield.value?.let{
            dialog.findViewById<EditText>(R.id.shield_name_txt).setText(viewModel.shield.value!!.name)
            dialog.findViewById<EditText>(R.id.shield_block_txt).setText(viewModel.shield.value!!.block.toString())
            viewModel.shield.value!!.res?.let {
                if (viewModel.shield.value!!.res.contains(Elem.FIRE)) dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked = true
                if (viewModel.shield.value!!.res.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked = true
                if (viewModel.shield.value!!.res.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked = true
                if (viewModel.shield.value!!.res.contains(Elem.MAGIC)) dialog.findViewById<CheckBox>(R.id.shield_res_magic).isChecked = true
                if (viewModel.shield.value!!.res.contains(Elem.ALL)) {
                    dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked = true
                }
            }

            dialog.findViewById<EditText>(R.id.shield_weight_txt).setText(viewModel.shield.value!!.weight.toString())
        }
    }

    fun editArmor(type: String, armor: Armor){
            val dialog = Dialog(activity)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setContentView(R.layout.popup_edit_armor)
            dialog.findViewById<TextView>(R.id.armor_type).text = type
            fillArmorEdit(dialog, armor)
            dialog.findViewById<EditText>(R.id.armor_name_txt).setSelection(dialog.findViewById<EditText>(R.id.armor_name_txt).text.length)
            dialog.findViewById<ImageView>(R.id.armor_cancel_button).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<TextView>(R.id.armor_delete_button).setOnClickListener {
                armor.reinit()
                viewModel.editEquipment()
                dialog.dismiss()
            }
            dialog.findViewById<TextView>(R.id.armor_save_button).setOnClickListener {
                armor.name = dialog.findViewById<EditText>(R.id.armor_name_txt).text.toString()
                if (dialog.findViewById<EditText>(R.id.armor_def_txt).text.toString().isNotEmpty()){
                    armor.def = dialog.findViewById<EditText>(R.id.armor_def_txt).text.toString().toFloat()
                }else{
                    armor.def = 0F
                }

                armor.immun?.let{  armor.immun.clear()}?:run{armor.immun = ArrayList<Status>()}
                if (dialog.findViewById<CheckBox>(R.id.armor_immun_frost).isChecked) {
                    armor.immun.add(Status.FROST)
                }
                if (dialog.findViewById<CheckBox>(R.id.armor_immun_poison).isChecked) {
                    armor.immun.add(Status.POISON)
                }
                if (dialog.findViewById<CheckBox>(R.id.armor_immun_bleed).isChecked) {
                    armor.immun.add(Status.BLEED)
                }

                armor.res?.let{  armor.res.clear()}?:run{armor.res = ArrayList<Elem>()}
                if (dialog.findViewById<CheckBox>(R.id.armor_res_fire).isChecked && dialog.findViewById<CheckBox>(R.id.armor_res_dark).isChecked
                        && dialog.findViewById<CheckBox>(R.id.armor_res_light).isChecked && dialog.findViewById<CheckBox>(R.id.armor_res_magic).isChecked){
                    armor.res.add(Elem.ALL)
                }else {
                    if (dialog.findViewById<CheckBox>(R.id.armor_res_fire).isChecked) {
                        armor.res.add(Elem.FIRE)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.armor_res_dark).isChecked) {
                        armor.res.add(Elem.DARKNESS)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.armor_res_light).isChecked) {
                        armor.res.add(Elem.LIGHTNING)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.armor_res_magic).isChecked) {
                        armor.res.add(Elem.MAGIC)
                    }
                }

                armor.weak?.let{  armor.weak.clear()}?:run{armor.weak = ArrayList<Elem>()}
                if (dialog.findViewById<CheckBox>(R.id.armor_weak_fire).isChecked && dialog.findViewById<CheckBox>(R.id.armor_weak_dark).isChecked
                        && dialog.findViewById<CheckBox>(R.id.armor_weak_light).isChecked && dialog.findViewById<CheckBox>(R.id.armor_weak_magic).isChecked){
                    armor.weak.add(Elem.ALL)
                }else {
                    if (dialog.findViewById<CheckBox>(R.id.armor_weak_fire).isChecked) {
                        armor.weak.add(Elem.FIRE)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.armor_weak_dark).isChecked) {
                        armor.weak.add(Elem.DARKNESS)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.armor_weak_light).isChecked) {
                        armor.weak.add(Elem.LIGHTNING)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.armor_weak_magic).isChecked) {
                        armor.weak.add(Elem.MAGIC)
                    }
                }

                if (dialog.findViewById<EditText>(R.id.armor_weight_txt).text.toString().isNotEmpty()){
                    armor.weight = dialog.findViewById<EditText>(R.id.armor_weight_txt).text.toString().toFloat()
                }else{
                    armor.weight = 0F
                }
                viewModel.editEquipment()
                dialog.dismiss()
            }

            dialog .show()

    }

    fun fillArmorEdit(dialog: Dialog, armor: Armor?){
        if(!armor!!.name.isNullOrEmpty()){
            dialog.findViewById<EditText>(R.id.armor_name_txt).setText(armor.name)
            dialog.findViewById<EditText>(R.id.armor_def_txt).setText(armor.def.toString())

                if(armor.immun.contains(Status.BLEED))dialog.findViewById<CheckBox>(R.id.armor_immun_bleed).isChecked = true
                if(armor.immun.contains(Status.POISON))dialog.findViewById<CheckBox>(R.id.armor_immun_poison).isChecked = true
                if(armor.immun.contains(Status.FROST))dialog.findViewById<CheckBox>(R.id.armor_immun_frost).isChecked = true


                if(armor.res.contains(Elem.MAGIC))dialog.findViewById<CheckBox>(R.id.armor_res_magic).isChecked = true
                if(armor.res.contains(Elem.FIRE))dialog.findViewById<CheckBox>(R.id.armor_res_fire).isChecked = true
                if(armor.res.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.armor_res_dark).isChecked = true
                if(armor.res.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.armor_res_light).isChecked = true
                if(armor.res.contains(Elem.ALL)){
                    dialog.findViewById<CheckBox>(R.id.armor_res_magic).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.armor_res_fire).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.armor_res_dark).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.armor_res_light).isChecked = true
                }


                if(armor.weak.contains(Elem.MAGIC)) dialog.findViewById<CheckBox>(R.id.armor_weak_magic).isChecked = true
                if(armor.weak.contains(Elem.FIRE)) dialog.findViewById<CheckBox>(R.id.armor_weak_fire).isChecked = true
                if(armor.weak.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.armor_weak_dark).isChecked = true
                if(armor.weak.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.armor_weak_light).isChecked = true
                if(armor.weak.contains(Elem.ALL)){
                    dialog.findViewById<CheckBox>(R.id.armor_weak_magic).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.armor_weak_fire).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.armor_weak_dark).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.armor_weak_light).isChecked = true
                }

            dialog.findViewById<EditText>(R.id.armor_weight_txt).setText(armor.weight.toString())
        }
    }

    fun initViewDisabled(view: View){
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_damages).setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_defense).setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_res).setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_immun).setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.equipment_stat_weak).setEnabled(false)
    }

    fun getListStringBonus():ArrayList<String>{
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
}
