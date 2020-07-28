package com.rphelper.cecib.rphelper.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.SwipeLayout.SwipeListener
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.LineItemComponent
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.PieceEquipment
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener

class ItemAdapter(val mDataset: MutableList<Any>, callback : RecyclerViewClickListener) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    final private var callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_swipe_item, parent, false) as SwipeLayout

        return ViewHolder(item)
    }

    override fun getItemCount(): Int = mDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val component = holder!!.lineObject.findViewById<LineItemComponent>(R.id.line_item_component)
        component.lineItemName.text = (mDataset[position] as Stuff).name
        if (mDataset[position] is Weapon) {
            component.lineItemNote.text = (mDataset[position] as Weapon).getDescription()

            if ((mDataset[position] as Weapon).boost > 0) {
                component.lineItemIcon.setImageResource(R.drawable.catalyst)
            } else {
                component.lineItemIcon.setImageResource(R.drawable.weapon)
            }

        }
        if (mDataset[position] is Shield) {
            component.lineItemNote.text = (mDataset[position] as Shield).getDescription()
            component.lineItemIcon.setImageResource(R.drawable.shield)
        }
        if (mDataset[position] is Armor) {
            component.lineItemNote.text = (mDataset[position] as Armor).getDescription()
            when ((mDataset[position] as Armor).type) {
                PieceEquipment.HAT -> component.lineItemIcon.setImageResource(R.drawable.hat)
                PieceEquipment.CHEST -> component.lineItemIcon.setImageResource(R.drawable.chest)
                PieceEquipment.GLOVES -> component.lineItemIcon.setImageResource(R.drawable.gloves)
                PieceEquipment.GREAVES -> component.lineItemIcon.setImageResource(R.drawable.greaves)
                else -> component.lineItemIcon.setImageResource(R.drawable.chest)
            }
        }
        if (mDataset[position] is Jewel) {
            component.lineItemEquip.visibility = View.VISIBLE
            var desc = (mDataset[position] as Jewel).desc
            desc += (mDataset[position] as Jewel).getDescription()
            component.lineItemNote.text = desc
            component.lineItemIcon.setImageResource(R.drawable.jewel)
            if ((mDataset[position] as Jewel).equip) {
                component.lineItemEquipImg.setImageResource(R.drawable.circle_green)
            } else {
                component.lineItemEquipImg.setImageResource(R.drawable.circle_grey)
            }
        } else {
            component.lineItemEquip.visibility = View.GONE
        }
        if (mDataset[position] is Item) {
            component.lineItemNote.text = (mDataset[position] as Item).effect
            component.lineItemIcon.setImageResource(R.drawable.item)
            component.lineItemQuantityLayout.visibility = View.VISIBLE
            component.lineItemQuantity.text = (mDataset[position] as Item).quantity.toString()
            holder!!.lineObject.findViewById<ImageView>(R.id.line_button_plus).visibility = View.VISIBLE
            holder!!.lineObject.findViewById<ImageView>(R.id.line_button_minus).visibility = View.VISIBLE
            holder!!.lineObject.findViewById<ImageView>(R.id.line_button_equip).visibility = View.GONE
        } else {
            component.lineItemQuantityLayout.visibility = View.GONE
            holder!!.lineObject.findViewById<ImageView>(R.id.line_button_plus).visibility = View.GONE
            holder!!.lineObject.findViewById<ImageView>(R.id.line_button_minus).visibility = View.GONE
            holder!!.lineObject.findViewById<ImageView>(R.id.line_button_equip).visibility = View.VISIBLE
        }

        if (0F != (mDataset[position] as Stuff).weight) {
            component.lineItemWeightLayout.visibility = View.VISIBLE
            component.lineItemWeight.text = (mDataset[position] as Stuff).weight.toString()
        } else {
            component.lineItemWeightLayout.visibility = View.GONE
        }

        holder.lineObject.findViewById<ImageView>(R.id.line_button_edit).setOnClickListener {
            callback.onItemClicked(position, holder.lineObject.findViewById<ImageView>(R.id.line_button_edit), R.id.line_button_edit)
        }
        holder.lineObject.findViewById<ImageView>(R.id.line_button_delete).setOnClickListener {
            callback.onItemClicked(position, holder.lineObject.findViewById<ImageView>(R.id.line_button_delete), R.id.line_button_delete)
        }
        holder.lineObject.findViewById<ImageView>(R.id.line_button_equip).setOnClickListener {
            callback.onItemClicked(position, holder.lineObject.findViewById<ImageView>(R.id.line_button_equip), R.id.line_button_equip)
        }
        holder.lineObject.findViewById<ImageView>(R.id.line_button_minus).setOnClickListener {
            callback.onItemClicked(position, holder.lineObject.findViewById<ImageView>(R.id.line_button_minus), R.id.line_button_minus)
        }
        holder.lineObject.findViewById<ImageView>(R.id.line_button_plus).setOnClickListener {
            callback.onItemClicked(position, holder.lineObject.findViewById<ImageView>(R.id.line_button_plus), R.id.line_button_plus)
        }

    }

    class ViewHolder(val lineObject: SwipeLayout) : RecyclerView.ViewHolder(lineObject)
}