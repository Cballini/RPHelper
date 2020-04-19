package com.rphelper.cecib.rphelper.fragments


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.ItemAdapter
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.viewmodel.InventoryViewModel
import java.util.ArrayList


class InventoryFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: InventoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)

        viewModel = InventoryViewModel(context!!)

        //Money
        view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTitle.text = getString(R.string.money)
        viewModel.money.observe(viewLifecycleOwner, Observer {
            val money = it!!.toString() + " po"
            view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.setText(it.toString())
        })

        //Objects
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ItemAdapter(ArrayList(viewModel.items.value!!))
        viewModel.items.observe(viewLifecycleOwner, Observer {
            viewAdapter = ItemAdapter(ArrayList(it!!))
        })

        recyclerView = view.findViewById<RecyclerView>(R.id.bag_recycler).apply {
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
