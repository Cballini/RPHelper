package com.rphelper.cecib.rphelper.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.enums.PieceEquipment
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener

class ItemAdapter(val mDataset: ArrayList<Any>, callback : RecyclerViewClickListener) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    final private var callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_line_item, parent, false) as ConstraintLayout

        return ViewHolder(item)
    }

    override fun getItemCount(): Int = mDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.lineObject.findViewById<TextView>(R.id.line_item_name).text = (mDataset[position]as Stuff).name
        if(mDataset[position] is Weapon){
            holder!!.lineObject.findViewById<TextView>(R.id.line_item_note).text = (mDataset[position] as Weapon).getDescription()
            if((mDataset[position] as Weapon).boost>0){
                holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.catalyst)
            }else{
                holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.weapon)
            }

        }
        if(mDataset[position] is Shield){
            holder!!.lineObject.findViewById<TextView>(R.id.line_item_note).text = (mDataset[position] as Shield).getDescription()
            holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.shield)
        }
        if(mDataset[position] is Armor) {
            holder!!.lineObject.findViewById<TextView>(R.id.line_item_note).text = (mDataset[position] as Armor).getDescription()
            when((mDataset[position] as Armor).type){
                PieceEquipment.HAT -> holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.hat)
                PieceEquipment.CHEST -> holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.chest)
                PieceEquipment.GLOVES -> holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.gloves)
                PieceEquipment.GREAVES -> holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.greaves)
                else -> holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.chest)
            }
        }
        if(mDataset[position] is Jewel) {
            holder!!.lineObject.findViewById<ImageView>(R.id.line_item_equip_img).visibility = View.VISIBLE
            var desc = (mDataset[position] as Jewel).desc
            desc += (mDataset[position] as Jewel).getDescription()
            holder!!.lineObject.findViewById<TextView>(R.id.line_item_note).text = desc
            holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.jewel)
            if ((mDataset[position] as Jewel).equip) {
                holder!!.lineObject.findViewById<ImageView>(R.id.line_item_equip_img).setImageResource(R.drawable.circle_green)
            } else {
                holder!!.lineObject.findViewById<ImageView>(R.id.line_item_equip_img).setImageResource(R.drawable.circle_grey)
            }
        } else{
            holder!!.lineObject.findViewById<ImageView>(R.id.line_item_equip_img).visibility = View.GONE
        }
        if(mDataset[position] is Item) {
            holder!!.lineObject.findViewById<TextView>(R.id.line_item_note).text = (mDataset[position] as Item).effect
            holder!!.lineObject.findViewById<ImageView>(R.id.line_item_icon).setImageResource(R.drawable.item)
            holder!!.lineObject.findViewById<LinearLayout>(R.id.line_item_quantity_layout).visibility = View.VISIBLE
            holder!!.lineObject.findViewById<TextView>(R.id.line_item_quantity_txt).text = (mDataset[position] as Item).quantity.toString()
        }else{
            holder!!.lineObject.findViewById<LinearLayout>(R.id.line_item_quantity_layout).visibility = View.GONE
        }

        if(0F!=(mDataset[position]as Stuff).weight) {
            holder!!.lineObject.findViewById<LinearLayout>(R.id.line_item_weight_layout).visibility = View.VISIBLE
            holder!!.lineObject.findViewById<TextView>(R.id.line_item_weight_txt).text = (mDataset[position]as Stuff).weight.toString()
        }else{
            holder!!.lineObject.findViewById<LinearLayout>(R.id.line_item_weight_layout).visibility = View.GONE
        }

        holder.lineObject.setOnClickListener {
            callback.onItemClicked(position, holder.lineObject)
        }
    }

    class ViewHolder(val lineObject: ConstraintLayout) : RecyclerView.ViewHolder(lineObject)
}