package com.rphelper.cecib.rphelper.fragments


import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.DamageComponent
import com.rphelper.cecib.rphelper.viewmodel.FightViewModel
import kotlinx.android.synthetic.main.component_damage_calc.view.*
import kotlin.math.roundToInt


class FightFragment : Fragment() {
    private lateinit var viewModel: FightViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fight, container, false)

        viewModel = FightViewModel(context!!)

        view.findViewById<TextView>(R.id.fight_roll_dice).setOnClickListener {
            val random = Math.random()*100
            view.findViewById<TextView>(R.id.fight_roll_dice).text = random.toInt().toString()
        }

        //Calc damages
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageTitle.text = getString(R.string.damages)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton1.text = getString(R.string.brut_dmg)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton2.text = getString(R.string.elem_res_dmg)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton3.text = getString(R.string.block_dmg)
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton4.text = getString(R.string.weak_dmg)

        var dmg = 0
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when(true){
                    //Brut damages
                    view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton1.isChecked -> {
                        if(s.toString().isNotEmpty()) {
                            dmg = (s.toString().toInt() - viewModel.getDef()).toInt()
                        }
                    }
                    //Res elem
                    view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton2.isChecked -> {
                        if(s.toString().isNotEmpty()) {
                            dmg = (s.toString().toInt() * 0.2F).toInt()
                        }
                    }
                    //Block
                    view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton3.isChecked ->{
                        if(s.toString().isNotEmpty()) {
                            dmg = (s.toString().toInt() * viewModel.getBlock()).toInt()
                            dmg -= (viewModel.getDef()).toInt()
                        }
                    }
                    //Weak
                    view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageButton4.isChecked ->{
                        if(s.toString().isNotEmpty()) {
                            dmg = (s.toString().toInt() * 2)
                        }
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
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageSubmit.setOnClickListener { viewModel.submit(dmg)}

        //Calc heal
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageTitle.text = getString(R.string.recovery)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton1.text = getString(R.string.pv_min)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton2.text = getString(R.string.const_min)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton3.text = getString(R.string.mana_min)
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton4.visibility = View.GONE
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageResult.visibility = View.GONE
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageEqual.visibility = View.GONE

        var recovery = 0
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                recovery = s.toString().toInt()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        //Life
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton1.setOnCheckedChangeListener{
            button, b ->  if (view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton1.isChecked){
            view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageSubmit.setOnClickListener { viewModel.recoverLife(recovery)} }
        }
        //Const
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton2.setOnCheckedChangeListener{
            button, b ->  if (view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton2.isChecked){
            view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageSubmit.setOnClickListener { viewModel.recoverConst(recovery)} }
        }
        //Mana
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton3.setOnCheckedChangeListener{
            button, b ->  if (view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageButton3.isChecked){
            view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageSubmit.setOnClickListener { viewModel.recoverMana(recovery)} }
        }
        view.findViewById<DamageComponent>(R.id.fight_calc_recovery).damageSubmit.text = getString(R.string.recover)

        /********* ACTIONS *************/
        view.findViewById<Button>(R.id.fight_action_attack).setOnClickListener { viewModel.attackOrBlock() }
        view.findViewById<Button>(R.id.fight_action_twin).setOnClickListener { viewModel.twin() }
        view.findViewById<Button>(R.id.fight_action_block).setOnClickListener { viewModel.attackOrBlock() }
        view.findViewById<Button>(R.id.fight_action_dodge).setOnClickListener { viewModel.dodge() }
        view.findViewById<EditText>(R.id.fight_action_bleed_txt).setText(viewModel.bleed.value.toString())
        view.findViewById<Button>(R.id.fight_action_bleed).setOnClickListener {
            var damage = 0
            if (view.findViewById<EditText>(R.id.fight_action_bleed_txt).text.isNotBlank()) damage = view.findViewById<EditText>(R.id.fight_action_bleed_txt).text.toString().toInt()
            viewModel.bleed(damage) }
        view.findViewById<Button>(R.id.fight_action_poison).setOnClickListener { viewModel.poison() }
        if(viewModel.frost.value!!){
            view.findViewById<Button>(R.id.fight_action_frost).text = getString(R.string.annul_frost)
        }else{
            view.findViewById<Button>(R.id.fight_action_frost).text = getString(R.string.activ_frost)
        }
        view.findViewById<Button>(R.id.fight_action_frost).setOnClickListener {
            viewModel.frost()
            if(viewModel.frost.value!!){
                view.findViewById<Button>(R.id.fight_action_frost).text = getString(R.string.annul_frost)

            }else{
                view.findViewById<Button>(R.id.fight_action_frost).text = getString(R.string.activ_frost)
            }
        }



        return view
    }


}
