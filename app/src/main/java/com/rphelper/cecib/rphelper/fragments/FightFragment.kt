package com.rphelper.cecib.rphelper.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.DamageComponent
import com.rphelper.cecib.rphelper.viewmodel.FightViewModel


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

        //Brut damage
        view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageType.text = getString(R.string.brut_dmg)
        var dmg = 0F
        view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNotEmpty()) {
                    dmg = s.toString().toInt() - viewModel.getDef()
                    view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageResult.text = dmg.toString()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageButton.setOnClickListener { viewModel.submit(dmg)}

        //Elem res damage
        view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageType.text = getString(R.string.elem_res_dmg)
        var dmgRes = 0F
        view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNotEmpty()) {
                    dmgRes = s.toString().toInt() * 0.2F
                    view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageResult.text = dmgRes.toString()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageButton.setOnClickListener { viewModel.submit(dmgRes) }

        //Block damage
        view.findViewById<DamageComponent>(R.id.fight_block_damage).damageType.text = getString(R.string.block_dmg)
        var dmgPar = 0F
        view.findViewById<DamageComponent>(R.id.fight_block_damage).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNotEmpty()) {
                    dmgPar = s.toString().toInt() * viewModel.getBlock()
                    dmgPar -= viewModel.getDef()
                    view.findViewById<DamageComponent>(R.id.fight_block_damage).damageResult.text = dmgPar.toString()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })

        view.findViewById<DamageComponent>(R.id.fight_block_damage).damageButton.setOnClickListener {viewModel.submit(dmgPar)}

        //Weak damage
        view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageType.text = getString(R.string.weak_dmg)
        var dmgWeak = 0F
        view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if(s.toString().isNotEmpty()) {
                    dmgWeak = (s.toString().toInt() * 2).toFloat()
                    view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageResult.text = dmgWeak.toString()
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageButton.setOnClickListener {viewModel.submit(dmgWeak)  }

        return view
    }


}
