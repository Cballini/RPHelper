package com.rphelper.cecib.rphelper.fragments


import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.rphelper.cecib.rphelper.MainActivity
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.EquipmentComponent
import com.rphelper.cecib.rphelper.component.IndicSoloComponent
import com.rphelper.cecib.rphelper.component.IndicSoloIconsComponent
import com.rphelper.cecib.rphelper.dto.*
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

        viewModel = EquipmentViewModel(context!!, MainActivity.viewModel.character.value!!, MainActivity.viewModel.equipment.value!!, MainActivity.viewModel.inventory.value!!)

        MainActivity.viewModel.character.observe(viewLifecycleOwner, Observer {
            viewModel.character = it
            viewModel._damages.value = viewModel.getDamages()
            viewModel._defense.value = viewModel.getDef()
        })
        MainActivity.viewModel.equipment.observe(viewLifecycleOwner, Observer {
            viewModel.equipment = it
            viewModel._defense.value = viewModel.getDef()
            viewModel._res.value = viewModel.getRes()
            viewModel._immun.value = viewModel.getImmun()
            viewModel._weak.value = viewModel.getWeak()
            initWeaponView(view, R.id.equipment_left_hand, getString(R.string.left_hand), it.leftHand)
            initWeaponView(view, R.id.equipment_right_hand, getString(R.string.right_hand), it.rightHand)
            initWeaponView(view, R.id.equipment_catalyst, getString(R.string.catalyst), it.catalyst)
            initShieldView(view, it.shield)
            initArmorView(view, R.id.equipment_hat, getString(R.string.hat), it.hat)
            initArmorView(view, R.id.equipment_chest, getString(R.string.chestplate), it.chest)
            initArmorView(view, R.id.equipment_gloves, getString(R.string.gloves), it.gloves)
            initArmorView(view, R.id.equipment_greaves, getString(R.string.greaves), it.greaves)
        })

        MainActivity.viewModel.inventory.observe(viewLifecycleOwner, Observer {
            viewModel.inventory = it
        })

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
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.damageBonusTxt), Preferences.PREF_MODIFIER_DAMAGES_TEMP, { viewModel.updateEquipmentBonus() })
        }
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloTitle.text = getString(R.string.def)
        setOnClickListenerIndicDrop(R.id.equipment_stat_defense, view)
        viewModel.defense.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloCurrent.text =it.toString()
        })
        viewModel.defenseBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloBonus.text = DisplayUtils.stringBonus(it!!)
        })
        view.findViewById<IndicSoloComponent>(R.id.equipment_stat_defense).indicSoloEditBonus.setOnClickListener {
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.defBonusTxt), Preferences.PREF_MODIFIER_DEFENSE_TEMP, { viewModel.updateEquipmentBonus() })
        }
        view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsTitle.text = getString(R.string.res)
        setOnClickListenerIconsIndicDrop(R.id.equipment_stat_res, view)
        viewModel.res.observe(viewLifecycleOwner, Observer {
            //init
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsFire.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsMagic.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsLight.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsDark.visibility = View.GONE
            if(it.contains(Elem.FIRE.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsFire.visibility = View.VISIBLE
            if(it.contains(Elem.MAGIC.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsMagic.visibility = View.VISIBLE
            if(it.contains(Elem.LIGHTNING.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsLight.visibility = View.VISIBLE
            if(it.contains(Elem.DARKNESS.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsDark.visibility = View.VISIBLE
            if(it.contains(Elem.ALL.name)){
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsFire.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsMagic.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsLight.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsDark.visibility = View.VISIBLE
            }
        })
        view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsEditBonus.setOnClickListener {
            displaySelectElemDialog(getString(R.string.resBonusTxt), Preferences.PREF_MODIFIER_RES_TEMP)
        }
        viewModel.resBonus.observe(viewLifecycleOwner, Observer {
            //init
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusFire.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusMagic.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusLight.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusDark.visibility = View.GONE
            if(it.contains(Elem.FIRE.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusFire.visibility = View.VISIBLE
            if(it.contains(Elem.MAGIC.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusMagic.visibility = View.VISIBLE
            if(it.contains(Elem.LIGHTNING.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusLight.visibility = View.VISIBLE
            if(it.contains(Elem.DARKNESS.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusDark.visibility = View.VISIBLE
            if(it.contains(Elem.ALL.name)){
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusFire.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusMagic.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusLight.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_res).indicSoloIconsBonusDark.visibility = View.VISIBLE
            }
        })
        view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsTitle.text = getString(R.string.immun)
        setOnClickListenerIconsIndicDrop(R.id.equipment_stat_immun, view)
        viewModel.immun.observe(viewLifecycleOwner, Observer {
            //init
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsPoison.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsFrost.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBleed.visibility = View.GONE
            if(it.contains(Status.POISON.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsPoison.visibility = View.VISIBLE
            if(it.contains(Status.FROST.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsFrost.visibility = View.VISIBLE
            if(it.contains(Status.BLEED.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBleed.visibility = View.VISIBLE
        })
        viewModel.immunBonus.observe(viewLifecycleOwner, Observer {
            //init
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBonusPoison.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBonusFrost.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBonusBleed.visibility = View.GONE
            if(it.contains(Status.POISON.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBonusPoison.visibility = View.VISIBLE
            if(it.contains(Status.FROST.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBonusFrost.visibility = View.VISIBLE
            if(it.contains(Status.BLEED.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsBonusBleed.visibility = View.VISIBLE
        })
        view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_immun).indicSoloIconsEditBonus.setOnClickListener {
            displaySelectStatusDialog(getString(R.string.immunBonusTxt), Preferences.PREF_MODIFIER_IMMUN_TEMP)
        }
        view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsTitle.text = getString(R.string.fai)
        setOnClickListenerIconsIndicDrop(R.id.equipment_stat_weak, view)
        viewModel.weak.observe(viewLifecycleOwner, Observer {
            //init
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsFire.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsMagic.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsLight.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsDark.visibility = View.GONE
            if(it.contains(Elem.FIRE.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsFire.visibility = View.VISIBLE
            if(it.contains(Elem.MAGIC.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsMagic.visibility = View.VISIBLE
            if(it.contains(Elem.LIGHTNING.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsLight.visibility = View.VISIBLE
            if(it.contains(Elem.DARKNESS.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsDark.visibility = View.VISIBLE
            if(it.contains(Elem.ALL.name)){
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsFire.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsMagic.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsLight.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsDark.visibility = View.VISIBLE
            }
        })
        viewModel.weakBonus.observe(viewLifecycleOwner, Observer {
            //init
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusFire.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusMagic.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusLight.visibility = View.GONE
            view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusDark.visibility = View.GONE
            if(it.contains(Elem.FIRE.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusFire.visibility = View.VISIBLE
            if(it.contains(Elem.MAGIC.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusMagic.visibility = View.VISIBLE
            if(it.contains(Elem.LIGHTNING.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusLight.visibility = View.VISIBLE
            if(it.contains(Elem.DARKNESS.name)) view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusDark.visibility = View.VISIBLE
            if(it.contains(Elem.ALL.name)){
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusFire.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusMagic.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusLight.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsBonusDark.visibility = View.VISIBLE
            }
        })
        view.findViewById<IndicSoloIconsComponent>(R.id.equipment_stat_weak).indicSoloIconsEditBonus.setOnClickListener {
            displaySelectElemDialog(getString(R.string.weakBonusTxt), Preferences.PREF_MODIFIER_WEAK_TEMP)
        }

        //Help
        view.findViewById<ImageView>(R.id.equipment_help).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.help))
                setMessage(getString(R.string.equipment_help))
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }

        //Help
        view.findViewById<ImageView>(R.id.weapon_help).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.help))
                setMessage(getString(R.string.weapon_help))
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }


        /*********** EDIT **********/
        view.findViewById<EquipmentComponent>(R.id.equipment_left_hand).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.left_hand), viewModel.equipment.leftHand)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_right_hand).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.right_hand), viewModel.equipment.rightHand)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_catalyst).equipmentTypeLayout.setOnClickListener {
            editWeapon(getString(R.string.catalyst), viewModel.equipment.catalyst)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentTypeLayout.setOnClickListener {
            editShield()
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_hat).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.hat), viewModel.equipment.hat)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_chest).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.chestplate), viewModel.equipment.chest)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_gloves).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.gloves), viewModel.equipment.gloves)
        }
        view.findViewById<EquipmentComponent>(R.id.equipment_greaves).equipmentTypeLayout.setOnClickListener {
            editArmor(getString(R.string.greaves), viewModel.equipment.greaves)
        }

        return view
    }

    fun initWeaponView(view: View, id: Int, type: String, weapon: Weapon?) {
        weapon?.let {
            view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
            view!!.findViewById<EquipmentComponent>(id).equipment_name.text = weapon.name
            if (type.equals(context!!.getString(R.string.catalyst))) {
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanel.visibility = View.GONE
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanel.visibility = View.VISIBLE
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.boost)
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = weapon.boost.toString()
                view.findViewById<EquipmentComponent>(id).equipmentSecondLine.visibility = View.GONE
                view.findViewById<EquipmentComponent>(id).equipmentLargePanelLayout.visibility = View.GONE
            } else {
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanel.visibility = View.VISIBLE
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.damages)
                view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = weapon.damage.toString()
                view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanel.visibility = View.GONE
                view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = getString(R.string.status)
                weapon.status?.let{
                    when(weapon.status){
                        Status.POISON -> view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelPoison.visibility = View.VISIBLE
                        Status.FROST -> view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelFrost.visibility = View.VISIBLE
                        Status.BLEED -> view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelBleed.visibility = View.VISIBLE
                        else -> {
                            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelPoison.visibility = View.GONE
                            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelFrost.visibility = View.GONE
                            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelBleed.visibility = View.GONE
                        }
                    }
                }
                view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.text = if (null != weapon.statusValue && weapon.statusValue != 0F) weapon.statusValue.toString() else "/"
                view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.affinity)
                weapon.affinity?.let{
                    when(weapon.affinity){
                        Elem.FIRE -> view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelFire.visibility = View.VISIBLE
                        Elem.MAGIC -> view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelMagic.visibility = View.VISIBLE
                        Elem.LIGHTNING -> view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelLight.visibility = View.VISIBLE
                        Elem.DARKNESS -> view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelDark.visibility = View.VISIBLE
                        else -> {
                            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelFire.visibility = View.GONE
                            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelMagic.visibility = View.GONE
                            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelLight.visibility = View.GONE
                            view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelDark.visibility = View.GONE
                        }
                    }
                }
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

    fun initShieldView(view: View, shield: Shield) {
        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentType.text = getString(R.string.shield)
            shield?.let {
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipment_name.text = it.name
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTitle.text = getString(R.string.block)
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTxt.text = it.block.toString()
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTitle.text = getString(R.string.res)
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelFire.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelMagic.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelLight.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelDark.visibility = View.GONE
                if (null != it.res && it.res.isNotEmpty()) {
                    if(it.res.contains(Elem.FIRE)) view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelFire.visibility = View.VISIBLE
                    if(it.res.contains(Elem.MAGIC)) view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelMagic.visibility = View.VISIBLE
                    if(it.res.contains(Elem.LIGHTNING)) view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelLight.visibility = View.VISIBLE
                    if(it.res.contains(Elem.DARKNESS)) view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelDark.visibility = View.VISIBLE
                    if(it.res.contains(Elem.ALL)) {
                        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelFire.visibility = View.VISIBLE
                        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelMagic.visibility = View.VISIBLE
                        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelLight.visibility = View.VISIBLE
                        view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelDark.visibility = View.VISIBLE
                    }
                }
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondLine.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentBonusLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentLargePanelLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentWeightTxt.text = it.weight.toString()
            } ?: run {
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipment_name.text = ""
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTitle.text = getString(R.string.block)
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentFirstPanelTxt.text = ""
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondPanelTitle.text = getString(R.string.res)
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentSecondLine.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentBonusLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentLargePanelLayout.visibility = View.GONE
                view.findViewById<EquipmentComponent>(R.id.equipment_shield).equipmentWeightTxt.text = "0"
            }
    }

    fun initArmorView(view: View, id: Int, type: String, armor: Armor?) {
        view!!.findViewById<EquipmentComponent>(id).equipmentType.text = type
        view!!.findViewById<EquipmentComponent>(id).equipment_name.text = armor!!.name
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTitle.text = getString(R.string.def)
        view!!.findViewById<EquipmentComponent>(id).equipmentFirstPanelTxt.text = armor!!.def.toString()
        view!!.findViewById<EquipmentComponent>(id).equipmentSecondPanelTitle.text = getString(R.string.res)
        view.findViewById<EquipmentComponent>(id).equipmentSecondPanelFire.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentSecondPanelMagic.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentSecondPanelLight.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentSecondPanelDark.visibility = View.GONE
        if (null != armor!!.res && armor!!.res.isNotEmpty()) {
            if(armor.res.contains(Elem.FIRE)) view.findViewById<EquipmentComponent>(id).equipmentSecondPanelFire.visibility = View.VISIBLE
            if(armor.res.contains(Elem.MAGIC)) view.findViewById<EquipmentComponent>(id).equipmentSecondPanelMagic.visibility = View.VISIBLE
            if(armor.res.contains(Elem.LIGHTNING)) view.findViewById<EquipmentComponent>(id).equipmentSecondPanelLight.visibility = View.VISIBLE
            if(armor.res.contains(Elem.DARKNESS)) view.findViewById<EquipmentComponent>(id).equipmentSecondPanelDark.visibility = View.VISIBLE
            if(armor.res.contains(Elem.ALL)){
                view.findViewById<EquipmentComponent>(id).equipmentSecondPanelFire.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentSecondPanelMagic.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentSecondPanelLight.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentSecondPanelDark.visibility = View.VISIBLE
            }
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTitle.text = getString(R.string.immun)
        view.findViewById<EquipmentComponent>(id).equipmentThirdPanelPoison.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentThirdPanelFrost.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentThirdPanelBleed.visibility = View.GONE
        if (null != armor!!.immun && armor!!.immun.isNotEmpty()) {
            view!!.findViewById<EquipmentComponent>(id).equipmentThirdPanelTxt.visibility = View.GONE
            if(armor.immun.contains(Status.POISON)) view.findViewById<EquipmentComponent>(id).equipmentThirdPanelPoison.visibility = View.VISIBLE
            if(armor.immun.contains(Status.FROST)) view.findViewById<EquipmentComponent>(id).equipmentThirdPanelFrost.visibility = View.VISIBLE
            if(armor.immun.contains(Status.BLEED)) view.findViewById<EquipmentComponent>(id).equipmentThirdPanelBleed.visibility = View.VISIBLE
        }
        view!!.findViewById<EquipmentComponent>(id).equipmentFourthPanelTitle.text = getString(R.string.fai)
        view.findViewById<EquipmentComponent>(id).equipmentFourthPanelFire.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentFourthPanelMagic.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentFourthPanelLight.visibility = View.GONE
        view.findViewById<EquipmentComponent>(id).equipmentFourthPanelDark.visibility = View.GONE
        if (null != armor!!.weak && armor!!.weak.isNotEmpty()) {
            if(armor.weak.contains(Elem.FIRE)) view.findViewById<EquipmentComponent>(id).equipmentFourthPanelFire.visibility = View.VISIBLE
            if(armor.weak.contains(Elem.MAGIC)) view.findViewById<EquipmentComponent>(id).equipmentFourthPanelMagic.visibility = View.VISIBLE
            if(armor.weak.contains(Elem.LIGHTNING)) view.findViewById<EquipmentComponent>(id).equipmentFourthPanelLight.visibility = View.VISIBLE
            if(armor.weak.contains(Elem.DARKNESS)) view.findViewById<EquipmentComponent>(id).equipmentFourthPanelDark.visibility = View.VISIBLE
            if(armor.weak.contains(Elem.ALL)) {
                view.findViewById<EquipmentComponent>(id).equipmentFourthPanelFire.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentFourthPanelMagic.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentFourthPanelLight.visibility = View.VISIBLE
                view.findViewById<EquipmentComponent>(id).equipmentFourthPanelDark.visibility = View.VISIBLE
            }
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
        DisplayUtils.openShieldDialog(viewModel.equipment.shield, context!!, activity!!,
                {
                    displayMsg(viewModel.equipment.shield.name)
                    viewModel.shieldToInventory(viewModel.equipment.shield)
                },
                {
                    viewModel.equipment.shield?.let { viewModel.equipment.shield.reinit() }
                    viewModel.editEquipment()
                },
                {
                    viewModel.equipment.shield.equip = true
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

    fun setOnClickListenerIconsIndicDrop(id: Int, view: View) {
        view.findViewById<IndicSoloIconsComponent>(id).indicSoloIconsDrop.setOnClickListener {
            if (view.findViewById<IndicSoloIconsComponent>(id).indicSoloIconsButtonsLayout.visibility == View.GONE) {
                view.findViewById<IndicSoloIconsComponent>(id).indicSoloIconsButtonsLayout.visibility = View.VISIBLE
                view.findViewById<IndicSoloIconsComponent>(id).indicSoloIconsDrop.setImageResource(R.drawable.ic_arrow_drop_up)
            } else {
                view.findViewById<IndicSoloIconsComponent>(id).indicSoloIconsButtonsLayout.visibility = View.GONE
                view.findViewById<IndicSoloIconsComponent>(id).indicSoloIconsDrop.setImageResource(R.drawable.ic_arrow_drop_down)
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
            viewModel.updateEquipmentBonus()
        }
        builder.setNeutralButton(context!!.getString(R.string.reset)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putString(pref, "")
            editor.apply()
            viewModel.updateEquipmentBonus()
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
            viewModel.updateEquipmentBonus()
        }
        builder.setNeutralButton(context!!.getString(R.string.reset)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putString(pref, "")
            editor.apply()
            viewModel.updateEquipmentBonus()
        }

        builder.show()
    }
}
