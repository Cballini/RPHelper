package com.rphelper.cecib.rphelper.fragments

import android.app.AlertDialog
import android.app.Dialog
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.SpellKnownAdapter
import com.rphelper.cecib.rphelper.component.SpellComponent
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.dto.Weapon
import com.rphelper.cecib.rphelper.utils.FileUtils
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener
import com.rphelper.cecib.rphelper.viewmodel.SpellViewModel
import kotlinx.android.synthetic.main.component_spell.view.*
import org.json.JSONObject

class SpellFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: SpellViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spell, container, false)

        viewModel = SpellViewModel(context!!)

        val liveData = viewModel.getDataSnapshotLiveData()
        liveData!!.observe(viewLifecycleOwner, Observer { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModel._character.value = dataSnapshot.child("character").getValue(Character::class.java)
                viewModel._catalyst.value = dataSnapshot.child("equipment").child("catalyst").getValue(Weapon::class.java)

                var allSpells = ArrayList<Spell>()
                val value = dataSnapshot.child("spells").value as ArrayList<HashMap<String, Any>>
                for (_value in value) {
                    // Convert HashMap to Spell
                    val jsonSpell = JSONObject(_value as Map<*, *>).toString()
                    val spell = Gson().fromJson<Spell>(jsonSpell, Spell::class.java)
                    allSpells.add(spell)
                }
                viewModel._allSpells.value = allSpells

                viewModel.getSpell1()
                viewModel.getSpell2()
                viewModel.getSpell3()
                viewModel.getSpell4()
                viewModel.getSpell5()
                viewModel.getSpell6()
                viewModel._knownSpells.value = viewModel.getKnownSpells()
            }
        })


        /********* Equip spells ********/
        var maxSpells = 3
        viewModel.character.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            maxSpells = viewModel.getMaxEquipSpells()
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).visibility = if (maxSpells < 4) View.GONE else View.VISIBLE
            view.findViewById<SpellComponent>(R.id.spell_fifth_equip).visibility = if (maxSpells<5) View.GONE else View.VISIBLE
            view.findViewById<SpellComponent>(R.id.spell_sixth_equip).visibility = if (maxSpells<6) View.GONE else View.VISIBLE
        })

        //First spell
        viewModel.firstEquipSpell.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            initSpellView(view, R.id.spell_first_equip, getString(R.string.spell1), it)
        })

        //Second spell
        viewModel.secondEquipSpell.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            initSpellView(view, R.id.spell_second_equip, getString(R.string.spell2), it)
        })

        //Third spell
        viewModel.thirdEquipSpell.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            initSpellView(view, R.id.spell_third_equip, getString(R.string.spell3), it)
        })

        //Fourth spell
        viewModel.fourthEquipSpell.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            initSpellView(view, R.id.spell_fourth_equip, getString(R.string.spell4), it)
        })

        //Fifth spell
        viewModel.fifthEquipSpell.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            initSpellView(view, R.id.spell_fifth_equip, getString(R.string.spell5), it)
        })

        //Sixth spell
        viewModel.sixthEquipSpell.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            initSpellView(view, R.id.spell_sixth_equip, getString(R.string.spell6), it)
        })

        //Help
        view.findViewById<ImageView>(R.id.spell_help).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.help))
                setMessage(getString(R.string.spell_help))
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }


        /********** Spell known *******/
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = SpellKnownAdapter(viewModel.knownSpells.value!!, this)

        recyclerView = view.findViewById<RecyclerView>(R.id.spell_known_recycler).apply {
            // use a linear layout manager
            layoutManager = viewManager
            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        viewModel.knownSpells.observe(viewLifecycleOwner, Observer {
            viewAdapter = SpellKnownAdapter(viewModel._knownSpells.value!!, this)
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
        view.findViewById<SpellComponent>(R.id.spell_fifth_equip).spellPlaceLayout.setOnClickListener {
            editSpell(getString(R.string.spell5), viewModel.fifthEquipSpell.value!!)
        }
        view.findViewById<SpellComponent>(R.id.spell_sixth_equip).spellPlaceLayout.setOnClickListener {
            editSpell(getString(R.string.spell6), viewModel.sixthEquipSpell.value!!)
        }
        addSpell(view, R.id.spell_known_add, getString(R.string.known_spells))


        return view
    }

    override fun onItemClicked(position: Int, v: View, id :Int) {
        val popupMenu = PopupMenu(context, v)
        popupMenu.menuInflater.inflate(R.menu.menu_item, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_edit -> editSpell(getString(R.string.known_spells), viewModel.knownSpells.value!![position])
                R.id.action_delete -> {
                    val spell = viewModel.knownSpells.value!![position]
                    viewModel.knownSpells.value!!.remove(spell)
                    viewModel.editSpells()
                }
                R.id.action_equip -> {
                    equipSpell(viewModel.knownSpells.value!![position])
                }
            }
            true
        })
        popupMenu.show()
    }

    fun initSpellView(view: View, id: Int, place: String, spell: Spell?) {
        view!!.findViewById<SpellComponent>(id).spellPlace.text = place
        if (spell!!.name.isNotBlank()) {
            view!!.findViewById<SpellComponent>(id).spellName.text = spell!!.name
            view!!.findViewById<SpellComponent>(id).spellDamage.text = spell!!.damage.toString()
            view!!.findViewById<SpellComponent>(id).spellMana.text = spell!!.mana.toString()
            view!!.findViewById<SpellComponent>(id).spellTotal.text = viewModel.getTotalDamage(spell!!).toString()
            view!!.findViewById<SpellComponent>(id).spellEffect.text = spell!!.effect
            view!!.findViewById<SpellComponent>(id).spellUse.text = spell!!.use
            view!!.findViewById<SpellComponent>(id).spellUseValue.text = spell!!.useValue.toString()
            if (spell.use2.isNullOrEmpty()) {
                view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.GONE
            } else {
                view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.VISIBLE
                view!!.findViewById<SpellComponent>(id).spellUse2.text = spell!!.use2
                view!!.findViewById<SpellComponent>(id).spellUseValue2.text = spell!!.useValue2.toString()
            }
            view!!.findViewById<SpellComponent>(id).spellButton.setOnClickListener { viewModel.attack(spell); checkAndDisplayAlert(spell.mana) }
        } else {
            view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.GONE
            view!!.findViewById<SpellComponent>(id).spellName.text = ""
            view!!.findViewById<SpellComponent>(id).spellDamage.text = "0"
            view!!.findViewById<SpellComponent>(id).spellMana.text = "0"
            view!!.findViewById<SpellComponent>(id).spellTotal.text = "0"
            view!!.findViewById<SpellComponent>(id).spellEffect.text = ""
            view!!.findViewById<SpellComponent>(id).spellUse.text = ""
            view!!.findViewById<SpellComponent>(id).spellUseValue.text = "0"
        }
    }

    fun editSpell(place: String, spell: Spell) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_edit_spell)
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
            if (dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().isNotEmpty()) {
                spell.damage = dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().toInt()
            } else {
                spell.damage = 0
            }
            if (dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().isNotEmpty()) {
                spell.mana = dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().toInt()
            } else {
                spell.mana = 0
            }
            if(dialog.findViewById<CheckBox>(R.id.spell_rapidfire).isChecked) spell.rapidFire = true
            spell.use = when (true) {
                dialog.findViewById<CheckBox>(R.id.spell_use_int).isChecked -> getString(R.string.intel)
                else -> ""
            }
            if (dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().isNotEmpty()) {
                spell.useValue = dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().toInt()
            } else {
                spell.useValue = 0
            }
            spell.use2 = when (true) {
                dialog.findViewById<CheckBox>(R.id.spell_use_foi).isChecked -> getString(R.string.faith)
                else -> ""
            }
            if (dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().isNotEmpty()) {
                spell.useValue2 = dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().toInt()
            } else {
                spell.useValue2 = 0
            }

            if (dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString().isNotEmpty()) {
                spell.effect = dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString()
            } else {
                spell.effect = ""
            }
            viewModel.editSpells()
            dialog.dismiss()
        }

        dialog.show()
    }

    fun addSpell(view: View, id: Int, place: String) {
        view.findViewById<FloatingActionButton>(id).setOnClickListener {
            val dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_edit_spell)
            dialog.findViewById<TextView>(R.id.spell_type).text = place
            dialog.findViewById<ImageView>(R.id.spell_cancel_button).setOnClickListener { dialog.dismiss() }
            dialog.findViewById<TextView>(R.id.spell_disequip_button).visibility = View.GONE
            dialog.findViewById<TextView>(R.id.spell_save_button).setOnClickListener {
                val spell = Spell()
                spell.name = dialog.findViewById<EditText>(R.id.spell_name_txt).text.toString()
                if (dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().isNotEmpty()) {
                    spell.damage = dialog.findViewById<EditText>(R.id.spell_damage_txt).text.toString().toInt()
                } else {
                    spell.damage = 0
                }
                if (dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().isNotEmpty()) {
                    spell.mana = dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().toInt()
                } else {
                    spell.mana = 0
                }
                if (dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().isNotEmpty()) {
                    spell.mana = dialog.findViewById<EditText>(R.id.spell_mana_txt).text.toString().toInt()
                } else {
                    spell.mana = 0
                }
                if(dialog.findViewById<CheckBox>(R.id.spell_rapidfire).isChecked) spell.rapidFire = true
                spell.use = when (true) {
                    dialog.findViewById<CheckBox>(R.id.spell_use_int).isChecked -> getString(R.string.intel)
                    else -> ""
                }
                if (dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().isNotEmpty()) {
                    spell.useValue = dialog.findViewById<EditText>(R.id.spell_use_int_txt).text.toString().toInt()
                } else {
                    spell.useValue = 0
                }
                spell.use2 = when (true) {
                    dialog.findViewById<CheckBox>(R.id.spell_use_foi).isChecked -> getString(R.string.faith)
                    else -> ""
                }
                if (dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().isNotEmpty()) {
                    spell.useValue2 = dialog.findViewById<EditText>(R.id.spell_use_foi_txt).text.toString().toInt()
                } else {
                    spell.useValue2 = 0
                }

                if (dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString().isNotEmpty()) {
                    spell.effect = dialog.findViewById<EditText>(R.id.spell_effect_txt).text.toString()
                } else {
                    spell.effect = ""
                }
                spell.equip = false
                viewModel.knownSpells.value!!.add(spell)
                viewModel.editSpells()
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    fun fillSpellEdit(dialog: Dialog, spell: Spell) {
        if (spell.name.isNotEmpty()) {
            dialog.findViewById<EditText>(R.id.spell_name_txt).setText(spell.name)
            dialog.findViewById<EditText>(R.id.spell_damage_txt).setText(spell.damage.toString())
            dialog.findViewById<EditText>(R.id.spell_mana_txt).setText(spell.mana.toString())
            if(spell.rapidFire) dialog.findViewById<CheckBox>(R.id.spell_rapidfire).isChecked = true
            if (spell.use.isNotEmpty()) {
                when (spell.use) {
                    context!!.getString(R.string.intel) -> dialog.findViewById<CheckBox>(R.id.spell_use_int).isChecked = true
                }
            }
            if (0 != spell.useValue) dialog.findViewById<EditText>(R.id.spell_use_int_txt).setText(spell.useValue.toString())
            if (spell.use2.isNotEmpty()) {
                when (spell.use2) {
                    context!!.getString(R.string.faith) -> dialog.findViewById<CheckBox>(R.id.spell_use_foi).isChecked = true
                }
            }
            if (0 != spell.useValue2) dialog.findViewById<EditText>(R.id.spell_use_foi_txt).setText(spell.useValue2.toString())
            dialog.findViewById<EditText>(R.id.spell_effect_txt).setText(spell.effect)
        }
    }

    fun equipSpell(spell: Spell) {
        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_equip_spell)
        val maxSpells = viewModel.getMaxEquipSpells()
        when(maxSpells){
            3 -> {
                dialog.findViewById<RadioButton>(R.id.equip_spell4).visibility = View.GONE
                dialog.findViewById<RadioButton>(R.id.equip_spell5).visibility = View.GONE
                dialog.findViewById<RadioButton>(R.id.equip_spell6).visibility = View.GONE
            }
            4->{
                dialog.findViewById<RadioButton>(R.id.equip_spell5).visibility = View.GONE
                dialog.findViewById<RadioButton>(R.id.equip_spell6).visibility = View.GONE
            }
            5->{
                dialog.findViewById<RadioButton>(R.id.equip_spell6).visibility = View.GONE
            }
        }

        dialog.findViewById<ImageView>(R.id.equip_cancel_button).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.equip_save_button).setOnClickListener {
            spell.equip = true
            when (true) {
                dialog.findViewById<RadioButton>(R.id.equip_spell1).isChecked -> {
                    fillOneSpell(viewModel.firstEquipSpell.value!!, spell)
                }
                dialog.findViewById<RadioButton>(R.id.equip_spell2).isChecked -> {
                    fillOneSpell(viewModel.secondEquipSpell.value!!, spell)
                }
                dialog.findViewById<RadioButton>(R.id.equip_spell3).isChecked -> {
                fillOneSpell(viewModel.thirdEquipSpell.value!!, spell)
            }
                dialog.findViewById<RadioButton>(R.id.equip_spell4).isChecked -> {
                    fillOneSpell(viewModel.fourthEquipSpell.value!!, spell)
                }
                dialog.findViewById<RadioButton>(R.id.equip_spell5).isChecked -> {
                    fillOneSpell(viewModel.fifthEquipSpell.value!!, spell)
                }
                dialog.findViewById<RadioButton>(R.id.equip_spell6).isChecked -> {
                    fillOneSpell(viewModel.sixthEquipSpell.value!!, spell)
                }
            }

            viewModel.editSpells()
            dialog.dismiss()
        }
        dialog.show()
    }

    fun fillOneSpell(spellToFill : Spell, spell : Spell){
        if (spellToFill.name.isNotEmpty()) {
            spellToFill.equip = false
            viewModel.knownSpells.value!!.add(Spell(spellToFill))
        }
        spellToFill.name = spell.name
        spellToFill.damage = spell.damage
        spellToFill.rapidFire = spell.rapidFire
        spellToFill.mana = spell.mana
        spellToFill.effect = spell.effect
        spellToFill.use = spell.use
        spellToFill.useValue = spell.useValue
        spellToFill.use2 = spell.use2
        spellToFill.useValue2 = spell.useValue2
        spellToFill.equip = spell.equip
    }

    fun checkAndDisplayAlert(value: Int) {
        var msg = ""
        var snackMsg = ""

        if (viewModel.checkMana()) {
            msg = getString(R.string.warning_mana)
        } else {
            snackMsg = getString(R.string.lost_msg) + " " + value + " points de mana et 30 points de constitution."
        }

        if (msg.isNotEmpty()) {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.warning))
                setMessage(msg)
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        } else {
            Snackbar.make(view!!, snackMsg, Snackbar.LENGTH_SHORT).show()
        }
    }

}
