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
import com.rphelper.cecib.rphelper.dto.Object

class ObjectAdapter(val mDataset: ArrayList<Object>) : RecyclerView.Adapter<ObjectAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_line_object, parent, false) as ConstraintLayout

        return ViewHolder(item)
    }

    override fun getItemCount(): Int = mDataset.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.lineObject.findViewById<TextView>(R.id.line_object_name).text = mDataset[position].name
        holder!!.lineObject.findViewById<TextView>(R.id.line_object_note).text = mDataset[position].effect
        if(mDataset[position].equip) {
            holder!!.lineObject.findViewById<ImageView>(R.id.line_object_equip_img).setImageResource(R.drawable.circle_green)
        }else{
            holder!!.lineObject.findViewById<ImageView>(R.id.line_object_equip_img).setImageResource(R.drawable.circle_grey)
        }
        if(0F!=mDataset[position].weight) {
            holder!!.lineObject.findViewById<LinearLayout>(R.id.line_object_weight_layout).visibility = View.VISIBLE
            holder!!.lineObject.findViewById<TextView>(R.id.line_object_weight_txt).text = mDataset[position].weight.toString()
        }else{
            holder!!.lineObject.findViewById<LinearLayout>(R.id.line_object_weight_layout).visibility = View.GONE
        }
        holder!!.lineObject.findViewById<TextView>(R.id.line_object_quantity_txt).text = mDataset[position].quantity.toString()
    }

    class ViewHolder(val lineObject: ConstraintLayout) : RecyclerView.ViewHolder(lineObject)
}