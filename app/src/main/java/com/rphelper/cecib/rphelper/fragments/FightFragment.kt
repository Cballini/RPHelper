package com.rphelper.cecib.rphelper.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.DamageComponent
import com.rphelper.cecib.rphelper.viewmodel.FightViewModel
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
        view.findViewById<TextView>(R.id.damage_title).text = getString(R.string.damages)
        view.findViewById<RadioButton>(R.id.damage_button1).text = getString(R.string.brut_dmg)
        view.findViewById<RadioButton>(R.id.damage_button2).text = getString(R.string.elem_res_dmg)
        view.findViewById<RadioButton>(R.id.damage_button3).text = getString(R.string.block_dmg)
        view.findViewById<RadioButton>(R.id.damage_button4).text = getString(R.string.weak_dmg)

        var dmg = 0F
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageReceived.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when(true){
                    //Brut damages
                    view.findViewById<RadioButton>(R.id.damage_button1).isChecked -> {
                        if(s.toString().isNotEmpty()) {
                            dmg = s.toString().toInt() - viewModel.getDef()
                        }
                    }
                    //Res elem
                    view.findViewById<RadioButton>(R.id.damage_button2).isChecked -> {
                        if(s.toString().isNotEmpty()) {
                            dmg = s.toString().toInt() * 0.2F
                        }
                    }
                    //Block
                    view.findViewById<RadioButton>(R.id.damage_button3).isChecked ->{
                        if(s.toString().isNotEmpty()) {
                            dmg = s.toString().toInt() * viewModel.getBlock()
                            dmg -= viewModel.getDef()
                        }
                    }
                    //Weak
                    view.findViewById<RadioButton>(R.id.damage_button4).isChecked ->{
                        if(s.toString().isNotEmpty()) {
                            dmg = (s.toString().toInt() * 2).toFloat()
                        }
                    }
                }
                view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageResult.text = dmg.toInt().toString()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {}
        })
        view.findViewById<DamageComponent>(R.id.fight_calc_damage).damageSubmit.setOnClickListener { viewModel.submit(dmg)}

        return view
    }


}
