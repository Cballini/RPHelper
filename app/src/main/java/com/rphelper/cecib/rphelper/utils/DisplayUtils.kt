package com.rphelper.cecib.rphelper.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.dto.Armor
import com.rphelper.cecib.rphelper.dto.Shield
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.enums.Bonus
import com.rphelper.cecib.rphelper.enums.Bonus.Companion.getListStringBonus
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.PieceEquipment
import com.rphelper.cecib.rphelper.enums.Status

object DisplayUtils {
    @JvmStatic
    fun hideKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmStatic
    fun stringBonus(bonus :Int) : String{
        val sign = if (bonus>=0) "+ " else ""
        return "(" + sign + bonus.toString() + ")"
    }

    @JvmStatic
    fun stringBonusString(bonus :String) : String{
        val string = if (bonus.isNotEmpty()) "(" + bonus.toString() + ")" else ""
        return string
    }

    @JvmStatic
    fun displayEditIndicBonusDialog(context: Context, txt: String, pref :String, toDo: () -> Unit){
        val builder = AlertDialog.Builder(context)
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_indic, null)
        builder.setView(dialogLayout)
        builder.setTitle(context.getString(R.string.bonusTitle))
        val txtView = dialogLayout.findViewById<TextView>(R.id.edit_indic_txt)
        txtView.text = txt
        val editText = dialogLayout.findViewById<EditText>(R.id.edit_indic_value)

        builder.setPositiveButton(context.getString(R.string.ok)) { dialogInterface, i ->
            dialogInterface.dismiss()

            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            var value = 0
            if (editText.text.isNotEmpty()) { value = editText.text.toString().toInt() }
            editor.putInt(pref, value)
            editor.apply()
            toDo()
        }

        builder.setNeutralButton(context.getString(R.string.reset)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putInt(pref, 0)
            editor.apply()
            toDo()
        }
        builder.show()
    }


    @JvmStatic
    fun openWeaponDialog(type: String, weapon: Weapon,context: Context, activity :Activity,  toDoEquip: () -> Unit,  toDoDelete: () -> Unit,  toDoSave: () -> Unit) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_edit_weapon)
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
        if(!weapon.equip) dialog.findViewById<TextView>(R.id.weapon_disequip_button).text = context.getString(R.string.equiper)
        dialog.findViewById<EditText>(R.id.weapon_name_txt).setSelection(dialog.findViewById<EditText>(R.id.weapon_name_txt).text.length)
        dialog.findViewById<ImageView>(R.id.weapon_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.weapon_disequip_button).setOnClickListener {
            toDoEquip()
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.weapon_delete_button).setOnClickListener {
            removeEquipment(context, toDoDelete)
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.weapon_save_button).setOnClickListener {
            weapon.name = dialog.findViewById<EditText>(R.id.weapon_name_txt).text.toString()
            if (dialog.findViewById<EditText>(R.id.weapon_damage_txt).text.toString().isNotEmpty()) {
                weapon.damage = dialog.findViewById<EditText>(R.id.weapon_damage_txt).text.toString().toInt()
            } else {
                weapon.damage = 0
            }
            if (dialog.findViewById<EditText>(R.id.weapon_boost_txt).text.toString().isNotEmpty()) {
                weapon.boost = dialog.findViewById<EditText>(R.id.weapon_boost_txt).text.toString().toInt()
            } else {
                weapon.boost = 0
            }
            if (dialog.findViewById<CheckBox>(R.id.weapon_rapidfire).isChecked) weapon.rapidFire = true
            if (dialog.findViewById<CheckBox>(R.id.weapon_rapidfire).isChecked) weapon.rapidFire = true
            if (dialog.findViewById<EditText>(R.id.weapon_status_proc_txt).text.toString().isNotEmpty()) {
                weapon.statusValue = dialog.findViewById<EditText>(R.id.weapon_status_proc_txt).text.toString().toFloat()
            } else {
                weapon.statusValue = 0F
            }
            weapon.status = when (true) {
                dialog.findViewById<CheckBox>(R.id.weapon_status_bleed).isChecked -> Status.BLEED
                dialog.findViewById<CheckBox>(R.id.weapon_status_poison).isChecked -> Status.POISON
                dialog.findViewById<CheckBox>(R.id.weapon_status_frost).isChecked -> Status.FROST
                else -> Status.NOTHING
            }
            weapon.affinity = when (true) {
                dialog.findViewById<CheckBox>(R.id.weapon_affinity_fire).isChecked -> Elem.FIRE
                dialog.findViewById<CheckBox>(R.id.weapon_affinity_dark).isChecked -> Elem.DARKNESS
                dialog.findViewById<CheckBox>(R.id.weapon_affinity_light).isChecked -> Elem.LIGHTNING
                dialog.findViewById<CheckBox>(R.id.weapon_affinity_magic).isChecked -> Elem.MAGIC
                else -> Elem.NOTHING
            }
            weapon.bonusFor = when (true) {
                dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                else -> Bonus.NOTHING
            }
            weapon.bonusDex = when (true) {
                dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                else -> Bonus.NOTHING
            }
            weapon.bonusInt = when (true) {
                dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                else -> Bonus.NOTHING
            }
            weapon.bonusFoi = when (true) {
                dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.S.name) -> Bonus.S
                dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.A.name) -> Bonus.A
                dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.B.name) -> Bonus.B
                dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.C.name) -> Bonus.C
                dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.D.name) -> Bonus.D
                dialog.findViewById<Spinner>(R.id.weapon_bonus_foi_spinner).selectedItem.toString().equals(Bonus.E.name) -> Bonus.E
                else -> Bonus.NOTHING
            }

            if (dialog.findViewById<EditText>(R.id.weapon_weight_txt).text.toString().isNotEmpty()) {
                weapon.weight = dialog.findViewById<EditText>(R.id.weapon_weight_txt).text.toString().toFloat()
            } else {
                weapon.weight = 0F
            }
            toDoSave()
            dialog.dismiss()
        }

        dialog.show()
    }

