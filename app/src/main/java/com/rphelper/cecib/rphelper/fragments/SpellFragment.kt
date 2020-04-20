package com.rphelper.cecib.rphelper.fragments

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.SpellKnownAdapter
import com.rphelper.cecib.rphelper.component.SpellComponent
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener
import com.rphelper.cecib.rphelper.viewmodel.SpellViewModel
import kotlinx.android.synthetic.main.component_spell.view.*
import java.util.ArrayList

class SpellFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: SpellViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spell, container, false)

        viewModel = SpellViewModel(context!!)


        /********* Equip spells ********/
        //First spell
        viewModel.firstEquipSpell.observe(viewLifecycleOwner, Observer {
            initSpellView(view, R.id.spell_first_equip, getString(R.string.spell1), it)
        })

        //Second spell
        viewModel.secondEquipSpell.observe(viewLifecycleOwner, Observer {
            initSpellView(view, R.id.spell_second_equip, getString(R.string.spell2), it)
        })

        //Third spell
        viewModel.thirdEquipSpell.observe(viewLifecycleOwner, Observer {
            initSpellView(view, R.id.spell_third_equip, getString(R.string.spell3), it)
        })

        //Fourth spell
        viewModel.fourthEquipSpell.observe(viewLifecycleOwner, Observer {
            initSpellView(view, R.id.spell_fourth_equip, getString(R.string.spell4), it)
        })


        /********** Spell known *******/
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = SpellKnownAdapter(ArrayList(viewModel._knownSpells.value), this)

        recyclerView = view.findViewById<RecyclerView>(R.id.spell_known_recycler).apply {
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        viewModel.knownSpells.observe(viewLifecycleOwner, Observer {
            viewAdapter = SpellKnownAdapter(ArrayList(viewModel._knownSpells.value), this)
            recyclerView.apply {
                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }
        })

        /********* EDIT *******/
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellPlaceLayout.setOnClickListener {
            editSpell(getString(R.string.spell1), viewModel.firstEquipSpell.value!!)
        }
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellPlaceLayout.setOnClickListener {
            editSpell(getString(R.string.spell2), viewModel.secondEquipSpell.value!!)
        }
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellPlaceLayout.setOnClickListener {
        editSpell(getString(R.string.spell3), viewModel.thirdEquipSpell.value!!)
        }
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellPlaceLayout.setOnClickListener {
            editSpell(getString(R.string.spell4), viewModel.fourthEquipSpell.value!!)
        }
        addSpell(view, R.id.spell_known_add, getString(R.string.known_spells))


        return view
    }

    override fun onItemClicked(position: Int, v: View) {
        val popupMenu = PopupMenu(context,v)
        popupMenu.menuInflater.inflate(R.menu.menu_item,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_edit -> editSpell(getString(R.string.known_spells), viewModel.knownSpells.value!![position])
                R.id.action_delete -> {
                    val spell = viewModel.knownSpells.value!![position]
                    viewModel.knownSpells.value!!.remove(spell)
                    viewModel.editSpells()
                }
                R.id.action_equip ->{
                    equipSpell(viewModel.knownSpells.value!![position])
                }
            }
            true
        })
        popupMenu.show()
    }

    fun initSpellView(view : View, id: Int, place :String, spell: Spell?){
        view!!.findViewById<SpellComponent>(id).spellPlace.text = place
        if(spell!!.name.isNotBlank()){
            view!!.findViewById<SpellComponent>(id).spellName.text = spell!!.name
            view!!.findViewById<SpellComponent>(id).spellDamage.text = spell!!.damage.toString()
            view!!.findViewById<SpellComponent>(id).spellMana.text = spell!!.mana.toString()
            view!!.findViewById<SpellComponent>(id).spellTotal.text = viewModel.getTotalDamage(spell!!).toString()
            view!!.findViewById<SpellComponent>(id).spellEffect.text = spell!!.effect
            view!!.findViewById<SpellComponent>(id).spellUse.text = spell!!.use
            view!!.findViewById<SpellComponent>(id).spellUseValue.text = spell!!.useValue.toString()
            if(spell.use2.isNullOrEmpty())
            { view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.GONE }
            else{
                view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.VISIBLE
                view!!.findViewById<SpellComponent>(id).spellUse2.text = spell!!.use2
                view!!.findViewById<SpellComponent>(id).spellUseValue2.text = spell!!.useValue2.toString()
            }
        }else{
            view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.GONE
            view!!.findViewById<SpellComponent>(id).spellName.text = ""
            view!!.findViewById<SpellComponent>(id).spellDamage.text = "0"
            view!!.findViewById<SpellComponent>(id).spellMana.text = "0"
            view!!.findViewById<SpellComponent>(id).spellTotal.text = "0"
            view!!.findViewById<SpellComponent>(id).spellEffect.text = ""
            view!!.findViewById<SpellComponent>(id).spellUse.text = ""
            view!!.findViewById<SpellComponent>(id).spellUseValue.text = "0"
            view!!.findViewById<SpellComponent>(id).spellButton.setOnClickListener { }
        }
    }

    fun editSpell(place :String, spell: Spell){
            val dialog = Dialog(activity)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setContentView(R.layout.popup_edit_spell)
            dialog.findViewById<TextView>(R.id.spell_type).text = place
            fillSpellEdit(dialog, spell)
        dialog.findViewById<EditText>(R.id.spell_name_txt).setSelection(dialog.findViewById<EditText>(R.id.spell_name_txt).text.length)
            dialog.findViewById<ImageView>(R.id.spell_cancel_button).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<TextView>(R.id.spell_disequip_button).setOnClickListener {
                spell.equip = false
                viewModel.editSpells()
                dialog.dismiss()
            }
            dialog.findViewById<TextView>(R.id.spell_save_button).setOnClickListener {
                spell.name = dialog.findViewById<EditText>(R.id.spell_name_txt).text.toString()
                if (dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().isNotEmpty()){
                    spell.damage = dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().toInt()
                }else{
                    spell.damage = 0
                }
                if (dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().isNotEmpty()){
                    spell.mana = dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().toInt()
                }else{
                    spell.mana = 0
                }
                spell.use = when(true){
                    dialog.findViewById<CheckBox>(R.id.spell_use_int).isChecked -> getString(R.string.intel)
                    else -> ""
                }
                if (dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().isNotEmpty()){
                    spell.useValue = dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().toInt()
                }else{
                    spell.useValue = 0
                }
                spell.use2 = when(true){
                    dialog.findViewById<CheckBox>(R.id.spell_use_foi).isChecked -> getString(R.string.faith)
                    else -> ""
                }
                if (dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().isNotEmpty()){
                    spell.useValue2 = dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().toInt()
                }else{
                    spell.useValue2 = 0
                }

                if (dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString().isNotEmpty()){
                    spell.effect = dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString()
                }else{
                    spell.effect = ""
                }

                viewModel.editSpells()
                dialog.dismiss()
            }

            dialog .show()
    }

    fun addSpell(view: View, id: Int, place :String){
        view.findViewById<ImageView>(id).setOnClickListener {
            val dialog = Dialog(activity)
            dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog .setContentView(R.layout.popup_edit_spell)
            dialog.findViewById<TextView>(R.id.spell_type).text = place
            dialog.findViewById<ImageView>(R.id.spell_cancel_button).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<TextView>(R.id.spell_disequip_button).visibility = View.GONE
            dialog.findViewById<TextView>(R.id.spell_save_button).setOnClickListener {
                val spell = Spell()
                spell.name = dialog.findViewById<EditText>(R.id.spell_name_txt).text.toString()
                if (dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().isNotEmpty()){
                    spell.damage = dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().toInt()
                }else{
                    spell.damage = 0
                }
                if (dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().isNotEmpty()){
                    spell.mana = dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().toInt()
                }else{
                    spell.mana = 0
                }
                if (dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().isNotEmpty()){
                    spell.mana = dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().toInt()
                }else{
                    spell.mana = 0
                }
                spell.use = when(true){
                    dialog.findViewById<CheckBox>(R.id.spell_use_int).isChecked -> getString(R.string.intel)
                    else -> ""
                }
                if (dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().isNotEmpty()){
                    spell.useValue = dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().toInt()
                }else{
                    spell.useValue = 0
                }
                spell.use2 = when(true){
                    dialog.findViewById<CheckBox>(R.id.spell_use_foi).isChecked -> getString(R.string.faith)
                    else -> ""
                }
                if (dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().isNotEmpty()){
                    spell.useValue2 = dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().toInt()
                }else{
                    spell.useValue2 = 0
                }

                if (dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString().isNotEmpty()){
                    spell.effect = dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString()
                }else{
                    spell.effect = ""
                }
                spell.equip = false
                viewModel.knownSpells.value!!.add(spell)
                viewModel.editSpells()
                dialog.dismiss()
            }

            dialog .show()
        }
    }

    fun fillSpellEdit(dialog: Dialog, spell: Spell){
        if(spell.name.isNotEmpty()) {
            dialog.findViewById<EditText>(R.id.spell_name_txt).setText(spell.name)
            dialog.findViewById<EditText>(R.id.spell_damage_txt).setText(spell.damage.toString())
            dialog.findViewById<EditText>(R.id.spell_mana_txt).setText(spell.mana.toString())
            if (spell.use.isNotEmpty()){
                when(spell.use){
                    context!!.getString(R.string.intel) -> dialog.findViewById<CheckBox>(R.id.spell_use_int).isChecked = true
                }
            }
            if(0!=spell.useValue)dialog.findViewById<EditText>(R.id.spell_use_int_txt).setText(spell.useValue.toString())
            if (spell.use2.isNotEmpty()){
                when(spell.use2){
                    context!!.getString(R.string.faith) -> dialog.findViewById<CheckBox>(R.id.spell_use_foi).isChecked = true
                }
            }
            if(0!=spell.useValue2)dialog.findViewById<EditText>(R.id.spell_use_foi_txt).setText(spell.useValue2.toString())
            dialog.findViewById<EditText>(R.id.spell_effect_txt).setText(spell.effect)
        }
    }

    fun equipSpell(spell: Spell){
        val dialog = Dialog(activity)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setContentView(R.layout.popup_equip_spell)
        dialog.findViewById<ImageView>(R.id.equip_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.equip_save_button).setOnClickListener {
            spell.equip = true
            when(true){
                dialog.findViewById<RadioButton>(R.id.equip_spell1).isChecked ->{
                    if(viewModel.firstEquipSpell.value!!.name.isNotEmpty()){
                        viewModel.firstEquipSpell.value!!.equip = false
                        viewModel.knownSpells.value!!.add(Spell(viewModel.firstEquipSpell.value!!))
                    }
                    viewModel.firstEquipSpell.value!!.name = spell.name
                    viewModel.firstEquipSpell.value!!.damage = spell.damage
                    viewModel.firstEquipSpell.value!!.mana = spell.mana
                    viewModel.firstEquipSpell.value!!.effect = spell.effect
                    viewModel.firstEquipSpell.value!!.use = spell.use
                    viewModel.firstEquipSpell.value!!.useValue = spell.useValue
                    viewModel.firstEquipSpell.value!!.use2 = spell.use2
                    viewModel.firstEquipSpell.value!!.useValue2 = spell.useValue2
                    viewModel.firstEquipSpell.value!!.equip = spell.equip
                }
                dialog.findViewById<RadioButton>(R.id.equip_spell2).isChecked ->{
                    if(viewModel.secondEquipSpell.value!!.name.isNotEmpty()){
                        viewModel.secondEquipSpell.value!!.equip = false
                        viewModel.knownSpells.value!!.add(Spell(viewModel.secondEquipSpell.value!!))
                    }
                    viewModel.secondEquipSpell.value!!.name = spell.name
                    viewModel.secondEquipSpell.value!!.damage = spell.damage
                    viewModel.secondEquipSpell.value!!.mana = spell.mana
                    viewModel.secondEquipSpell.value!!.effect = spell.effect
                    viewModel.secondEquipSpell.value!!.use = spell.use
                    viewModel.secondEquipSpell.value!!.useValue = spell.useValue
                    viewModel.secondEquipSpell.value!!.use2 = spell.use2
                    viewModel.secondEquipSpell.value!!.useValue2 = spell.useValue2
                    viewModel.secondEquipSpell.value!!.equip = spell.equip
                }
                dialog.findViewById<RadioButton>(R.id.equip_spell3).isChecked ->{
                    if(viewModel.thirdEquipSpell.value!!.name.isNotEmpty()){
                        viewModel.thirdEquipSpell.value!!.equip = false
                        viewModel.knownSpells.value!!.add(Spell(viewModel.thirdEquipSpell.value!!))
                    }
                    viewModel.thirdEquipSpell.value!!.name = spell.name
                    viewModel.thirdEquipSpell.value!!.damage = spell.damage
                    viewModel.thirdEquipSpell.value!!.mana = spell.mana
                    viewModel.thirdEquipSpell.value!!.effect = spell.effect
                    viewModel.thirdEquipSpell.value!!.use = spell.use
                    viewModel.thirdEquipSpell.value!!.useValue = spell.useValue
                    viewModel.thirdEquipSpell.value!!.use2 = spell.use2
                    viewModel.thirdEquipSpell.value!!.useValue2 = spell.useValue2
                    viewModel.thirdEquipSpell.value!!.equip = spell.equip
                }
                dialog.findViewById<RadioButton>(R.id.equip_spell4).isChecked ->{
                    if(viewModel.fourthEquipSpell.value!!.name.isNotEmpty()){
                        viewModel.fourthEquipSpell.value!!.equip = false
                        viewModel.knownSpells.value!!.add(Spell(viewModel.fourthEquipSpell.value!!))
                    }
                    viewModel.fourthEquipSpell.value!!.name = spell.name
                    viewModel.fourthEquipSpell.value!!.damage = spell.damage
                    viewModel.fourthEquipSpell.value!!.mana = spell.mana
                    viewModel.fourthEquipSpell.value!!.effect = spell.effect
                    viewModel.fourthEquipSpell.value!!.use = spell.use
                    viewModel.fourthEquipSpell.value!!.useValue = spell.useValue
                    viewModel.fourthEquipSpell.value!!.use2 = spell.use2
                    viewModel.fourthEquipSpell.value!!.useValue2 = spell.useValue2
                    viewModel.fourthEquipSpell.value!!.equip = spell.equip
                }
            }

            viewModel.editSpells()
            dialog.dismiss()
        }
        dialog .show()
    }

}
