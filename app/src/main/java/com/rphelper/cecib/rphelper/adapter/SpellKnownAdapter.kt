package com.rphelper.cecib.rphelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.LineSpellComponent
import com.rphelper.cecib.rphelper.dto.Spell
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener

class SpellKnownAdapter(val mDataset: MutableList<Spell>, callback:RecyclerViewClickListener) : RecyclerView.Adapter<SpellKnownAdapter.ViewHolder>() {

    final private var callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_swipe_spell, parent, false) as SwipeLayout
        return ViewHolder(item)
    }

    override fun getItemCount(): Int = mDataset.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellName.text = mDataset[position].name
        holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellNote.text = mDataset[position].effect
        holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellDamageTxt.text = mDataset[position].damage.toString()
        holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellManaTxt.text = mDataset[position].mana.toString()
        holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellUse.text = mDataset[position].use
        holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellUseTxt.text = mDataset[position].useValue.toString()
        if(mDataset[position].use2.isNotEmpty()) {
            holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellUseLayout2.visibility = View.VISIBLE
            holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellUse2.text = mDataset[position].use2
            holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellUseTxt2.text = mDataset[position].useValue2.toString()
        }else{
            holder!!.spellKnown.findViewById<LineSpellComponent>(R.id.line_don_component).lineSpellUseLayout2.visibility = View.GONE
        }
        holder.spellKnown.setOnClickListener {
            callback.onItemClicked(position, holder.spellKnown, 0)
        }

        holder.spellKnown.findViewById<ImageView>(R.id.line_button_edit).setOnClickListener {
            callback.onItemClicked(position, holder.spellKnown.findViewById<ImageView>(R.id.line_button_edit), R.id.line_button_edit)
        }
        holder.spellKnown.findViewById<ImageView>(R.id.line_button_delete).setOnClickListener {
            callback.onItemClicked(position, holder.spellKnown.findViewById<ImageView>(R.id.line_button_delete), R.id.line_button_delete)
        }
        holder.spellKnown.findViewById<ImageView>(R.id.line_button_equip).setOnClickListener {
            callback.onItemClicked(position, holder.spellKnown.findViewById<ImageView>(R.id.line_button_equip), R.id.line_button_equip)
        }
    }

    class ViewHolder(val spellKnown: SwipeLayout) : RecyclerView.ViewHolder(spellKnown)
}