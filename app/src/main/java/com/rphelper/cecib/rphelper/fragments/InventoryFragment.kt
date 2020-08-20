package com.rphelper.cecib.rphelper.fragments


import android.app.AlertDialog
import android.app.Dialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_CONST_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DAMAGES
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DEFENSE
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_DEX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_END
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_FOI
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_FOR
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_IMMUN
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_INT
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_LIFE_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_MANA_MAX
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_MEM
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_RES
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_VIG
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_VIT
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_WEAK
import com.rphelper.cecib.rphelper.Preferences.PREF_MODIFIER_WEIGHT_MAX
import com.rphelper.cecib.rphelper.Preferences.PRIVATE_MODE
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.ItemAdapter
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.component.RecapEquipmentComponent
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.Elem
import com.rphelper.cecib.rphelper.enums.PieceEquipment
import com.rphelper.cecib.rphelper.enums.Status
import com.rphelper.cecib.rphelper.utils.CalcUtils
import com.rphelper.cecib.rphelper.utils.DisplayUtils
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener
import com.rphelper.cecib.rphelper.viewmodel.InventoryViewModel
import org.json.JSONObject
import java.util.*


class InventoryFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: InventoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)

        viewModel = InventoryViewModel(context!!)


        val liveData = viewModel.getDataSnapshotLiveData()
        liveData!!.observe(viewLifecycleOwner, Observer { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModel._inventory.value = dataSnapshot.child("inventory").getValue(Inventory::class.java)

                var allStuff = ArrayList<Any>()
                //Weapon
                var valueWeapon = ArrayList<HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("weapons").value?.let{
                    valueWeapon = dataSnapshot.child("inventory").child("weapons").value as ArrayList<HashMap<String, Any>>
                    for (_value in valueWeapon) {
                        // Convert HashMap to Weapon
                        val jsonWeapon = JSONObject(_value).toString()
                        val weapon = Gson().fromJson<Weapon>(jsonWeapon, Weapon::class.java)
                        allStuff.add(weapon)
                    }
                }

                //Shield
                var valueShield = ArrayList<HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("shields").value?.let{
                    valueShield = dataSnapshot.child("inventory").child("shields").value as ArrayList<HashMap<String, Any>>
                    for (_value in valueShield) {
                        // Convert HashMap to Shield
                        val jsonShield = JSONObject(_value).toString()
                        val shield = Gson().fromJson<Shield>(jsonShield, Shield::class.java)
                        allStuff.add(shield)
                    }
                }

                //Armor
                var valueArmor = ArrayList<HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("armors").value?.let{
                    valueArmor = dataSnapshot.child("inventory").child("armors").value as ArrayList<HashMap<String, Any>>
                    for (_value in valueArmor) {
                        // Convert HashMap to Armor
                        val jsonArmor = JSONObject(_value).toString()
                        val armor = Gson().fromJson<Armor>(jsonArmor, Armor::class.java)
                        allStuff.add(armor)
                    }
                }
                //Jewel
                var valueJewel = ArrayList<HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("jewels").value?.let{
                    valueJewel = dataSnapshot.child("inventory").child("jewels").value as ArrayList<HashMap<String, Any>>
                    for (_value in valueJewel) {
                        // Convert HashMap to Jewel
                        val jsonJewel = JSONObject(_value).toString()
                        val jewel = Gson().fromJson<Jewel>(jsonJewel, Jewel::class.java)
                        allStuff.add(jewel)
                    }
                }

                //Item
                var valueItem = ArrayList<HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("item").value?.let{
                    valueItem = dataSnapshot.child("inventory").child("item").value as ArrayList<HashMap<String, Any>>
                    for (_value in valueItem) {
                        // Convert HashMap to Item
                        val jsonItem = JSONObject(_value).toString()
                        val item = Gson().fromJson<Item>(jsonItem, Item::class.java)
                        allStuff.add(item)
                    }
                }

                viewModel._stuff.value = allStuff

                viewModel._money.value = viewModel._inventory.value!!.money
                viewModel._weight.value = viewModel.getInventoryWeight()
                viewModel._character.value = dataSnapshot.child("character").getValue(Character::class.java)
                viewModel._equipment.value = dataSnapshot.child("equipment").getValue(Equipment::class.java)
            }
        })

        //Money
        view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTitle.text = getString(R.string.money)
        view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.setEnabled(false)
        viewModel.money.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.inventory_money).catTxt.setText(it.toString())
        })

        //Objects
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ItemAdapter(viewModel.stuff.value!!, this)
        recyclerView = view.findViewById<RecyclerView>(R.id.inventory_recycler).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        viewModel.stuff.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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
        view.findViewById<TextView>(R.id.inventory_money_minus).setOnClickListener{
            val builder = AlertDialog.Builder(context)
            val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_indic, null)
            builder.setView(dialogLayout)
            builder.setTitle(getString(R.string.money_title))
            val txtView = dialogLayout.findViewById<TextView>(R.id.edit_indic_txt)
            txtView.text = getString(R.string.money_msg_minus)
            val editText = dialogLayout.findViewById<EditText>(R.id.edit_indic_value)

            builder.setPositiveButton(getString(R.string.ok)) { dialogInterface, i ->
                dialogInterface.dismiss()
                if(editText.text.isNotEmpty()) {
                    val money = viewModel.money.value!!
                    viewModel._money.value = money - (editText.text.toString().toInt())
                    viewModel.editInventory()
                }
            }
            builder.show()
        }
        view.findViewById<TextView>(R.id.inventory_money_plus).setOnClickListener{
            val builder = AlertDialog.Builder(context)
            val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_indic, null)
            builder.setView(dialogLayout)
            builder.setTitle(getString(R.string.money_title))
            val txtView = dialogLayout.findViewById<TextView>(R.id.edit_indic_txt)
            txtView.text = getString(R.string.money_msg_plus)
            val editText = dialogLayout.findViewById<EditText>(R.id.edit_indic_value)

            builder.setPositiveButton(getString(R.string.ok)) { dialogInterface, i ->
                dialogInterface.dismiss()
                if(editText.text.isNotEmpty()) {
                    val money = viewModel.money.value!!
                    viewModel._money.value = money + (editText.text.toString().toInt())
                    viewModel.editInventory()
                }
            }
            builder.show()
        }

        view.findViewById<FloatingActionButton>(R.id.inventory_add).setOnClickListener {
            addStuff()
        }

        view.findViewById<MaterialButton>(R.id.inventory_disequip_all).setOnClickListener{
            var newJewels = ArrayList<Jewel>()
            for(jewel in viewModel.getJewels()){
                if(jewel.equip){
                    jewel.equip = false
                    modifPrefString(PREF_MODIFIER_WEAK, jewel)
                    modifPrefString(PREF_MODIFIER_RES, jewel)
                    modifPrefString(PREF_MODIFIER_IMMUN, jewel)
                }
                newJewels.add(jewel)
            }
            viewModel.stuff.value!!.removeAll(viewModel.getJewels())
            viewModel.stuff.value!!.addAll(newJewels)
            viewModel.editInventory()
            CalcUtils.reinitStuffPref(context!!)
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
                dialog.findViewById<RadioButton>(R.id.choose_type_button_weapon).isChecked -> editWeapon(Weapon(), true)
                dialog.findViewById<RadioButton>(R.id.choose_type_button_shield).isChecked -> editShield(Shield(), true)
                dialog.findViewById<RadioButton>(R.id.choose_type_button_armor).isChecked -> editArmor(Armor(), true)
                dialog.findViewById<RadioButton>(R.id.choose_type_button_jewel).isChecked -> editJewel(Jewel(), true)
                dialog.findViewById<RadioButton>(R.id.choose_type_button_item).isChecked -> addItem()
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    fun editWeapon(weapon: Weapon, isAdd :Boolean) {
        DisplayUtils.openWeaponDialog(getString(R.string.weapon), weapon, context!!, activity!!,
                {if(weapon.boost>0)equipCata(weapon)
                else equipWeapon(weapon) },
                {if(viewModel.stuff.value!!.contains(weapon)){
                    viewModel.stuff.value!!.remove(weapon)
                    viewModel.editInventory()} },
                {weapon.equip = false
                    if(isAdd)viewModel.stuff.value!!.add(weapon)
                    viewModel.editInventory()
                })
    }

    fun editShield(shield: Shield, isAdd :Boolean) {
        DisplayUtils.openShieldDialog(shield, context!!, activity!!,
                {equipShield(shield) },
                {if(viewModel.stuff.value!!.contains(shield)){
                    viewModel.stuff.value!!.remove(shield)
                    viewModel.editInventory()}},
                {shield.equip = false
                    if(isAdd)viewModel.stuff.value!!.add(shield)
                    viewModel.editInventory()
                })
    }

    fun editArmor(armor: Armor, isAdd :Boolean) {
        DisplayUtils.openArmorDialog(getString(R.string.armor), armor, context!!, activity!!,
                {equipArmor(armor) },
                {if(viewModel.stuff.value!!.contains(armor)){
                    viewModel.stuff.value!!.remove(armor)
                    viewModel.editInventory()} },
                {armor.equip = false
                    if(isAdd)viewModel.stuff.value!!.add(armor)
                    viewModel.editInventory()
                })
    }

    fun editJewel(jewel: Jewel, isAdd :Boolean) {
        openJewelDialog(jewel,
                ({
                    if(isAdd)viewModel.stuff.value!!.add(jewel)
                    viewModel.editInventory()
                }))
    }

    fun openJewelDialog(jewel: Jewel, toDoSave: () -> Unit) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_edit_jewel)
        dialog.findViewById<TextView>(R.id.jewel_ask).text = getString(R.string.ask_jewel)

        fillJewel(dialog, jewel)
        val isAlreadyEquip = jewel.equip

        dialog.findViewById<CheckBox>(R.id.jewel_checkbox_weakness).setOnCheckedChangeListener { compoundButton, check ->
            if(check) dialog.findViewById<LinearLayout>(R.id.jewel_weakness_layout).visibility = View.VISIBLE
            else dialog.findViewById<LinearLayout>(R.id.jewel_weakness_layout).visibility =View.GONE
        }
        dialog.findViewById<CheckBox>(R.id.jewel_checkbox_res).setOnCheckedChangeListener { compoundButton, check ->
            if(check) dialog.findViewById<LinearLayout>(R.id.jewel_res_layout).visibility = View.VISIBLE
            else dialog.findViewById<LinearLayout>(R.id.jewel_res_layout).visibility = View.GONE
        }
        dialog.findViewById<CheckBox>(R.id.jewel_checkbox_immun).setOnCheckedChangeListener { compoundButton, check ->
            if(check) dialog.findViewById<LinearLayout>(R.id.jewel_immun_layout).visibility = View.VISIBLE
            else dialog.findViewById<LinearLayout>(R.id.jewel_immun_layout).visibility =View.GONE
        }

        dialog.findViewById<ImageView>(R.id.jewel_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.jewel_save_button).setOnClickListener {
            jewel.name = dialog.findViewById<TextView>(R.id.jewel_name_txt).text.toString()
            jewel.weight = if (dialog.findViewById<EditText>(R.id.jewel_weight_txt).text.toString().isNotEmpty()) dialog.findViewById<EditText>(R.id.jewel_weight_txt).text.toString().toFloat() else 0F
            jewel.desc = if (dialog.findViewById<EditText>(R.id.jewel_desc_txt).text.toString().isNotEmpty()) dialog.findViewById<EditText>(R.id.jewel_desc_txt).text.toString() else ""
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_pv_max).isChecked) {
                    jewel.maxLifeModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_pv_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_pv_max).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_cons_max).isChecked) {
                    jewel.maxConstModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_const_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_const_max).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_mana_max).isChecked) {
                    jewel.maxManaModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_mana_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_mana_max).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_weight_max).isChecked) {
                    jewel.maxWeightModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_weight_max).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_weight_max).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_damages).isChecked) {
                    jewel.damageModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_damages).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_damages).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_defense).isChecked) {
                    jewel.defModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_defense).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_defense).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_vit).isChecked) {
                    jewel.vitModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_vit).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_vit).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_vig).isChecked) {
                    jewel.vigModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_vig).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_vig).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_for).isChecked) {
                    jewel.forModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_for).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_for).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_dex).isChecked) {
                    jewel.dexModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_dex).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_dex).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_end).isChecked) {
                    jewel.endModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_end).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_end).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_mem).isChecked) {
                    jewel.memModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_mem).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_mem).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_int).isChecked) {
                    jewel.intModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_int).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_int).text.toString().toInt() else 0
                }
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_foi).isChecked) {
                    jewel.foiModifier = if (dialog.findViewById<EditText>(R.id.jewel_edit_foi).text.isNotBlank()) dialog.findViewById<EditText>(R.id.jewel_edit_foi).text.toString().toInt() else 0
                }
                jewel.weakModifier.clear()
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_weakness).isChecked){
                    if(dialog.findViewById<CheckBox>(R.id.jewel_weakness_magic).isChecked) jewel.weakModifier.add(Elem.MAGIC)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_weakness_fire).isChecked) jewel.weakModifier.add(Elem.FIRE)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_weakness_dark).isChecked) jewel.weakModifier.add(Elem.DARKNESS)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_weakness_light).isChecked) jewel.weakModifier.add(Elem.LIGHTNING)
                }
                jewel.resModifier.clear()
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_res).isChecked){
                    if(dialog.findViewById<CheckBox>(R.id.jewel_res_magic).isChecked) jewel.resModifier.add(Elem.MAGIC)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_res_fire).isChecked) jewel.resModifier.add(Elem.FIRE)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_res_dark).isChecked) jewel.resModifier.add(Elem.DARKNESS)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_res_light).isChecked) jewel.resModifier.add(Elem.LIGHTNING)
                }
                jewel.immunModifier.clear()
                if (dialog.findViewById<CheckBox>(R.id.jewel_checkbox_immun).isChecked){
                    if(dialog.findViewById<CheckBox>(R.id.jewel_immun_poison).isChecked) jewel.immunModifier.add(Status.POISON)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_immun_bleed).isChecked) jewel.immunModifier.add(Status.BLEED)
                    if(dialog.findViewById<CheckBox>(R.id.jewel_immun_frost).isChecked) jewel.immunModifier.add(Status.FROST)
                }
            if ((!isAlreadyEquip && dialog.findViewById<CheckBox>(R.id.jewel_equip).isChecked) || (isAlreadyEquip && !dialog.findViewById<CheckBox>(R.id.jewel_equip).isChecked)){
                equipJewel(jewel)
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

        if(jewel.weakModifier.isNotEmpty()){
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_weakness).isChecked = true
            dialog.findViewById<LinearLayout>(R.id.jewel_weakness_layout).visibility = View.VISIBLE
            if (jewel.weakModifier.contains(Elem.MAGIC)) dialog.findViewById<CheckBox>(R.id.jewel_weakness_magic).isChecked = true
            if (jewel.weakModifier.contains(Elem.FIRE)) dialog.findViewById<CheckBox>(R.id.jewel_weakness_fire).isChecked = true
            if (jewel.weakModifier.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.jewel_weakness_dark).isChecked = true
            if (jewel.weakModifier.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.jewel_weakness_light).isChecked = true
        }else dialog.findViewById<LinearLayout>(R.id.jewel_weakness_layout).visibility = View.GONE

        if(jewel.resModifier.isNotEmpty()){
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_res).isChecked = true
            dialog.findViewById<LinearLayout>(R.id.jewel_res_layout).visibility = View.VISIBLE
            if (jewel.resModifier.contains(Elem.MAGIC)) dialog.findViewById<CheckBox>(R.id.jewel_res_magic).isChecked = true
            if (jewel.resModifier.contains(Elem.FIRE)) dialog.findViewById<CheckBox>(R.id.jewel_res_fire).isChecked = true
            if (jewel.resModifier.contains(Elem.DARKNESS)) dialog.findViewById<CheckBox>(R.id.jewel_res_dark).isChecked = true
            if (jewel.resModifier.contains(Elem.LIGHTNING)) dialog.findViewById<CheckBox>(R.id.jewel_res_light).isChecked = true
        }else dialog.findViewById<LinearLayout>(R.id.jewel_res_layout).visibility = View.GONE

        if(jewel.immunModifier.isNotEmpty()){
            dialog.findViewById<CheckBox>(R.id.jewel_checkbox_immun).isChecked = true
            dialog.findViewById<LinearLayout>(R.id.jewel_immun_layout).visibility = View.VISIBLE
            if (jewel.immunModifier.contains(Status.POISON)) dialog.findViewById<CheckBox>(R.id.jewel_immun_poison).isChecked = true
            if (jewel.immunModifier.contains(Status.BLEED)) dialog.findViewById<CheckBox>(R.id.jewel_immun_bleed).isChecked = true
            if (jewel.immunModifier.contains(Status.FROST)) dialog.findViewById<CheckBox>(R.id.jewel_immun_frost).isChecked = true
        }else dialog.findViewById<LinearLayout>(R.id.jewel_immun_layout).visibility = View.GONE

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
            viewModel.stuff.value!!.add(item)
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
        modifPrefString(PREF_MODIFIER_WEAK, jewel)
        modifPrefString(PREF_MODIFIER_RES, jewel)
        modifPrefString(PREF_MODIFIER_IMMUN, jewel)
    }

    fun equipWeapon(weapon: Weapon){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_equip_weapon)

        val leftHand = viewModel.getLeftHand()
        val rightHand = viewModel.getRightHand()
        fillWeaponRecap(dialog, R.id.equip_weapon_recap_left, leftHand)
        fillWeaponRecap(dialog, R.id.equip_weapon_recap_right, rightHand)

        dialog.findViewById<ImageView>(R.id.equip_weapon_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.equip_weapon_save_button).setOnClickListener {
            if (dialog.findViewById<RadioButton>(R.id.equip_weapon_left_hand).isChecked) viewModel.weaponToEquipment(weapon, true)
            if (dialog.findViewById<RadioButton>(R.id.equip_weapon_right_hand).isChecked) viewModel.weaponToEquipment(weapon, false)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun fillWeaponRecap(dialog: Dialog, id:Int, weapon: Weapon){
        if(weapon.name.isEmpty()){
            dialog.findViewById<RecapEquipmentComponent>(id).recapName.text = getString(R.string.nothing)
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.visibility = View.GONE
        }else {
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.text = weapon.name
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.text = getString(R.string.total_damage)
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.text = CalcUtils.getTotalDamage(weapon, context!!, viewModel.character.value!!).toString()
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.text = weapon.weight.toString()
        }
    }

    fun equipCata(weapon: Weapon){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_equip_shield_cata)

        dialog.findViewById<TextView>(R.id.equip_shield_cata_title).text = getString(R.string.equip_catalyst)

        val catalyst = viewModel.getCatalyst()
        fillCatalystRecap(dialog, R.id.equip_shield_cata_recap, catalyst)

        dialog.findViewById<ImageView>(R.id.equip_shield_cata_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.equip_shield_cata_save_button).setOnClickListener {
            viewModel.catalystToEquipment(weapon)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun fillCatalystRecap(dialog: Dialog, id:Int, weapon: Weapon){
        if(weapon.name.isEmpty()){
            dialog.findViewById<RecapEquipmentComponent>(id).recapName.text = getString(R.string.nothing)
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.visibility = View.GONE
        }else {
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.text = weapon.name
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.text = getString(R.string.boost)
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.text = weapon.boost.toString()
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.text = weapon.weight.toString()
        }
    }

    fun equipShield(shield: Shield){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_equip_shield_cata)

        dialog.findViewById<TextView>(R.id.equip_shield_cata_title).text = getString(R.string.equip_shield)

        val shieldEquip = viewModel.getShield()
        fillShieldRecap(dialog, R.id.equip_shield_cata_recap, shieldEquip)

        dialog.findViewById<ImageView>(R.id.equip_shield_cata_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.equip_shield_cata_save_button).setOnClickListener {
            viewModel.shieldToEquipment(shield)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun fillShieldRecap(dialog: Dialog, id:Int, shield: Shield){
        if(shield.name.isEmpty()){
            dialog.findViewById<RecapEquipmentComponent>(id).recapName.text = getString(R.string.nothing)
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.visibility = View.GONE
        }else {
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.text = shield.name
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.text = getString(R.string.block)
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.text = shield.block.toString()
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.text = shield.weight.toString()
        }
    }

    fun equipArmor(armor: Armor){
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_equip_armor)

        when(armor.type){
            PieceEquipment.HAT -> {
                val hat = viewModel.getHat()
                fillArmorRecap(dialog, R.id.equip_armor_recap_hat, hat)
                dialog.findViewById<TextView>(R.id.equip_armor_chest).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_gloves).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_greaves).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_chest).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_gloves).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_greaves).visibility = View.GONE
            }
            PieceEquipment.CHEST ->{
                val chest = viewModel.getChest()
                fillArmorRecap(dialog, R.id.equip_armor_recap_chest, chest)
                dialog.findViewById<TextView>(R.id.equip_armor_hat).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_gloves).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_greaves).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_hat).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_gloves).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_greaves).visibility = View.GONE
            }
            PieceEquipment.GLOVES -> {
                val gloves = viewModel.getGloves()
                fillArmorRecap(dialog, R.id.equip_armor_recap_gloves, gloves)
                dialog.findViewById<TextView>(R.id.equip_armor_chest).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_hat).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_greaves).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_chest).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_hat).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_greaves).visibility = View.GONE
            }
            PieceEquipment.GREAVES ->{
                val greaves = viewModel.getGreaves()
                fillArmorRecap(dialog, R.id.equip_armor_recap_greaves, greaves)
                dialog.findViewById<TextView>(R.id.equip_armor_chest).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_gloves).visibility = View.GONE
                dialog.findViewById<TextView>(R.id.equip_armor_hat).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_chest).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_gloves).visibility = View.GONE
                dialog.findViewById<RecapEquipmentComponent>(R.id.equip_armor_recap_hat).visibility = View.GONE
            }
        }

        dialog.findViewById<ImageView>(R.id.equip_armor_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.equip_armor_save_button).setOnClickListener {
            viewModel.armorToEquipment(armor)
            dialog.dismiss()
        }
        dialog.show()
    }

    fun fillArmorRecap(dialog: Dialog, id:Int, armor: Armor){
        if(armor.name.isEmpty()){
            dialog.findViewById<RecapEquipmentComponent>(id).recapName.text = getString(R.string.nothing)
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.visibility = View.GONE
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.visibility = View.GONE
        }else {
            dialog.findViewById<RecapEquipmentComponent>(id).recapNameTxt.text = armor.name
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfo.text = getString(R.string.def)
            dialog.findViewById<RecapEquipmentComponent>(id).recapInfoTxt.text = armor.def.toString()
            dialog.findViewById<RecapEquipmentComponent>(id).recapWeightTxt.text = armor.weight.toString()
        }
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

    fun modifPrefString(pref: String, jewel: Jewel) {
        val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, PRIVATE_MODE)
        val prefValue = sharedPref.getString(pref, "")
        val editor = sharedPref.edit()
        var value = ""
        var deleteValue = prefValue
        when (pref) {
            PREF_MODIFIER_WEAK -> {
                for (w in jewel.weakModifier){
                    value += ", " + w.name
                    //deleteValue = ""
                    deleteValue = deleteValue.replaceFirst(", " +w.name, "")
                }
            }
            PREF_MODIFIER_RES -> {
                for (r in jewel.resModifier){
                    value += ", " + r.name
                    //deleteValue = ""
                    deleteValue = deleteValue.replaceFirst(", " +r.name, "")
                }
            }
            PREF_MODIFIER_IMMUN -> {
                for (i in jewel.immunModifier){
                    value += ", " + i.name
                    //deleteValue = ""
                    deleteValue = deleteValue.replaceFirst(", " +i.name, "")
                }
            }
        }
        if (jewel.equip) editor.putString(pref, prefValue + value) else editor.putString(pref, deleteValue)

        editor.apply()
    }

    fun fillEditItem(dialog: Dialog, item: Item) {
        dialog.findViewById<EditText>(R.id.item_name_txt).setText(item.name)
        dialog.findViewById<EditText>(R.id.item_weight_txt).setText(item.weight.toString())
        dialog.findViewById<EditText>(R.id.item_quantity_txt).setText(item.quantity.toString())
        dialog.findViewById<EditText>(R.id.item_effect_txt).setText(item.effect)
    }

    fun equipItem(item: Item, isAdd :Boolean) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_equip_item)

        if (!isAdd) {
            item.equip = !item.equip
            viewModel.editInventory()
        }

        dialog.findViewById<TextView>(R.id.equip_item_title).text = getString(R.string.disequip)
        dialog.findViewById<TextView>(R.id.equip_item_ask).text = getString(R.string.ask_item_disequip)

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

    fun remmoveStuff(position: Int){
        val builder = AlertDialog.Builder(context)
        with(builder)
        {
            setTitle(getString(R.string.warning))
            setMessage(getString(R.string.confirm_delete))
            setNegativeButton(getString(R.string.no)){dialog, which ->
                dialog.dismiss()
            }
            setPositiveButton(getString(R.string.ok)) { dialog, which ->
                if (viewModel.stuff.value!![position] is Jewel) {
                    if ((viewModel.stuff.value!![position] as Jewel).equip) equipJewel((viewModel.stuff.value!![position] as Jewel)) //disequip before remove
                }
                viewModel.stuff.value!!.remove(viewModel.stuff.value!![position])
                viewModel.editInventory()
                dialog.dismiss() }
            show()
        }
    }

    fun addOne(position: Int){
        (viewModel.stuff.value!![position] as Item).quantity +=1
        viewModel.editInventory()
    }

    fun removeOne(position: Int){
        (viewModel.stuff.value!![position] as Item).quantity -=1
        viewModel.editInventory()
    }

    override fun onItemClicked(position: Int, v: View, id :Int) {
        when(id){
            R.id.line_button_equip -> {
                if (viewModel.stuff.value!![position] is Weapon) {
                    if((viewModel.stuff.value!![position] as Weapon).boost>0)equipCata((viewModel.stuff.value!![position] as Weapon))
                    else equipWeapon((viewModel.stuff.value!![position] as Weapon))
                }
                if (viewModel.stuff.value!![position] is Shield) equipShield((viewModel.stuff.value!![position] as Shield))
                if (viewModel.stuff.value!![position] is Armor) equipArmor((viewModel.stuff.value!![position] as Armor))
                if (viewModel.stuff.value!![position] is Jewel) equipJewel((viewModel.stuff.value!![position] as Jewel))
                //if (viewModel.stuff.value!![position] is Item) equipItem((viewModel.stuff.value!![position] as Item), false)
            }
            R.id.line_button_edit -> {
                if (viewModel.stuff.value!![position] is Weapon) editWeapon(viewModel.stuff.value!![position] as Weapon, false)
                if (viewModel.stuff.value!![position] is Shield) editShield(viewModel.stuff.value!![position] as Shield, false)
                if (viewModel.stuff.value!![position] is Armor) editArmor(viewModel.stuff.value!![position] as Armor, false)
                if (viewModel.stuff.value!![position] is Jewel) editJewel(viewModel.stuff.value!![position] as Jewel, false)
                if (viewModel.stuff.value!![position] is Item) editItem((viewModel.stuff.value!![position] as Item))
            }
            R.id.line_button_delete -> remmoveStuff(position)
            R.id.line_button_minus -> removeOne(position)
            R.id.line_button_plus -> addOne(position)
        }
    }
}
