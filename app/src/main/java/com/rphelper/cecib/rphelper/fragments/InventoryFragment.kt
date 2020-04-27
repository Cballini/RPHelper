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
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_CONST_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DAMAGES
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DEFENSE
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_LIFE_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_MANA_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_WEIGHT_MAX
import com.rphelper.cecib.rphelper.Preferences.PRIVATE_MODE
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.ItemAdapter
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.dto.Item
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
            if(it>15){
                view.findViewById<TextView>(R.id.inventory_weight).setTextColor(resources.getColor(R.color.red))
            }else{
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
            addItem()
        }

        return view
    }

    fun addItem() {
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
            item.equip = dialog.findViewById<CheckBox>(R.id.item_equip).isChecked
            if (item.equip) equipItem(item, true)
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
            item.equip = dialog.findViewById<CheckBox>(R.id.item_equip).isChecked

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

    fun equipItem(item: Item, isAdd :Boolean) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_equip_item)

        if (!isAdd) {
            item.equip = !item.equip
            viewModel.editInventory()
        }

        if (item.equip) {
            dialog.findViewById<TextView>(R.id.equip_item_title).text = getString(R.string.equiper)
            dialog.findViewById<TextView>(R.id.equip_item_ask).text = getString(R.string.ask_item_equip)
        } else {
            dialog.findViewById<TextView>(R.id.equip_item_title).text = getString(R.string.disequip)
            dialog.findViewById<TextView>(R.id.equip_item_ask).text = getString(R.string.ask_item_disequip)
        }

        dialog.findViewById<ImageView>(R.id.equip_item_cancel_button).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.equip_item_save_button).setOnClickListener {
            if (dialog.findViewById<CheckBox>(R.id.equip_item_checkbox_pv_max).isChecked) {
                val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_MODIFIER_LIFE_MAX, PRIVATE_MODE)
                val prefValue = sharedPref.getInt(PREF_MODIFIER_LIFE_MAX, 0)
                val editor = sharedPref.edit()
                var value = 0
                if (dialog.findViewById<EditText>(R.id.equip_item_edit_pv_max).text.isNotBlank()) value = dialog.findViewById<EditText>(R.id.equip_item_edit_pv_max).text.toString().toInt()
                if (item.equip) editor.putInt(PREF_MODIFIER_LIFE_MAX, prefValue+value) else editor.putInt(PREF_MODIFIER_LIFE_MAX, prefValue-value)
                editor.apply()
            }
            if (dialog.findViewById<CheckBox>(R.id.equip_item_checkbox_cons_max).isChecked) {
                val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_MODIFIER_CONST_MAX, PRIVATE_MODE)
                val prefValue = sharedPref.getInt(PREF_MODIFIER_CONST_MAX, 0)
                val editor = sharedPref.edit()
                var value = 0
                if (dialog.findViewById<EditText>(R.id.equip_item_edit_const_max).text.isNotBlank()) value = dialog.findViewById<EditText>(R.id.equip_item_edit_const_max).text.toString().toInt()
                if (item.equip) editor.putInt(PREF_MODIFIER_CONST_MAX, prefValue+value) else editor.putInt(PREF_MODIFIER_CONST_MAX, prefValue-value)
                editor.apply()
            }
            if (dialog.findViewById<CheckBox>(R.id.equip_item_checkbox_mana_max).isChecked) {
                val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_MODIFIER_MANA_MAX, PRIVATE_MODE)
                val prefValue = sharedPref.getInt(PREF_MODIFIER_MANA_MAX, 0)
                val editor = sharedPref.edit()
                var value = 0
                if (dialog.findViewById<EditText>(R.id.equip_item_edit_mana_max).text.isNotBlank()) value = dialog.findViewById<EditText>(R.id.equip_item_edit_mana_max).text.toString().toInt()
                if (item.equip) editor.putInt(PREF_MODIFIER_MANA_MAX, prefValue+value) else editor.putInt(PREF_MODIFIER_MANA_MAX, prefValue-value)
                editor.apply()
            }
            if (dialog.findViewById<CheckBox>(R.id.equip_item_checkbox_weight_max).isChecked) {
                val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_MODIFIER_WEIGHT_MAX, PRIVATE_MODE)
                val prefValue = sharedPref.getInt(PREF_MODIFIER_WEIGHT_MAX, 0)
                val editor = sharedPref.edit()
                var value = 0
                if (dialog.findViewById<EditText>(R.id.equip_item_edit_weight_max).text.isNotBlank()) value = dialog.findViewById<EditText>(R.id.equip_item_edit_weight_max).text.toString().toInt()
                if (item.equip) editor.putInt(PREF_MODIFIER_WEIGHT_MAX, prefValue+value) else editor.putInt(PREF_MODIFIER_WEIGHT_MAX, prefValue-value)
                editor.apply()
            }
            if (dialog.findViewById<CheckBox>(R.id.equip_item_checkbox_damages).isChecked) {
                val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_MODIFIER_DAMAGES, PRIVATE_MODE)
                val prefValue = sharedPref.getInt(PREF_MODIFIER_DAMAGES, 0)
                val editor = sharedPref.edit()
                var value = 0
                if (dialog.findViewById<EditText>(R.id.equip_item_edit_damages).text.isNotBlank()) value = dialog.findViewById<EditText>(R.id.equip_item_edit_damages).text.toString().toInt()
                if (item.equip) editor.putInt(PREF_MODIFIER_DAMAGES, prefValue+value) else editor.putInt(PREF_MODIFIER_DAMAGES, prefValue-value)
                editor.apply()
            }
            if (dialog.findViewById<CheckBox>(R.id.equip_item_checkbox_defense).isChecked) {
                val sharedPref: SharedPreferences = context!!.getSharedPreferences(PREF_MODIFIER_DEFENSE, PRIVATE_MODE)
                val prefValue = sharedPref.getInt(PREF_MODIFIER_DEFENSE, 0)
                val editor = sharedPref.edit()
                var value = 0
                if (dialog.findViewById<EditText>(R.id.equip_item_edit_defense).text.isNotBlank()) value = dialog.findViewById<EditText>(R.id.equip_item_edit_defense).text.toString().toInt()
                if (item.equip) editor.putInt(PREF_MODIFIER_DEFENSE, prefValue+value) else editor.putInt(PREF_MODIFIER_DEFENSE, prefValue-value)
                editor.apply()
            }

            dialog.dismiss()
        }
        dialog.show()
    }

    fun fillEditItem(dialog: Dialog, item: Item) {
        dialog.findViewById<EditText>(R.id.item_name_txt).setText(item.name)
        dialog.findViewById<EditText>(R.id.item_weight_txt).setText(item.weight.toString())
        dialog.findViewById<EditText>(R.id.item_quantity_txt).setText(item.quantity.toString())
        dialog.findViewById<CheckBox>(R.id.item_equip).isChecked = item.equip
        dialog.findViewById<EditText>(R.id.item_effect_txt).setText(item.effect)
    }

    override fun onItemClicked(position: Int, v: View) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.menuInflater.inflate(R.menu.menu_item, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> editItem(viewModel.items.value!![position])
                R.id.action_delete -> {
                    if (viewModel.items.value!![position].equip) equipItem(viewModel.items.value!![position], false) //disequip before remove
                    viewModel.items.value!!.remove(viewModel.items.value!![position])
                    viewModel.editInventory()
                }
                R.id.action_equip -> {
                    equipItem(viewModel.items.value!![position], false)
                }
            }
            true
        })
        popupMenu.show()
    }
}
