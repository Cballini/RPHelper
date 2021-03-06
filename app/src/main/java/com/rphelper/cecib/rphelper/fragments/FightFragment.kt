package com.rphelper.cecib.rphelper.fragments


import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.rphelper.cecib.rphelper.*
import com.rphelper.cecib.rphelper.adapter.HistoryAdapter
import com.rphelper.cecib.rphelper.adapter.SpellKnownAdapter
import com.rphelper.cecib.rphelper.component.DamageComponent
import com.rphelper.cecib.rphelper.component.IndicComponent
import com.rphelper.cecib.rphelper.component.SpellComponent
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.dto.Fight
import com.rphelper.cecib.rphelper.dto.Inventory
import com.rphelper.cecib.rphelper.utils.CalcUtils
import com.rphelper.cecib.rphelper.viewmodel.FightViewModel
import kotlinx.android.synthetic.main.component_damage_calc.view.*
import kotlin.math.absoluteValue


class FightFragment : Fragment() {
    private lateinit var viewModel: FightViewModel
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyViewAdapter: RecyclerView.Adapter<*>
    private lateinit var historyViewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fight, container, false)

        viewModel = FightViewModel(context!!, MainActivity.viewModel.fight.value!!, MainActivity.viewModel.character.value!!, MainActivity.viewModel.equipment.value!!)

        view.findViewById<TextView>(R.id.fight_roll_dice).setOnClickListener {
            val random = Math.random()*100
            view.findViewById<TextView>(R.id.fight_roll_dice).text = random.toInt().toString()
        }

        //Help
        view.findViewById<ImageView>(R.id.fight_help).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.help))
                setMessage(getString(R.string.fight_help))
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }

        MainActivity.viewModel.fight.observe(viewLifecycleOwner, Observer {
            viewModel.fight = it
            //Posture
            when(it.posture){
                getString(R.string.offensive) ->{
                    view.findViewById<RadioButton>(R.id.fight_posture_offensive).isChecked = true
                    view.findViewById<LinearLayout>(R.id.fight_action_offensive_layout).visibility = View.VISIBLE
                    view.findViewById<LinearLayout>(R.id.fight_action_offensive_layout2).visibility = View.VISIBLE
                    view.findViewById<LinearLayout>(R.id.fight_action_def_ref_layout).visibility = View.GONE
                }
                getString(R.string.defensive) ->{
                    view.findViewById<RadioButton>(R.id.fight_posture_defensive).isChecked = true
                    view.findViewById<LinearLayout>(R.id.fight_action_offensive_layout).visibility = View.GONE
                    view.findViewById<LinearLayout>(R.id.fight_action_offensive_layout2).visibility = View.GONE
                    view.findViewById<LinearLayout>(R.id.fight_action_def_ref_layout).visibility = View.VISIBLE
                    view.findViewById<Button>(R.id.fight_action_dodge).visibility = View.GONE
                    view.findViewById<Button>(R.id.fight_action_block).visibility = View.VISIBLE
                }
                getString(R.string.reflex) -> {
                    view.findViewById<RadioButton>(R.id.fight_posture_reflex).isChecked = true
                    view.findViewById<LinearLayout>(R.id.fight_action_offensive_layout).visibility = View.GONE
                    view.findViewById<LinearLayout>(R.id.fight_action_offensive_layout2).visibility = View.GONE
                    view.findViewById<LinearLayout>(R.id.fight_action_def_ref_layout).visibility = View.VISIBLE
                    view.findViewById<Button>(R.id.fight_action_dodge).visibility = View.VISIBLE
                    view.findViewById<Button>(R.id.fight_action_block).visibility = View.GONE
                }
            }

            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageResult.text = viewModel.getLastDamage().toString()

            view.findViewById<EditText>(R.id.fight_action_bleed_txt).setText(it.bleed.toString())

            //Frost
            if(it.frost){
                view.findViewById<Button>(R.id.fight_action_frost).text = getString(R.string.annul_frost)
            }else{
                view.findViewById<Button>(R.id.fight_action_frost).text = getString(R.string.activ_frost)
            }
        })
        MainActivity.viewModel.character.observe(viewLifecycleOwner, Observer {
            viewModel.character = it
            if(it.const.value< COST_ATTACK_CONST) {
                view.findViewById<Button>(R.id.fight_action_attack).isEnabled = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_attack).backgroundTintList =  resources.getColorStateList(R.color.medium_grey)
                }
            }
            else{
                view.findViewById<Button>(R.id.fight_action_attack).isEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_attack).backgroundTintList =  resources.getColorStateList(R.color.colorAccent)
                }
            }
            if(it.const.value< COST_TWIN_CONST) {
                view.findViewById<Button>(R.id.fight_action_twin).isEnabled = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_twin).backgroundTintList =  resources.getColorStateList(R.color.medium_grey)
                }
            }
            else{
                view.findViewById<Button>(R.id.fight_action_twin).isEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_twin).backgroundTintList =  resources.getColorStateList(R.color.colorAccent)
                }
            }
            if(it.const.value< COST_2HANDS_CONST){
                view.findViewById<Button>(R.id.fight_action_attack_2_hands).isEnabled = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_attack_2_hands).backgroundTintList =  resources.getColorStateList(R.color.medium_grey)
                }
            }
            else{
                view.findViewById<Button>(R.id.fight_action_attack_2_hands).isEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_attack_2_hands).backgroundTintList =  resources.getColorStateList(R.color.colorAccent)
                }
            }
            if(it.const.value< COST_BLOCK_CONST) {
                view.findViewById<Button>(R.id.fight_action_block).isEnabled = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_block).backgroundTintList =  resources.getColorStateList(R.color.medium_grey)
                }
            }
            else{
                view.findViewById<Button>(R.id.fight_action_block).isEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_block).backgroundTintList =  resources.getColorStateList(R.color.colorAccent)
                }
            }
            if(it.const.value< COST_DODGE_CONST) {
                view.findViewById<Button>(R.id.fight_action_dodge).isEnabled = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_dodge).backgroundTintList =  resources.getColorStateList(R.color.medium_grey)
                }
            }
            else{
                view.findViewById<Button>(R.id.fight_action_dodge).isEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.findViewById<Button>(R.id.fight_action_dodge).backgroundTintList =  resources.getColorStateList(R.color.colorAccent)
                }
            }
        })
        MainActivity.viewModel.equipment.observe(viewLifecycleOwner, Observer {
            viewModel.equipment = it
        })


        view.findViewById<RadioButton>(R.id.fight_posture_offensive).setOnCheckedChangeListener { compoundButton, b ->
            if(view.findViewById<RadioButton>(R.id.fight_posture_offensive).isChecked) {
                viewModel.fight.posture = getString(R.string.offensive)
                viewModel.saveFight()
            }
        }
        view.findViewById<RadioButton>(R.id.fight_posture_defensive).setOnCheckedChangeListener { compoundButton, b ->
            if(view.findViewById<RadioButton>(R.id.fight_posture_defensive).isChecked) {
                viewModel.fight.posture  = getString(R.string.defensive)
                viewModel.saveFight()
            }
        }
        view.findViewById<RadioButton>(R.id.fight_posture_reflex).setOnCheckedChangeListener { compoundButton, b ->
            if(view.findViewById<RadioButton>(R.id.fight_posture_reflex).isChecked) {
                viewModel.fight.posture  = getString(R.string.reflex)
                viewModel.saveFight()
            }
        }

        //Calc damages
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageTitle.text = getString(R.string.damages)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton1.text = getString(R.string.brut_dmg)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton2.text = getString(R.string.elem_res_dmg)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton3.text = getString(R.string.block)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton4.text = getString(R.string.weak_dmg)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton5.text = getString(R.string.elem_brut_dmg)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButtonSecondLine.visibility = View.VISIBLE

        var dmg = 0
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val s = view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageReceived.text
            if (s.toString().isNotEmpty()) {
                //Brut damages
                if (view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton1.isChecked) dmg = (s.toString().toInt() - viewModel.getDef())
                //Res elem
                if (view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton2.isChecked) dmg = (s.toString().toInt() * 0.2F).toInt()
                //Block
                if (view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton3.isChecked) {
                    dmg = (s.toString().toInt() * viewModel.getBlock()).toInt()
                    dmg -= (viewModel.getDef())
                }
                //Weak
                if (view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton4.isChecked) dmg = (s.toString().toInt() * 2)
                //Elem brut
                if (view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton5.isChecked) dmg = s.toString().toInt()
                if (dmg < 0) dmg = 0
                view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageResult.text = dmg.toString()
            }
            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageReceived.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if(s.toString().isNotEmpty()) {
                        when (true) {
                            //Brut damages
                            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton1.isChecked -> dmg = (s.toString().toInt() - viewModel.getDef())
                            //Res elem
                            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton2.isChecked -> dmg = (s.toString().toInt() * 0.2F).toInt()
                            //Block
                            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton3.isChecked -> {
                                    dmg = (s.toString().toInt() * viewModel.getBlock()).toInt()
                                    dmg -= (viewModel.getDef())
                            }
                            //Weak
                            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton4.isChecked -> dmg = (s.toString().toInt() * 2)
                            //Elem brut
                            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton5.isChecked -> dmg = s.toString().toInt()
                        }
                    }
                    if (dmg<0) dmg = 0
                    view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageResult.text = dmg.toString()
                }
                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {}
            })
            view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageSubmit.setOnClickListener {
                val result = view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageResult.text.toString().toInt()
                val life = viewModel.submit(result)
                checkAndDisplayAlert(getString(R.string.pv), result, life)
            }
        }

        //Calc heal
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageTitle.text = getString(R.string.recovery)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton1.text = getString(R.string.pv_min)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton2.text = getString(R.string.const_min)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton3.text = getString(R.string.mana_min)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButtonSecondLine.visibility = View.GONE
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageResult.visibility = View.GONE
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageEqual.visibility = View.GONE

        var recovery = 0
       view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val s = view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageReceived.text
            if (s.toString().isNotEmpty()) recovery = s.toString().toInt()
        }
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) recovery = s.toString().toInt()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageSubmit.text = getString(R.string.recover)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageSubmit.setOnClickListener {
            if (view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton1.isChecked){ val life = viewModel.recoverLife(recovery); msgHeal(getString(R.string.pv), recovery, life)}
            if (view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton2.isChecked){val const = viewModel.recoverConst(recovery); msgHeal(getString(R.string.constitution), recovery, const) }
            if (view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton3.isChecked){val mana = viewModel.recoverMana(recovery); msgHeal(getString(R.string.mana), recovery, mana)}
        }

        view.findViewById<ImageView>(R.id.fight_history_drop).setOnClickListener {
            if(view.findViewById<RecyclerView>(R.id.fight_history_recycler).visibility == View.GONE){
                view.findViewById<RecyclerView>(R.id.fight_history_recycler).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.fight_history_drop).setImageResource(R.drawable.ic_arrow_drop_up)
            }else{
                view.findViewById<RecyclerView>(R.id.fight_history_recycler).visibility = View.GONE
                view.findViewById<ImageView>(R.id.fight_history_drop).setImageResource(R.drawable.ic_arrow_drop_down)
            }
        }

        /********* ACTIONS *************/
        view.findViewById<Button>(R.id.fight_action_attack).setOnClickListener { checkAndDisplayAlert(getString(R.string.constitution), 80,  viewModel.attackOrBlock()) }
        view.findViewById<Button>(R.id.fight_action_twin).setOnClickListener { checkAndDisplayAlert(getString(R.string.constitution), 120, viewModel.twin()) }
        view.findViewById<Button>(R.id.fight_action_attack_2_hands).setOnClickListener { checkAndDisplayAlert(getString(R.string.constitution), 40,  viewModel.attack2Hands()) }
        view.findViewById<Button>(R.id.fight_action_block).setOnClickListener { checkAndDisplayAlert(getString(R.string.constitution), 80, viewModel.attackOrBlock()) }
        view.findViewById<Button>(R.id.fight_action_dodge).setOnClickListener { checkAndDisplayAlert(getString(R.string.constitution), 30, viewModel.dodge()) }
        view.findViewById<Button>(R.id.fight_action_bleed).setOnClickListener {
            var damage = 0
            if (view.findViewById<EditText>(R.id.fight_action_bleed_txt).text.isNotBlank()) damage = view.findViewById<EditText>(R.id.fight_action_bleed_txt).text.toString().toInt()
            val life = viewModel.bleed(damage)
            checkAndDisplayAlert(getString(R.string.pv), damage, life)}
        //TODO refacto
        view.findViewById<Button>(R.id.fight_action_poison).setOnClickListener { checkAndDisplayAlert(getString(R.string.pv),(CalcUtils.getLifeMax(context!!, Services.getJsonCharacter(context!!))*0.05).toInt(), viewModel.getPoison())}
        view.findViewById<Button>(R.id.fight_action_frost).setOnClickListener {
            val const = viewModel.frost()
            if(viewModel.fight.frost) {
                viewModel._lastMsg.value = context!!.getString(R.string.activ_msg_frost) + " " + const.absoluteValue
                Snackbar.make(view, viewModel.lastMsg.value.toString(), Snackbar.LENGTH_LONG).show()
            }
            else {
                viewModel._lastMsg.value = context!!.getString(R.string.annul_msg_frost)
                Snackbar.make(view, viewModel.lastMsg.value.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }


        /*********** HISTORY **************/
        historyViewManager = LinearLayoutManager(this.context)
        historyViewAdapter = HistoryAdapter(viewModel.getHistory())

        historyRecyclerView = view.findViewById<RecyclerView>(R.id.fight_history_recycler).apply {
            // use a linear layout manager
            layoutManager = historyViewManager
            // specify an viewAdapter (see also next example)
            adapter = historyViewAdapter
        }

        viewModel.lastMsg.observe(viewLifecycleOwner, Observer {
            if(it.isNotBlank()) {
                val history = viewModel.getHistory()
                var newHistory = ArrayList<String>()
                newHistory.add(it)
                if (history.size < 10) {
                    newHistory.addAll(history)
                } else {
                    newHistory.addAll(history.dropLast(1))
                }
                viewModel.editHistory(newHistory)
                historyViewAdapter = HistoryAdapter(newHistory)
                historyRecyclerView.apply {
                    // use a linear layout manager
                    layoutManager = historyViewManager

                    // specify an viewAdapter (see also next example)
                    adapter = historyViewAdapter
                }
            }
        })

        return view
    }

    fun checkAndDisplayAlert(type:String, value : Int, stat:Int){
        var msg =""
        var snackMsg =""
        when(type){
            getString(R.string.pv)->{
                if(viewModel.checkLife()){
                    msg = getString(R.string.warning_life) + "Il vous reste : " + stat + "/" + viewModel.getLifeMax()+"."
                }
                snackMsg = getString(R.string.lost_msg) + " " + value + " points de vie. (" + stat + "/" + viewModel.getLifeMax()+"pv)."
            }
            getString(R.string.constitution)->{
                if(viewModel.checkConst(30)){
                    msg = getString(R.string.warning_const30)
                }else if(viewModel.checkConst(80)){
                    msg = getString(R.string.warning_const80)
                }
                snackMsg = getString(R.string.lost_msg) + " " + value + " points de constitution. (" + stat + "/" + viewModel.getConstMax() +"pc)."
            }
        }
        if (msg.isNotEmpty()){
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.warning))
                setMessage(msg)
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }else{
            Snackbar.make(view!!, snackMsg, Snackbar.LENGTH_LONG).show()
        }
        viewModel._lastMsg.value = snackMsg
    }

    fun msgHeal(type:String, value : Int, stat: Int){
        var snackMsg =""
        when(type){
            getString(R.string.pv) -> snackMsg = getString(R.string.win_msg) + " " + value + " points de vie. (" + stat + "/" + viewModel.getLifeMax() + "pv)."
            getString(R.string.constitution) -> snackMsg = getString(R.string.win_msg) + " " + value + " points de constitution. (" + stat + "/" + viewModel.getConstMax() + "pc)."
            getString(R.string.mana) -> snackMsg = getString(R.string.win_msg) + " " + value + " points de mana. (" + stat + "/" + viewModel.getManaMax() + "pm)."
        }
        Snackbar.make(view!!, snackMsg, Snackbar.LENGTH_LONG).show()
        viewModel._lastMsg.value = snackMsg
    }

}
