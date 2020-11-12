package com.rphelper.cecib.rphelper.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.rphelper.cecib.rphelper.R

class HistoryAdapter(val mDataset:MutableList<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_line_history, parent, false) as ConstraintLayout
        return HistoryAdapter.ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var res = holder.itemView.context.resources

        holder.history.findViewById<TextView>(R.id.line_history_txt).text = mDataset[position]
        if(mDataset[position].contains("perdu")){
            holder.history.findViewById<TextView>(R.id.line_history_txt).setTextColor(res.getColor(R.color.red)) //damage
        }else if(mDataset[position].contains("gagné")){
            holder.history.findViewById<TextView>(R.id.line_history_txt).setTextColor(res.getColor(R.color.bonus)) //heal
        }else{
            holder.history.findViewById<TextView>(R.id.line_history_txt).setTextColor(res.getColor(R.color.colorTxt))
        }

    }

    override fun getItemCount(): Int = mDataset.size

    class ViewHolder(val history: ConstraintLayout) : RecyclerView.ViewHolder(history)
}