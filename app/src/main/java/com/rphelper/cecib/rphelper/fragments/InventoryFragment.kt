package com.rphelper.cecib.rphelper.fragments


import android.app.Dialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.adapter.ItemAdapter
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.dto.Item
import com.rphelper.cecib.rphelper.utils.RecyclerViewClickListener
import com.rphelper.cecib.rphelper.viewmodel.InventoryViewModel
import java.util.ArrayList


class InventoryFragment : Fragment(), RecyclerViewClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: InventoryViewModel
    private var isOnMoneyEdit = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_inventory, container, false)

        viewModel = InventoryViewModel(context!!)

        //Money
        view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTitle.text = getString(R.string.money)
        view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.setEnabled(false)
        viewModel.money.observe(viewLifecycleOwner, Observer {
            val money = it!!.toString() + " po"
            view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.setText(it.toString())
        })

        //Objects
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ItemAdapter(ArrayList(viewModel.items.value!!), this)
        recyclerView = view.findViewById<RecyclerView>(R.id.bag_recycler).apply {
            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter
        }
        viewModel.items.observe(viewLifecycleOwner, Observer {
            viewAdapter = ItemAdapter(ArrayList(it!!), this)
            recyclerView = view.findViewById<RecyclerView>(R.id.bag_recycler).apply {
                // use a linear layout manager
                layoutManager = viewManager

                // specify an viewAdapter (see also next example)
                adapter = viewAdapter
            }
        })

        /***** EDIT ********/
        view.findViewById<ImageView>(R.id.bag_money_edit).setOnClickListener {
            if(isOnMoneyEdit) {
                isOnMoneyEdit = false
                view.findViewById<ImageView>(R.id.bag_money_edit).setImageResource(R.drawable.ic_edit)
                view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.setEnabled(false)
                if(view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.text.toString().isNotEmpty())viewModel._money.value = view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.text.toString().toInt()
                viewModel.editInventory()
            }else{
                isOnMoneyEdit = true
                view.findViewById<ImageView>(R.id.bag_money_edit).setImageResource(R.drawable.ic_check)
                view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.setEnabled(true)
                view.findViewById<CategoryHorizontalComponent>(R.id.bag_money).catTxt.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
        view.findViewById<ImageView>(R.id.bag_add).setOnClickListener {
            addItem()
        }

        return view
    }

    fun addItem(){
        val dialog = Dialog(activity)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setContentView(R.layout.popup_edit_item)
        dialog.findViewById<ImageView>(R.id.item_cancel_button).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.item_save_button).setOnClickListener {
            var item = Item()
            item.name = dialog.findViewById<EditText>(R.id.item_name_txt).text.toString()
            if (dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().isNotEmpty()){
                item.weight = dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().toFloat()
            }else{
                item.weight = 0F
            }
            if (dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().isNotEmpty()){
                item.quantity = dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().toInt()
            }else{
                item.quantity = 0
            }
            item.equip = dialog.findViewById<CheckBox>(R.id.item_equip).isChecked

            if (dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString().isNotEmpty()){
                item.effect = dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString()
            }else{
                item.effect = ""
            }
            viewModel.items.value!!.add(item)
            viewModel.editInventory()
            dialog.dismiss()
        }

        dialog .show()
    }

    fun editItem(item: Item){
        val dialog = Dialog(activity)
        dialog .requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog .setContentView(R.layout.popup_edit_item)
        fillEditItem(dialog, item)
        dialog.findViewById<EditText>(R.id.item_name_txt).setSelection(dialog.findViewById<EditText>(R.id.item_name_txt).text.length)
        dialog.findViewById<ImageView>(R.id.item_cancel_button).setOnClickListener { dialog.dismiss() }

        dialog.findViewById<TextView>(R.id.item_save_button).setOnClickListener {
            item.name = dialog.findViewById<EditText>(R.id.item_name_txt).text.toString()
            if (dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().isNotEmpty()){
                item.weight = dialog.findViewById<EditText>(R.id.item_weight_txt).text.toString().toFloat()
            }else{
                item.weight = 0F
            }
            if (dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().isNotEmpty()){
                item.quantity = dialog.findViewById<EditText>(R.id.item_quantity_txt).text.toString().toInt()
            }else{
                item.quantity = 0
            }
            item.equip = dialog.findViewById<CheckBox>(R.id.item_equip).isChecked

            if (dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString().isNotEmpty()){
                item.effect = dialog.findViewById<EditText>(R.id.item_effect_txt).text.toString()
            }else{
                item.effect = ""
            }
            viewModel.editInventory()
            dialog.dismiss()
        }
        dialog .show()
    }

    fun fillEditItem(dialog: Dialog, item: Item){
        dialog.findViewById<EditText>(R.id.item_name_txt).setText(item.name)
        dialog.findViewById<EditText>(R.id.item_weight_txt).setText(item.weight.toString())
        dialog.findViewById<EditText>(R.id.item_quantity_txt).setText(item.quantity.toString())
        dialog.findViewById<CheckBox>(R.id.item_equip).isChecked = item.equip
        dialog.findViewById<EditText>(R.id.item_effect_txt).setText(item.effect)
    }

    override fun onItemClicked(position: Int, v: View) {
        val popupMenu = PopupMenu(context,v)
        popupMenu.menuInflater.inflate(R.menu.menu_item,popupMenu.menu)
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.action_edit -> editItem(viewModel.items.value!![position])
                R.id.action_delete -> {
                    viewModel.items.value!!.remove(viewModel.items.value!![position])
                    viewModel.editInventory()
                }
                R.id.action_equip ->{
                    viewModel.items.value!![position].equip = !viewModel.items.value!![position].equip
                    viewModel.editInventory()
                }
            }
            true
        })
        popupMenu.show()
    }
}
