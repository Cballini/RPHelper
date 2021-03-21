package com.rphelper.cecib.rphelper.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener

class DonAdapter (val mDataset: MutableList<String>, callback : RecyclerViewClickListener) : RecyclerView.Adapter<DonAdapter.ViewHolder>() {
    final private var callback = callback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonAdapter.ViewHolder {
        val item = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.component_swipe_don, parent, false) as SwipeLayout

        return DonAdapter.ViewHolder(item)
    }

    override fun getItemCount(): Int = mDataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.lineObject.findViewById<TextView>(R.id.line_don_txt).text = mDataset[position]

        holder.lineObject.findViewById<ImageView>(R.id.line_don_button_edit).setOnClickListener {
            callback.onItemClicked(position, holder.lineObject.findViewById<ImageView>(R.id.line_don_button_edit), R.id.line_don_button_edit)
        }
        holder.lineObject.findViewById<ImageView>(R.id.line_don_button_delete).setOnClickListener {
            callback.onItemClicked(position, holder.lineObject.findViewById<ImageView>(R.id.line_don_button_delete), R.id.line_don_button_delete)
        }
    }


    class ViewHolder(val lineObject: SwipeLayout) : RecyclerView.ViewHolder(lineObject)
}