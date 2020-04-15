package com.rphelper.cecib.rphelper.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.SpellKnownAdapter
import com.rphelper.cecib.rphelper.component.SpellComponent
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.viewmodel.SpellViewModel
import kotlinx.android.synthetic.main.component_spell.view.*
import java.util.ArrayList

class SpellFragment : Fragment() {
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
        viewModel.firstEquipSpell.value?.let {
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellName.text = viewModel.firstEquipSpell.value!!.name
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellDamage.text = viewModel.firstEquipSpell.value!!.damage.toString()
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellMana.text = viewModel.firstEquipSpell.value!!.mana.toString()
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellTotal.text = "242" //TODO calcul
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellEffect.text = viewModel.firstEquipSpell.value!!.effect
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUse.text = viewModel.firstEquipSpell.value!!.use
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUseValue.text = viewModel.firstEquipSpell.value!!.useValue.toString()
            if(viewModel.firstEquipSpell.value!!.use2.isNullOrEmpty())
            { view.findViewById<LinearLayout>(R.id.spell_first_equip).spell_use_layout2.visibility = View.GONE }
            else{
                view.findViewById<LinearLayout>(R.id.spell_first_equip).spell_use_layout2.visibility = View.VISIBLE
                view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUse2.text = viewModel.firstEquipSpell.value!!.use2
                view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUseValue2.text = viewModel.firstEquipSpell.value!!.useValue2.toString()
            }
        }?:run{
            view.findViewById<LinearLayout>(R.id.spell_first_equip).spell_use_layout2.visibility = View.GONE
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellName.text = ""
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellDamage.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellMana.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellTotal.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellEffect.text = ""
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUse.text = ""
            view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUseValue.text = "0"
        }
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellButton.setOnClickListener { }

        //Second spell
        viewModel.secondEquipSpell.value?.let {
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellName.text = viewModel.secondEquipSpell.value!!.name
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellDamage.text = viewModel.secondEquipSpell.value!!.damage.toString()
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellMana.text = viewModel.secondEquipSpell.value!!.mana.toString()
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellTotal.text = "242" //TODO calcul
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellEffect.text = viewModel.secondEquipSpell.value!!.effect
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUse.text = viewModel.secondEquipSpell.value!!.use
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUseValue.text = viewModel.secondEquipSpell.value!!.useValue.toString()
            if(viewModel.secondEquipSpell.value!!.use2.isNullOrEmpty())
            { view.findViewById<LinearLayout>(R.id.spell_second_equip).spell_use_layout2.visibility = View.GONE }
            else{
                view.findViewById<LinearLayout>(R.id.spell_second_equip).spell_use_layout2.visibility = View.VISIBLE
                view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUse2.text = viewModel.secondEquipSpell.value!!.use2
                view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUseValue2.text = viewModel.secondEquipSpell.value!!.useValue2.toString()
            }
        }?:run{
            view.findViewById<LinearLayout>(R.id.spell_second_equip).spell_use_layout2.visibility = View.GONE
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellName.text = ""
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellDamage.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellMana.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellTotal.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellEffect.text = ""
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUse.text = ""
            view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUseValue.text = "0"
        }
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellButton.setOnClickListener { }

        //Third spell
        viewModel.thirdEquipSpell.value?.let {
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellName.text = viewModel.thirdEquipSpell.value!!.name
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellDamage.text = viewModel.thirdEquipSpell.value!!.damage.toString()
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellMana.text = viewModel.thirdEquipSpell.value!!.mana.toString()
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellTotal.text = "242" //TODO calcul
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellEffect.text = viewModel.thirdEquipSpell.value!!.effect
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUse.text = viewModel.thirdEquipSpell.value!!.use
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUseValue.text = viewModel.thirdEquipSpell.value!!.useValue.toString()
            if(viewModel.thirdEquipSpell.value!!.use2.isNullOrEmpty())
            { view.findViewById<LinearLayout>(R.id.spell_third_equip).spell_use_layout2.visibility = View.GONE }
            else{
                view.findViewById<LinearLayout>(R.id.spell_third_equip).spell_use_layout2.visibility = View.VISIBLE
                view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUse2.text = viewModel.thirdEquipSpell.value!!.use2
                view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUseValue2.text = viewModel.thirdEquipSpell.value!!.useValue2.toString()
            }
        }?:run{
            view.findViewById<LinearLayout>(R.id.spell_third_equip).spell_use_layout2.visibility = View.GONE
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellName.text = ""
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellDamage.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellMana.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellTotal.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellEffect.text = ""
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUse.text = ""
            view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUseValue.text = "0"
        }
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellButton.setOnClickListener { }

        //Fourth spell
        viewModel.fourthEquipSpell.value?.let {
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellName.text = viewModel.fourthEquipSpell.value!!.name
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellDamage.text = viewModel.fourthEquipSpell.value!!.damage.toString()
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellMana.text = viewModel.fourthEquipSpell.value!!.mana.toString()
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellTotal.text = "242" //TODO calcul
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellEffect.text = viewModel.fourthEquipSpell.value!!.effect
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUse.text = viewModel.fourthEquipSpell.value!!.use
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUseValue.text = viewModel.fourthEquipSpell.value!!.useValue.toString()
            if(viewModel.fourthEquipSpell.value!!.use2.isNullOrEmpty())
            { view.findViewById<LinearLayout>(R.id.spell_fourth_equip).spell_use_layout2.visibility = View.GONE }
            else{
                view.findViewById<LinearLayout>(R.id.spell_fourth_equip).spell_use_layout2.visibility = View.VISIBLE
                view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUse2.text = viewModel.fourthEquipSpell.value!!.use2
                view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUseValue2.text = viewModel.fourthEquipSpell.value!!.useValue2.toString()
            }
        }?:run{
            view.findViewById<LinearLayout>(R.id.spell_fourth_equip).spell_use_layout2.visibility = View.GONE
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellName.text = ""
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellDamage.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellMana.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellTotal.text = "0"
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellEffect.text = ""
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUse.text = ""
            view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUseValue.text = "0"
        }
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellButton.setOnClickListener { }

        /********** Spell known *******/
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = SpellKnownAdapter(ArrayList(viewModel._knownSpells.value))

        recyclerView = view.findViewById<RecyclerView>(R.id.spell_known_recycler).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }

        return view
    }
}
