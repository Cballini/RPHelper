package com.rphelper.cecib.rphelper.fragments


import android.app.Dialog
import android.arch.lifecycle.Observer
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_CONST_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DAMAGES
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DEFENSE
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DEX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_END
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_FOI
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_FOR
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_INT
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_LIFE_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_MANA_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_MEM
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_VIG
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_VIT
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_WEIGHT_MAX
import com.rphelper.cecib.rphelper.Preferences.PRIVATE_MODE
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.ItemAdapter
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.utils.DisplayUtils
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener
import com.rphelper.cecib.rphelper.viewmodel.InventoryViewModel
import java.util.*


class InventoryFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: InventoryViewModel
    private var isOnMoneyEdit = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)

        viewModel = InventoryViewModel(context!!)

        //Money
        view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTitle.text = getString(R.string.money)
        view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.setEnabled(false)
        viewModel.money.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.setText(it!!.toString())
        })

        //Objects
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ItemAdapter(ArrayList(viewModel.items.value!!), this)
        recyclerView = view.findViewById<RecyclerView>(R.id.inventory_recycler).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        viewModel.items.observe(viewLifecycleOwner, Observer {
            viewAdapter = ItemAdapter(ArrayList(it!!), this)
            recyclerView = view.findViewById<RecyclerView>(R.id.inventory_recycler).apply {
                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }
        })

        //Weight
        viewModel.weight.observe(viewLifecycleOwner, Observer {
            val weight = it!!.toString() + getString(R.string.inventory_max_weight)
            if (it > 15) {
                view.findViewById<TextView>(R.id.inventory_weight).setTextColor(resources.getColor(R.color.red))
            } else {
                view.findViewById<TextView>(R.id.inventory_weight).setTextColor(resources.getColor(R.color.colorTxt))
            }
            view.findViewById<TextView>(R.id.inventory_weight).text = weight
        })

        /***** EDIT ********/
        view.findViewById<ImageView>(R.id.inventory_money_edit).setOnClickListener {
            if (isOnMoneyEdit) {
                isOnMoneyEdit = false
                view.findViewById<ImageView>(R.id.inventory_money_edit).setImageResource(R.drawable.ic_edit)
                view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.setEnabled(false)
                if (view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.text.toString().isNotEmpty()) viewModel._money.value = view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.text.toString().toInt()
                viewModel.editInventory()
            } else {
                isOnMoneyEdit = true
                view.findViewById<ImageView>(R.id.inventory_money_edit).setImageResource(R.drawable.ic_check)
                view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.setEnabled(true)
                view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
        view.findViewById<ImageView>(R.id.inventory_add).setOnClickListener {
            addStuff()
        }

        return view
    }

    fun addStuff() {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_choose_type_item)
        dialog.findViewById<ImageView>(R.id.choose_type_cancel_button).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.choose_type_save_button).setOnClickListener {
            when (true) {
                dialog.findViewById<RadioButton>(R.id.choose_type_button_weapon).isChecked -> editWeapon(Weapon())
                dialog.findViewById<RadioButton>(R.id.choose_type_button_shield).isChecked -> editShield(Shield())
                dialog.findViewById<RadioButton>(R.id.choose_type_button_armor).isChecked -> editArmor(Armor())
                dialog.findViewById<RadioButton>(R.id.choose_type_button_jewel).isChecked -> editJewel(Jewel())
                dialog.findViewById<RadioButton>(R.id.choose_type_button_item).isChecked -> addItem()
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    fun editWeapon(weapon: Weapon) {
        DisplayUtils.openWeaponDialog(getString(R.string.weapon), weapon, context!!, activity!!,
                {/*TODO equip*/ },
                {/*no delete*/ },
                {
                    viewModel.items.value!!.add(weapon)
                    viewModel.editInventory()
                })
    }

    fun editShield(shield: Shield) {
        DisplayUtils.openShieldDialog(shield, context!!, activity!!,
                {/*TODO equip*/ },
                {/*no delete*/ },
                {
                    viewModel.items.value!!.add(shield)
                    viewModel.editInventory()
                })
    }

    fun editArmor(armor: Armor) {
        DisplayUtils.openArmorDialog(getString(R.string.armor), armor, context!!, activity!!,
                {/*TODO equip*/ },
                {/*no delete*/ },
                {
                    viewModel.items.value!!.add(armor)
                    viewModel.editInventory()
                })
    }

    fun editJewel(jewel: Jewel) {
        openJewelDialog(jewel,
                ({
                    viewModel.items.value!!.add(jewel)
                    viewModel.editInventory()
                }))
    }

    fun openJewelDialog(jewel: Jewel, toDoSave: () -> Unit) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_edit_jewel)
        dialog.findViewById<TextView>(R.id.jewel_ask).text = getString(R.string.ask_jewel)

        fillJewel(dialog, jewel)
        //TODO init spinners + immun/weak/res

        dialog.findViewById<ImageView>(R.id.jewel_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.jewel_save_button).setOnClickListener {
            jewel.name = dialog.findViewById<TextView>(R.id.jewel_name_txt).text.toString()
            jewel.weight = if (dialog.findViewById<EditText>(R.id.jewel_weight_txt).text.toString().isNotEmpty()) dialog.findViewById<EditText>(R.id.jewel_weight_txt).text.toString().toFloat() else 0F
            jewel.equip = dialog.findViewById<CheckBox>(R.id.jewel_equip).isChecked
            jewel.desc = if (dialog.findViewById<EditText>(R.id.jewel_desc_txt).text.toString().isNotEmpty()) dialog.findViewById<EditText>(R.id.jewel_desc_txt).text.toString() else ""

            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_pv_max).isChecked) {
                jewel.maxLifeModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_pv_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_pv_max).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_LIFE_MAX, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_cons_max).isChecked) {
                jewel.maxConstModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_const_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_const_max).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_CONST_MAX, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_mana_max).isChecked) {
                jewel.maxManaModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_mana_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_mana_max).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_MANA_MAX, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_weight_max).isChecked) {
                jewel.maxWeightModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_weight_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_weight_max).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_WEIGHT_MAX, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_damages).isChecked) {
                jewel.damageModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_damages).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_damages).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_DAMAGES, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_defense).isChecked) {
                jewel.defModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_defense).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_defense).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_DEFENSE, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_vit).isChecked) {
                jewel.vitModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_vit).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_vit).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_VIT, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_vig).isChecked) {
                jewel.vigModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_vig).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_vig).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_VIG, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_for).isChecked) {
                jewel.forModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_for).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_for).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_FOR, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_dex).isChecked) {
                jewel.dexModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_dex).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_dex).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_DEX, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_end).isChecked) {
                jewel.endModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_end).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_end).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_END, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_mem).isChecked) {
                jewel.memModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_mem).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_mem).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_MEM, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_int).isChecked) {
                jewel.intModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_int).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_int).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_INT, jewel)
            }
            if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_foi).isChecked) {
                jewel.foiModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_foi).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_foi).text.toString().toInt() else 0
                if (jewel.equip) modifPref(PREF_MODIFIER_FOI, jewel)
            }

            toDoSave()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun fillJewel(dialog: Dialog, jewel: Jewel){
        dialog.findViewById<TextView>(R.id.jewel_name_txt).text = jewel.name
        dialog.findViewById<EditText>(R.id.jewel_weight_txt).setText(jewel.weight.toString())
        dialog.findViewById<CheckBox>(R.id.jewel_equip).isChecked = jewel.equip
        dialog.findViewById<EditText>(R.id.jewel_desc_txt).setText(jewel.desc)

        if(jewel.maxLifeModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_pv_max).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_pv_max).setText(jewel.maxLifeModifier.toString())
        }
        if(jewel.maxConstModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_cons_max).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_const_max).setText(jewel.maxConstModifier.toString())
        }
        if(jewel.maxManaModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_mana_max).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_mana_max).setText(jewel.maxManaModifier.toString())
        }
        if(jewel.maxWeightModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_weight_max).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_weight_max).setText(jewel.maxWeightModifier.toString())
        }
        if(jewel.damageModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_damages).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_damages).setText(jewel.damageModifier.toString())
        }
        if(jewel.defModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_defense).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_defense).setText(jewel.defModifier.toString())
        }
        if(jewel.vitModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_vit).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_vit).setText(jewel.vitModifier.toString())
        }
        if(jewel.vigModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_vig).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_vig).setText(jewel.vigModifier.toString())
        }
        if(jewel.forModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_for).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_for).setText(jewel.forModifier.toString())
        }
        if(jewel.dexModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_dex).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_dex).setText(jewel.dexModifier.toString())
        }
        if(jewel.endModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_end).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_end).setText(jewel.endModifier.toString())
        }
        if(jewel.memModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_mem).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_mem).setText(jewel.memModifier.toString())
        }
        if(jewel.intModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_int).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_int).setText(jewel.intModifier.toString())
        }
        if(jewel.foiModifier != 0) {
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_foi).isChecked = true
            dialog.findViewById<EditText>(R.id.jewel_edit_foi).setText(jewel.foiModifier.toString())
        }

        //TODO res/immu/fai
    }

    fun addItem() {
        //TODO refacto edit/add
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_edit_item)
        dialog.findViewById<ImageView>(R.id.item_cancel_button).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.item_save_button).setOnClickListener {
            var item = Item()
            item.name = dialog.findViewById<EditText>(R.id.item_name_txt).text.toString()
            if (dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().isNotEmpty()) {
                item.weight = dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().toFloat()
            } else {
                item.weight = 0F
            }
            if (dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().isNotEmpty()) {
                item.quantity = dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().toInt()
            } else {
                item.quantity = 0
            }

            if (dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString().isNotEmpty()) {
                item.effect = dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString()
            } else {
                item.effect = ""
            }
            viewModel.items.value!!.add(item)
            viewModel.editInventory()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun editItem(item: Item) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_edit_item)
        fillEditItem(dialog, item)
        dialog.findViewById<EditText>(R.id.item_name_txt).setSelection(dialog.findViewById<EditText>(R.id.item_name_txt).text.length)
        dialog.findViewById<ImageView>(R.id.item_cancel_button).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.item_save_button).setOnClickListener {
            item.name = dialog.findViewById<EditText>(R.id.item_name_txt).text.toString()
            if (dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().isNotEmpty()) {
                item.weight = dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().toFloat()
            } else {
                item.weight = 0F
            }
            if (dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().isNotEmpty()) {
                item.quantity = dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().toInt()
            } else {
                item.quantity = 0
            }

            if (dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString().isNotEmpty()) {
                item.effect = dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString()
            } else {
                item.effect = ""
            }
            viewModel.editInventory()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun equipJewel(jewel: Jewel) {

        jewel.equip = !jewel.equip
        viewModel.editInventory()
        modifPref(PREF_MODIFIER_LIFE_MAX, jewel)
        modifPref(PREF_MODIFIER_CONST_MAX, jewel)
        modifPref(PREF_MODIFIER_MANA_MAX, jewel)
        modifPref(PREF_MODIFIER_WEIGHT_MAX, jewel)
        modifPref(PREF_MODIFIER_DAMAGES, jewel)
        modifPref(PREF_MODIFIER_DEFENSE, jewel)
        modifPref(PREF_MODIFIER_VIT, jewel)
        modifPref(PREF_MODIFIER_VIG, jewel)
        modifPref(PREF_MODIFIER_FOR, jewel)
        modifPref(PREF_MODIFIER_DEX, jewel)
        modifPref(PREF_MODIFIER_END, jewel)
        modifPref(PREF_MODIFIER_MEM, jewel)
        modifPref(PREF_MODIFIER_INT, jewel)
        modifPref(PREF_MODIFIER_FOI, jewel)
    }

    fun modifPref(pref: String, jewel: Jewel) {
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, PRIVATE_MODE)
        val prefValue = sharedPref.getInt(pref, 0)
        val editor = sharedPref.edit()
        var value = 0
        when (pref) {
            PREF_MODIFIER_LIFE_MAX -> value = jewel.maxLifeModifier
            PREF_MODIFIER_MANA_MAX -> value = jewel.maxManaModifier
            PREF_MODIFIER_CONST_MAX -> value = jewel.maxConstModifier
            PREF_MODIFIER_WEIGHT_MAX -> value = jewel.maxWeightModifier
            PREF_MODIFIER_DAMAGES -> value = jewel.damageModifier
            PREF_MODIFIER_DEFENSE -> value = jewel.defModifier
            PREF_MODIFIER_VIT -> value = jewel.vitModifier
            PREF_MODIFIER_VIG -> value = jewel.vigModifier
            PREF_MODIFIER_FOR -> value = jewel.forModifier
            PREF_MODIFIER_DEX -> value = jewel.dexModifier
            PREF_MODIFIER_END -> value = jewel.endModifier
            PREF_MODIFIER_MEM -> value = jewel.memModifier
            PREF_MODIFIER_INT -> value = jewel.intModifier
            PREF_MODIFIER_FOI -> value = jewel.foiModifier
        }
        if (jewel.equip) editor.putInt(pref, prefValue + value) else editor.putInt(pref, prefValue - value)
        editor.apply()
    }

    fun fillEditItem(dialog: Dialog, item: Item) {
        dialog.findViewById<EditText>(R.id.item_name_txt).setText(item.name)
        dialog.findViewById<EditText>(R.id.item_weight_txt).setText(item.weight.toString())
        dialog.findViewById<EditText>(R.id.item_quantity_txt).setText(item.quantity.toString())
        dialog.findViewById<EditText>(R.id.item_effect_txt).setText(item.effect)
    }

    override fun onItemClicked(position: Int, v: View) {
        val popupMenu = PopupMenu(context, v)

        popupMenu.menuInflater.inflate(R.menu.menu_item, popupMenu.menu)
        if (viewModel.items.value!![position] !is Jewel) popupMenu.menu.findItem(R.id.action_equip).isVisible = false
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> {
                    if (viewModel.items.value!![position] is Weapon) editWeapon(viewModel.items.value!![position] as Weapon)
                    if (viewModel.items.value!![position] is Shield) editShield(viewModel.items.value!![position] as Shield)
                    if (viewModel.items.value!![position] is Armor) editArmor(viewModel.items.value!![position] as Armor)
                    if (viewModel.items.value!![position] is Jewel) editJewel(viewModel.items.value!![position] as Jewel)
                    if (viewModel.items.value!![position] is Item) editItem((viewModel.items.value!![position] as Item))
                }
                R.id.action_delete -> {
                    if (viewModel.items.value!![position] is Jewel) {
                        if ((viewModel.items.value!![position] as Jewel).equip) equipJewel((viewModel.items.value!![position] as Jewel)) //disequip before remove
                    }
                    viewModel.items.value!!.remove(viewModel.items.value!![position])
                    viewModel.editInventory()
                }
                R.id.action_equip -> {
                    equipJewel((viewModel.items.value!![position] as Jewel))
                }
            }
            true
        })
        popupMenu.show()
    }
}