fun removeEquipment(context: Context, toDoDelete: () -> Unit){
    val builder = AlertDialog.Builder(context)
    with(builder)
    {
        setTitle(context.getString(R.string.warning))
        setMessage(context.getString(R.string.confirm_delete))
        setNegativeButton(context.getString(R.string.no)) { dialog, which ->
            dialog.dismiss()
        }
        setPositiveButton(context.getString(R.string.ok)) { dialog, which ->
            toDoDelete()
            dialog.dismiss()
        }
        show()
    }
}

    fun fillWeaponEdit(dialog: Dialog, weapon: Weapon?) {
        if (!weapon!!.name.isNullOrEmpty()) {
            dialog.findViewById<EditText>(R.id.weapon_name_txt).setText(weapon.name)
            dialog.findViewById<EditText>(R.id.weapon_damage_txt).setText(weapon.damage.toString())
            dialog.findViewById<EditText>(R.id.weapon_boost_txt).setText(weapon.boost.toString())
            dialog.findViewById<EditText>(R.id.weapon_status_proc_txt).setText(weapon.statusValue.toString())
            if (weapon.rapidFire) dialog.findViewById<CheckBox>(R.id.weapon_rapidfire).isChecked = true
            when (weapon.status) {
                Status.BLEED -> dialog.findViewById<CheckBox>(R.id.weapon_status_bleed).isChecked = true
                Status.POISON -> dialog.findViewById<CheckBox>(R.id.weapon_status_poison).isChecked = true
                Status.FROST -> dialog.findViewById<CheckBox>(R.id.weapon_status_frost).isChecked = true
            }
            when (weapon.affinity) {
                Elem.MAGIC -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_magic).isChecked = true
                Elem.FIRE -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_fire).isChecked = true
                Elem.DARKNESS -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_dark).isChecked = true
                Elem.LIGHTNING -> dialog.findViewById<CheckBox>(R.id.weapon_affinity_light).isChecked = true
            }
            when (weapon.bonusFor) {
                Bonus.S -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(1)
                Bonus.A -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(2)
                Bonus.B -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(3)
                Bonus.C -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(4)
                Bonus.D -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(5)
                Bonus.E -> dialog.findViewById<Spinner>(R.id.weapon_bonus_for_spinner).setSelection(6)
            }
            when (weapon.bonusDex) {
                Bonus.S -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(1)
                Bonus.A -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(2)
                Bonus.B -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(3)
                Bonus.C -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(4)
                Bonus.D -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(5)
                Bonus.E -> dialog.findViewById<Spinner>(R.id.weapon_bonus_dex_spinner).setSelection(6)
            }
            when (weapon.bonusInt) {
                Bonus.S -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(1)
                Bonus.A -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(2)
                Bonus.B -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(3)
                Bonus.C -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(4)
                Bonus.D -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(5)
                Bonus.E -> dialog.findViewById<Spinner>(R.id.weapon_bonus_int_spinner).setSelection(6)
            }
            when (weapon.bonusFoi) {
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

    @JvmStatic
    fun openShieldDialog(shield: Shield, context: Context, activity: Activity, toDoEquip: () -> Unit,  toDoDelete: () -> Unit,  toDoSave: () -> Unit) {
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_edit_shield)
            dialog.findViewById<TextView>(R.id.shield_type).text = context.getString(R.string.shield)
            fillShieldEdit(dialog, shield)
            if(!shield.equip) dialog.findViewById<TextView>(R.id.shield_disequip_button).text = context.getString(R.string.equiper)
            dialog.findViewById<EditText>(R.id.shield_name_txt).setSelection(dialog.findViewById<EditText>(R.id.shield_name_txt).text.length)
            dialog.findViewById<ImageView>(R.id.shield_cancel_button).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<TextView>(R.id.shield_disequip_button).setOnClickListener {
                toDoEquip()
                dialog.dismiss()
            }
            dialog.findViewById<TextView>(R.id.shield_delete_button).setOnClickListener {
                removeEquipment(context, toDoDelete)
                dialog.dismiss()
            }
            dialog.findViewById<TextView>(R.id.shield_save_button).setOnClickListener {
                shield.name = dialog.findViewById<EditText>(R.id.shield_name_txt).text.toString()
                if (dialog.findViewById<EditText>(R.id.shield_block_txt).text.toString().isNotEmpty()) {
                    shield.block = dialog.findViewById<EditText>(R.id.shield_block_txt).text.toString().toFloat()
                } else {
                    shield.block = 0F
                }

                shield.res?.let { shield.res.clear() }
                        ?: run { shield.res = ArrayList<Elem>() }
                if (dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked && dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked
                        && dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked && dialog.findViewById<CheckBox>(R.id.shield_res_magic).isChecked) {
                    shield.res.add(Elem.ALL)
                } else {
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked) {
                        shield.res.add(Elem.FIRE)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked) {
                        shield.res.add(Elem.DARKNESS)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked) {
                        shield.res.add(Elem.LIGHTNING)
                    }
                    if (dialog.findViewById<CheckBox>(R.id.shield_res_magic).isChecked) {
                        shield.res.add(Elem.MAGIC)
                    }
                }

                if (dialog.findViewById<EditText>(R.id.shield_weight_txt).text.toString().isNotEmpty()) {
                    shield.weight = dialog.findViewById<EditText>(R.id.shield_weight_txt).text.toString().toFloat()
                } else {
                    shield.weight = 0F
                }
                toDoSave()
                dialog.dismiss()
            }

            dialog.show()
    }

    fun fillShieldEdit(dialog: Dialog, shield: Shield) {
        shield?.let {
            dialog.findViewById<EditText>(R.id.shield_name_txt).setText(shield.name)
            dialog.findViewById<EditText>(R.id.shield_block_txt).setText(shield.block.toString())
            shield.res?.let {
                if (shield.res.contains(Elem.FIRE)) dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked = true
                if (shield.res.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked = true
                if (shield.res.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked = true
                if (shield.res.contains(Elem.MAGIC)) dialog.findViewById<CheckBox>(R.id.shield_res_magic).isChecked = true
                if (shield.res.contains(Elem.ALL)) {
                    dialog.findViewById<CheckBox>(R.id.shield_res_fire).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.shield_res_dark).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked = true
                    dialog.findViewById<CheckBox>(R.id.shield_res_light).isChecked = true
                }
            }
            dialog.findViewById<EditText>(R.id.shield_weight_txt).setText(shield.weight.toString())
        }
    }

    @JvmStatic
    fun openArmorDialog(type: String, armor: Armor, context: Context, activity: Activity, toDoEquip: () -> Unit,  toDoDelete: () -> Unit,  toDoSave: () -> Unit) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_edit_armor)
        dialog.findViewById<TextView>(R.id.armor_type).text = type
        fillArmorEdit(dialog, armor)
        if(!type.equals(context.getString(R.string.armor))) dialog.findViewById<LinearLayout>(R.id.armor_type_layout).visibility = View.GONE
        if(!armor.equip) dialog.findViewById<TextView>(R.id.armor_disequip_button).text = context.getString(R.string.equiper)
        dialog.findViewById<EditText>(R.id.armor_name_txt).setSelection(dialog.findViewById<EditText>(R.id.armor_name_txt).text.length)
        dialog.findViewById<ImageView>(R.id.armor_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.armor_disequip_button).setOnClickListener {
            toDoEquip()
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.armor_delete_button).setOnClickListener {
            removeEquipment(context, toDoDelete)
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.armor_save_button).setOnClickListener {
            if(type.equals(context.getString(R.string.armor)) && (dialog.findViewById<MultiRowsRadioGroup>(R.id.armor_type_radiogroup).checkedRadioButtonId ==-1)){
                Snackbar.make(dialog.currentFocus!!, context.getString(R.string.warning_armor_type), Snackbar.LENGTH_LONG).show()
            }else {
                armor.name = dialog.findViewById<EditText>(R.id.armor_name_txt).text.toString()

                if (type.equals(context.getString(R.string.armor))) {
                    when (true) {
                        dialog.findViewById<RadioButton>(R.id.armor_type_hat).isChecked -> armor.type = PieceEquipment.HAT
                        dialog.findViewById<RadioButton>(R.id.armor_type_chest).isChecked -> armor.type = PieceEquipment.CHEST
                        dialog.findViewById<RadioButton>(R.id.armor_type_gloves).isChecked -> armor.type = PieceEquipment.GLOVES
                        dialog.findViewById<RadioButton>(R.id.armor_type_greaves).isChecked -> armor.type = PieceEquipment.GREAVES
                    }
                }

                if (dialog.findViewById<EditText>(R.id.armor_def_txt).text.toString().isNotEmpty()) {
                    armor.def = dialog.findViewById<EditText>(R.id.armor_def_txt).text.toString().toFloat()
                } else {
                    armor.def = 0F
                }

                armor.immun?.let { armor.immun.clear() }
                        ?: run { armor.immun = ArrayList<Status>() }
                if (dialog.findViewById<CheckBox>(R.id.armor_immun_frost).isChecked) {
                    armor.immun.add(Status.FROST)
                }
                if (dialog.findViewById<CheckBox>(R.id.armor_immun_poison).isChecked) {
                    armor.immun.add(Status.POISON)
                }
                if (dialog.findViewById<CheckBox>(R.id.armor_immun_bleed).isChecked) {
                    armor.immun.add(Status.BLEED)
                }

                armor.res?.let { armor.res.clear() } ?: run { armor.res = ArrayList<Elem>() }
                if (dialog.findViewById<CheckBox>(R.id.armor_res_fire).isChecked && dialog.findViewById<CheckBox>(R.id.armor_res_dark).isChecked
                        && dialog.findViewById<CheckBox>(R.id.armor_res_light).isChecked && dialog.findViewById<CheckBox>(R.id.armor_res_magic).isChecked) {
                    armor.res.add(Elem.ALL)
                } else {
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

                armor.weak?.let { armor.weak.clear() } ?: run { armor.weak = ArrayList<Elem>() }
                if (dialog.findViewById<CheckBox>(R.id.armor_weak_fire).isChecked && dialog.findViewById<CheckBox>(R.id.armor_weak_dark).isChecked
                        && dialog.findViewById<CheckBox>(R.id.armor_weak_light).isChecked && dialog.findViewById<CheckBox>(R.id.armor_weak_magic).isChecked) {
                    armor.weak.add(Elem.ALL)
                } else {
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

                if (dialog.findViewById<EditText>(R.id.armor_weight_txt).text.toString().isNotEmpty()) {
                    armor.weight = dialog.findViewById<EditText>(R.id.armor_weight_txt).text.toString().toFloat()
                } else {
                    armor.weight = 0F
                }
                toDoSave()
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    fun fillArmorEdit(dialog: Dialog, armor: Armor?) {
        if (!armor!!.name.isNullOrEmpty()) {
            dialog.findViewById<EditText>(R.id.armor_name_txt).setText(armor.name)

            when(armor.type){
                PieceEquipment.HAT -> dialog.findViewById<RadioButton>(R.id.armor_type_hat).isChecked = true
                PieceEquipment.CHEST -> dialog.findViewById<RadioButton>(R.id.armor_type_chest).isChecked = true
                PieceEquipment.GLOVES -> dialog.findViewById<RadioButton>(R.id.armor_type_gloves).isChecked = true
                PieceEquipment.GREAVES -> dialog.findViewById<RadioButton>(R.id.armor_type_greaves).isChecked = true
            }

            dialog.findViewById<EditText>(R.id.armor_def_txt).setText(armor.def.toString())

            if (armor.immun.contains(Status.BLEED)) dialog.findViewById<CheckBox>(R.id.armor_immun_bleed).isChecked = true
            if (armor.immun.contains(Status.POISON)) dialog.findViewById<CheckBox>(R.id.armor_immun_poison).isChecked = true
            if (armor.immun.contains(Status.FROST)) dialog.findViewById<CheckBox>(R.id.armor_immun_frost).isChecked = true


            if (armor.res.contains(Elem.MAGIC)) dialog.findViewById<CheckBox>(R.id.armor_res_magic).isChecked = true
            if (armor.res.contains(Elem.FIRE)) dialog.findViewById<CheckBox>(R.id.armor_res_fire).isChecked = true
            if (armor.res.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.armor_res_dark).isChecked = true
            if (armor.res.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.armor_res_light).isChecked = true
            if (armor.res.contains(Elem.ALL)) {
                dialog.findViewById<CheckBox>(R.id.armor_res_magic).isChecked = true
                dialog.findViewById<CheckBox>(R.id.armor_res_fire).isChecked = true
                dialog.findViewById<CheckBox>(R.id.armor_res_dark).isChecked = true
                dialog.findViewById<CheckBox>(R.id.armor_res_light).isChecked = true
            }

            if (armor.weak.contains(Elem.MAGIC)) dialog.findViewById<CheckBox>(R.id.armor_weak_magic).isChecked = true
            if (armor.weak.contains(Elem.FIRE)) dialog.findViewById<CheckBox>(R.id.armor_weak_fire).isChecked = true
            if (armor.weak.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.armor_weak_dark).isChecked = true
            if (armor.weak.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.armor_weak_light).isChecked = true
            if (armor.weak.contains(Elem.ALL)) {
                dialog.findViewById<CheckBox>(R.id.armor_weak_magic).isChecked = true
                dialog.findViewById<CheckBox>(R.id.armor_weak_fire).isChecked = true
                dialog.findViewById<CheckBox>(R.id.armor_weak_dark).isChecked = true
                dialog.findViewById<CheckBox>(R.id.armor_weak_light).isChecked = true
            }

            dialog.findViewById<EditText>(R.id.armor_weight_txt).setText(armor.weight.toString())
        }
    }
}