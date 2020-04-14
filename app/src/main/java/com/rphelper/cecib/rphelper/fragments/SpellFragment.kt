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
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.SpellKnownAdapter
import com.rphelper.cecib.rphelper.component.SpellComponent
import com.rphelper.cecib.rphelper.dto.Spell
import java.util.ArrayList

class SpellFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spell, container, false)

        /********* Equip spells ********/
        //First spell
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellName.text = "Piqure puissante d'Oolacile"
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellDamage.text = "22"
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellMana.text = "4"
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellTotal.text = "242"
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellEffect.text = "Lance une grande aiguille magique."
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUse.text = getString(R.string.intel)
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUseValue.text = "23"
        /*if (2use){
        view.findViewById<LinearLayout>(R.id.spell_first_equip).spellUseLayout2.visibility = View.GONE
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUse2.text = getString(R.string.intel)
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellUseValue2.text = "23"}
        else{view.findViewById<LinearLayout>(R.id.spell_first_equip).spellUseLayout2.visibility = View.VISIBLE}*/
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellButton.setOnClickListener {  }

        //Second spell
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellName.text = "Epée d'Oolacile"
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellDamage.text = "20"
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellMana.text = "4"
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellTotal.text = "240"
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellEffect.text = "Forme une rapière spectrale sur le sceptre. Attaque au càc."
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUse.text = getString(R.string.intel)
        view.findViewById<SpellComponent>(R.id.spell_second_equip).spellUseValue.text = "23"
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellButton.setOnClickListener {  }

        //Third spell
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellName.text = "Pluie de cristaux"
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellDamage.text = "14"
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellMana.text = "19"
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellTotal.text = "229"
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellEffect.text = "Lance 5 éclats magiques dans un cône jusqu'à 18m. Chaque éclat à 50% de chance de toucher la même cible."
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUse.text = getString(R.string.intel)
        view.findViewById<SpellComponent>(R.id.spell_third_equip).spellUseValue.text = "18"
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellButton.setOnClickListener {  }

        //Fourth spell
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellName.text = "Guèpe magique"
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellDamage.text = "20"
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellMana.text = "20"
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellTotal.text = "235"
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellEffect.text = "Active 3 boules magiques qui s'activent si un ennemi approche à moins de 3m (possibilité d'activer les boules une par une)."
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUse.text = getString(R.string.intel)
        view.findViewById<SpellComponent>(R.id.spell_fourth_equip).spellUseValue.text = "20"
        view.findViewById<SpellComponent>(R.id.spell_first_equip).spellButton.setOnClickListener {  }

        /********** Spell known *******/
        var spellsKnown = ArrayList<Spell>()
        var spell = Spell("Luciole", 0, 20, "Créer une boule de lumière éclairante.", getString(R.string.intel), 10, false)
        spellsKnown.add(spell)
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = SpellKnownAdapter(spellsKnown)

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
