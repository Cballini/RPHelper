package com.rphelper.cecib.rphelper.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.ObjectAdapter
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.dto.Object
import java.util.ArrayList


class BagFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bag, container, false)

        //Money
        view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTitle.text = getString(R.string.money)
        view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.text = "50 po"

        //Objects
        var carriedObjects = ArrayList<Object>()
        var carriedObject = Object("Talisman", 1, "Pour lancer miracles", false, 0.4F)
        var carriedObject2 = Object("Bague d'Oolacile", 1, "+20 mana max", true)
        carriedObjects.add(carriedObject)
        carriedObjects.add(carriedObject2)
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ObjectAdapter(carriedObjects)

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
