package com.rphelper.cecib.rphelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener

class SpellKnownAdapter(val mDataset: MutableList<Spell>, callback:RecyclerViewClickListener) : RecyclerView.Adapter<SpellKnownAdapter.ViewHolder>() {

    final private var callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_line_spell, parent, false) as ConstraintLayout
        return ViewHolder(item)
    }

    override fun getItemCount(): Int = mDataset.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_name).text = mDataset[position].name
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_note).text = mDataset[position].effect
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_damage_txt).text = mDataset[position].damage.toString()
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_mana_txt).text = mDataset[position].mana.toString()
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use).text = mDataset[position].use
        holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use_txt).text = mDataset[position].useValue.toString()
        if(mDataset[position].use2.isNotEmpty()) {
            holder!!.spellKnown.findViewById<LinearLayout>(R.id.line_spell_use_layout2).visibility = View.VISIBLE
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use2).text = mDataset[position].use2
            holder!!.spellKnown.findViewById<TextView>(R.id.line_spell_use_txt2).text = mDataset[position].useValue2.toString()
        }else{
            holder!!.spellKnown.findViewById<LinearLayout>(R.id.line_spell_use_layout2).visibility = View.GONE
        }
        holder.spellKnown.setOnClickListener {
            callback.onItemClicked(position, holder.spellKnown, 0)
        }
    }

    class ViewHolder(val spellKnown: ConstraintLayout) : RecyclerView.ViewHolder(spellKnown)
}