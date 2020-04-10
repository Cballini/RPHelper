package com.rphelper.cecib.rphelper.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.dto.Spell

class SpellKnownAdapter (val mDataset: ArrayList<Spell>) :RecyclerView.Adapter<SpellKnownAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SpellKnownAdapter.ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_line_spell, parent, false) as ConstraintLayout

        return ViewHolder(item)
    }

    override fun getItemCount(): Int = mDataset.size


    override fun onBindViewHolder(holder: SpellKnownAdapter.ViewHolder?, position: Int) {
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_name).text = mDataset[position].name
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_note).text = mDataset[position].effect
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_damage_txt).text = mDataset[position].damage.toString()
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_mana_txt).text = mDataset[position].mana.toString()
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use).text = mDataset[position].use
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use_txt).text = mDataset[position].useValue.toString()
        if (mDataset[position].use2.isNotEmpty()){
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use2).visibility = View.VISIBLE
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use_txt2).visibility = View.VISIBLE
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use2).text = mDataset[position].use2
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use_txt2).text = mDataset[position].useValue2.toString()
        }else{
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use2).visibility = View.INVISIBLE
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use_txt2).visibility = View.INVISIBLE
        }
    }

    class ViewHolder(val spellKnown: ConstraintLayout) : RecyclerView.ViewHolder(spellKnown)
}