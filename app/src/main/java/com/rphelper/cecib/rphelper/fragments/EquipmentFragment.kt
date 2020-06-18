package com.rphelper.cecib.rphelper.fragments


import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.EquipmentComponent
import com.rphelper.cecib.rphelper.component.IndicSoloComponent
import com.rphelper.cecib.rphelper.dto.Armor
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.Status
import com.rphelper.cecib.rphelper.utils.DisplayUtils
import com.rphelper.cecib.rphelper.viewmodel.EquipmentViewModel
import kotlinx.android.synthetic.main.component_equipment.view.*


class EquipmentFragment : Fragment() {

    private lateinit var viewModel: EquipmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_equipment, container, false)

        viewModel = EquipmentViewModel(context!!)

        /********** Stats **********/
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_damages).indicSoloTitle.text = getString(R.string.damages)
        setOnClickListenerIndicDrop(R.id.equipment_stat_damages, view)
        viewModel.damages.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_damages).indicSoloCurrent.text = it.toString()
        })
        viewModel.damagesBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_damages).indicSoloBonus.text = DisplayUtils.stringBonus(it!!)
        })
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_damages).indicSoloEditBonus.setOnClickListener {
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.damageBonusTxt), Preferences.PREF_MODIFIER_DAMAGES_TEMP, { viewModel.updateEquipment() })
        }
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloTitle.text = getString(R.string.def)
        setOnClickListenerIndicDrop(R.id.equipment_stat_defense, view)
        viewModel.defense.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloCurrent.setText(it.toString())
        })
        viewModel.defenseBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloBonus.text = DisplayUtils.stringBonus(it!!)
        })
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloEditBonus.setOnClickListener {
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.defBonusTxt), Preferences.PREF_MODIFIER_DEFENSE_TEMP, { viewModel.updateEquipment() })
        }
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_res).indicSoloTitle.text = getString(R.string.res)
        setOnClickListenerIndicDrop(R.id.equipment_stat_res, view)
        viewModel.res.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_res).indicSoloCurrent.setText(it.toString())
        })
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_res).indicSoloEditBonus.setOnClickListener {
            displaySelectElemDialog(getString(R.string.resBonusTxt), Preferences.PREF_MODIFIER_RES_TEMP)
        }
        viewModel.resBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_res).indicSoloBonus.text = DisplayUtils.stringBonus(it!!)
        })
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_immun).indicSoloTitle.text = getString(R.string.immun)
        setOnClickListenerIndicDrop(R.id.equipment_stat_immun, view)
        viewModel.immun.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_immun).indicSoloCurrent.setText(it.toString())
        })
        viewModel.immunBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_immun).indicSoloBonus.text = DisplayUtils.stringBonus(it!!)
        })
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_immun).indicSoloEditBonus.setOnClickListener {
            displaySelectStatusDialog(getString(R.string.immunBonusTxt), Preferences.PREF_MODIFIER_IMMUN_TEMP)
        }
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_weak).indicSoloTitle.text = getString(R.string.fai)
        setOnClickListenerIndicDrop(R.id.equipment_stat_weak, view)
        viewModel.weak.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_weak).indicSoloCurrent.setText(it.toString())
        })
        viewModel.weakBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_weak).indicSoloBonus.text = DisplayUtils.stringBonus(it!!)
        })
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_weak).indicSoloEditBonus.setOnClickListener {
            displaySelectElemDialog(getString(R.string.weakBonusTxt), Preferences.PREF_MODIFIER_WEAK_TEMP)
        }

        /******** Left hand **********/
        viewModel.leftHand.observe(viewLifecycleOwner, Observer {
            initWeaponView(view, R.id.equipment_left_hand, getString(R.string.left_hand), it)
        })

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
            initArmorView(view, R.id.equipment_gloves, getString(R.string.gloves), it)
        })

        /******** Greaves **********/
        viewModel.greaves.observe(viewLifecycleOwner, Observer {
            initArmorView(view, R.id.equipment_greaves, getString(R.string.greaves), it)
        })


        /*********** EDIT **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.left_hand), viewModel.leftHand.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.right_hand), viewModel.rightHand.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_catalyst).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.catalyst), viewModel.catalyst.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentTypeLayout.setOnClickListener {
            editShield()
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.hat), viewModel.hat.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.chestplate), viewModel.chest.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.gloves), viewModel.gloves.value!!)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.greaves), viewModel.greaves.value!!)
        }

        return view
    }

    fun initWeaponView(view: View, id: Int, type: String, weapon: Weapon?) {
        weapon?.let {
            view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
            view!!.findViewById<EquipmentComponent>(id).equipment_name.text = weapon.name
            if (type.equals(context!!.getString(R.string.catalyst))) {
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanel.visibility = View.GONE
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanel.visibility = View.VISIBLE
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTitle.text = getString(R.string.boost)
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = weapon.boost.toString()
                view.findViewById<EquipmentComponent>(id).equipmentSecondLine.visibility = View.GONE
                view.findViewById<EquipmentComponent>(id).equipmentLargePanelLayout.visibility = View.GONE
            } else {
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanel.visibility = View.VISIBLE
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.damages)
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = weapon.damage.toString()
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanel.visibility = View.GONE
                view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = if (null != weapon.status && !weapon.status.equals(Status.NOTHING)) weapon.status.toString() else getString(R.string.status)
                view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = if (null != weapon.statusValue && weapon.statusValue != 0F) weapon.statusValue.toString() else "/"
                view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.affinity)
                view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTxt.text = if (null != weapon.affinity && !weapon.affinity.equals(Elem.NOTHING)) weapon.affinity.toString() else "/"
                view.findViewById<EquipmentComponent>(id).equipmentSecondLine.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentLargePanelLayout.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentLargePanelTxt.text = viewModel.getTotalDamage(weapon).toString()
            }
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusForTxt.text = if (null != weapon.bonusFor && !weapon.bonusFor.equals(Bonus.NOTHING)) weapon.bonusFor.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusDexTxt.text = if (null != weapon.bonusDex && !weapon.bonusDex.equals(Bonus.NOTHING)) weapon.bonusDex.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusIntTxt.text = if (null != weapon.bonusInt && !weapon.bonusInt.equals(Bonus.NOTHING)) weapon.bonusInt.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentBonusFoiTxt.text = if (null != weapon.bonusFoi && !weapon.bonusFoi.equals(Bonus.NOTHING)) weapon.bonusFoi.toString() else "/"
            view!!.findViewById<EquipmentComponent>(id).equipmentWeightTxt.text = weapon.weight.toString()
        }
    }

    fun initShieldView(view: View) {
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

    fun initArmorView(view: View, id: Int, type: String, armor: Armor?) {
        view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
        view!!.findViewById<EquipmentComponent>(id).equipment_name.text = armor!!.name
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.def)
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = armor!!.def.toString()
        view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTitle.text = getString(R.string.res)
        if (null != armor!!.res && armor!!.res.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.res) {
                stringRes += res.toString() + " "
            }
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = stringRes
        } else {
            view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = getString(R.string.fai)
        if (null != armor!!.weak && armor!!.weak.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.weak) {
                stringRes += res.toString() + "\n"
            }
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = stringRes
        } else {
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.immun)
        if (null != armor!!.immun && armor!!.immun.isNotEmpty()) {
            var stringRes = ""
            for (res in armor!!.immun) {
                stringRes += res.toString() + "\n"
            }
            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTxt.text = stringRes
        } else {
            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTxt.text = "/"
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentBonusLayout.visibility = View.GONE
        view!!.findViewById<EquipmentComponent>(id).equipmentLargePanelLayout.visibility = View.GONE
        view!!.findViewById<EquipmentComponent>(id).equipmentWeightTxt.text = armor!!.weight.toString()
    }

    fun editWeapon(type: String, weapon: Weapon) {
        DisplayUtils.openWeaponDialog(type, weapon, context!!, activity!!,
                {
                    displayMsg(weapon.name)
                    viewModel.weaponToInventory(type, weapon)
                },
                {
                    val builder = AlertDialog.Builder(context)
                    with(builder)
                    {
                        setTitle(getString(R.string.warning))
                        setMessage(getString(R.string.confirm_delete))
                        setNegativeButton(getString(R.string.no)) { dialog, which ->
                            dialog.dismiss()
                        }
                        setPositiveButton(getString(R.string.ok)) { dialog, which ->
                            weapon.reinit()
                            viewModel.editEquipment()
                            dialog.dismiss()
                        }
                        show()
                    }
                },
                {
                    weapon.equip = true
                    viewModel.editEquipment()
                })
    }

    fun editShield() {
        DisplayUtils.openShieldDialog(viewModel.shield.value!!, context!!, activity!!,
                {
                    displayMsg(viewModel.shield.value!!.name)
                    viewModel.shieldToInventory(viewModel.shield.value!!)
                },
                {
                    viewModel.shield.value?.let { viewModel.shield.value!!.reinit() }
                    viewModel.editEquipment()
                },
                {
                    viewModel.shield.value!!.equip = true
                    viewModel.editEquipment()
                })
    }

    fun editArmor(type: String, armor: Armor) {
        DisplayUtils.openArmorDialog(type, armor, context!!, activity!!,
                {
                    displayMsg(armor.name)
                    viewModel.armorToInventory(type, armor)
                },
                {
                    armor.reinit()
                    viewModel.editEquipment()
                },
                {
                    armor.equip = true
                    viewModel.editEquipment()
                })
    }

    fun displayMsg(name: String) {
        var snackMsg = ""
        snackMsg = name + " " + getString(R.string.disequip_equipment_msg)
        Snackbar.make(view!!, snackMsg, Snackbar.LENGTH_SHORT).show()
    }

    fun setOnClickListenerIndicDrop(id: Int, view: View) {
        view.findViewById<IndicSoloComponent>(id).indicSoloDrop.setOnClickListener {
            if (view.findViewById<IndicSoloComponent>(id).indicSoloButtonsLayout.visibility == View.GONE) {
                view.findViewById<IndicSoloComponent>(id).indicSoloButtonsLayout.visibility = View.VISIBLE
                view.findViewById<IndicSoloComponent>(id).indicSoloDrop.setImageResource(R.drawable.ic_arrow_drop_up)
            } else {
                view.findViewById<IndicSoloComponent>(id).indicSoloButtonsLayout.visibility = View.GONE
                view.findViewById<IndicSoloComponent>(id).indicSoloDrop.setImageResource(R.drawable.ic_arrow_drop_down)
            }
        }
    }

    fun displaySelectElemDialog(txt: String, pref: String) {
        val builder = AlertDialog.Builder(context)
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_select_elem, null)
        builder.setView(dialogLayout)
        builder.setTitle(getString(R.string.bonusTitle))
        val txtView = dialogLayout.findViewById<TextView>(R.id.select_elem_txt)
        txtView.text = txt
        val magic = dialogLayout.findViewById<CheckBox>(R.id.select_elem_magic)
        val fire = dialogLayout.findViewById<CheckBox>(R.id.select_elem_fire)
        val light = dialogLayout.findViewById<CheckBox>(R.id.select_elem_light)
        val dark = dialogLayout.findViewById<CheckBox>(R.id.select_elem_dark)

        builder.setPositiveButton(context!!.getString(R.string.ok)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            var value = ""
            if (magic.isChecked) value += Elem.MAGIC.name
            if (fire.isChecked) value += " " + Elem.FIRE.name
            if (light.isChecked) value += " " + Elem.LIGHTNING.name
            if (dark.isChecked) value += " " + Elem.DARKNESS.name
            editor.putString(pref, value)
            editor.apply()
            viewModel.editEquipment()
        }
        builder.setNeutralButton(context!!.getString(R.string.reset)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putString(pref, "")
            editor.apply()
            viewModel.editEquipment()
        }

        builder.show()
    }

    fun displaySelectStatusDialog(txt: String, pref: String) {
        val builder = AlertDialog.Builder(context)
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_select_status, null)
        builder.setView(dialogLayout)
        builder.setTitle(getString(R.string.bonusTitle))
        val txtView = dialogLayout.findViewById<TextView>(R.id.select_status_txt)
        txtView.text = txt
        val bleed = dialogLayout.findViewById<CheckBox>(R.id.select_status_bleed)
        val poison = dialogLayout.findViewById<CheckBox>(R.id.select_status_poison)
        val frost = dialogLayout.findViewById<CheckBox>(R.id.select_status_frost)

        builder.setPositiveButton(context!!.getString(R.string.ok)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            var value = ""
            if (bleed.isChecked) value += Status.BLEED.name
            if (poison.isChecked) value += " " + Status.POISON.name
            if (frost.isChecked) value += " " + Status.FROST.name
            editor.putString(pref, value)
            editor.apply()
            viewModel.editEquipment()
        }
        builder.setNeutralButton(context!!.getString(R.string.reset)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putString(pref, "")
            editor.apply()
            viewModel.editEquipment()
        }

        builder.show()
    }
}
