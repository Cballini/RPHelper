package com.rphelper.cecib.rphelper.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.DamageComponent


class FightFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fight, container, false)

        view.findViewById<TextView>(R.id.fight_roll_dice).setOnClickListener {
            val random = Math.random()*100
            view.findViewById<TextView>(R.id.fight_roll_dice).text = random.toInt().toString()
        }

        //Brut damage
        view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageType.text = getString(R.string.brut_dmg)
        //view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageReceived
        view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageResult.text = "100"
        view.findViewById<DamageComponent>(R.id.fight_brut_damage).damageButton.setOnClickListener {  }

        //Elem res damage
        view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageType.text = getString(R.string.elem_res_dmg)
        //view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageReceived
        view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageResult.text = "100"
        view.findViewById<DamageComponent>(R.id.fight_elem_res_damage).damageButton.setOnClickListener {  }

        //Block damage
        view.findViewById<DamageComponent>(R.id.fight_block_damage).damageType.text = getString(R.string.block_dmg)
        //view.findViewById<DamageComponent>(R.id.fight_block_damage).damageReceived
        view.findViewById<DamageComponent>(R.id.fight_block_damage).damageResult.text = "100"
        view.findViewById<DamageComponent>(R.id.fight_block_damage).damageButton.setOnClickListener {  }

        //Weak damage
        view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageType.text = getString(R.string.weak_dmg)
        //view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageReceived
        view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageResult.text = "100"
        view.findViewById<DamageComponent>(R.id.fight_weak_damage).damageButton.setOnClickListener {  }

        return view
    }


}
