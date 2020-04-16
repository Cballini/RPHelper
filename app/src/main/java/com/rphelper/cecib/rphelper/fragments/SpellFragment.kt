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
            initSpellView(view, R.id.spell_first_equip, getString(R.string.spell1), viewModel.firstEquipSpell.value)
        }

        //Second spell
        viewModel.secondEquipSpell.value?.let {
            initSpellView(view, R.id.spell_second_equip, getString(R.string.spell2), viewModel.secondEquipSpell.value)
        }

        //Third spell
        viewModel.thirdEquipSpell.value?.let {
            initSpellView(view, R.id.spell_third_equip, getString(R.string.spell3), viewModel.thirdEquipSpell.value)
        }

        //Fourth spell
        viewModel.fourthEquipSpell.value?.let {
            initSpellView(view, R.id.spell_fourth_equip, getString(R.string.spell4), viewModel.fourthEquipSpell.value)
        }

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

    fun initSpellView(view : View, id: Int, place :String, spell: Spell?){
        spell?.let {
            view!!.findViewById<SpellComponent>(id).spellPlace.text = place
            view!!.findViewById<SpellComponent>(id).spellName.text = spell!!.name
            view!!.findViewById<SpellComponent>(id).spellDamage.text = spell!!.damage.toString()
            view!!.findViewById<SpellComponent>(id).spellMana.text = spell!!.mana.toString()
            view!!.findViewById<SpellComponent>(id).spellTotal.text = "242" //TODO calcul
            view!!.findViewById<SpellComponent>(id).spellEffect.text = spell!!.effect
            view!!.findViewById<SpellComponent>(id).spellUse.text = spell!!.use
            view!!.findViewById<SpellComponent>(id).spellUseValue.text = spell!!.useValue.toString()
            if(viewModel.firstEquipSpell.value!!.use2.isNullOrEmpty())
            { view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.GONE }
            else{
                view!!.findViewById<LinearLayout>(id).spell_use_layout2.visibility = View.VISIBLE
                view!!.findViewById<SpellComponent>(id).spellUse2.text = spell!!.use2
                view!!.findViewById<SpellComponent>(id).spellUseValue2.text = spell!!.useValue2.toString()
            }
        }?:run{
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

}
