package com.rphelper.cecib.rphelper.fragments

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.IndicComponent
import com.rphelper.cecib.rphelper.viewmodel.CharacterViewModel

class StatsFragment : Fragment() {

    lateinit var viewModel : CharacterViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)

        viewModel = CharacterViewModel(context!!)

        //Name
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTitle.text = getString(R.string.name)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTxt.text = viewModel.character.value!!.name
        //Race
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTitle.text = getString(R.string.race)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTxt.text = viewModel.character.value!!.race
        //Origin
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTitle.text = getString(R.string.origin)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTxt.text = viewModel.character.value!!.origin
        //Religion
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTitle.text = getString(R.string.religion)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTxt.text = viewModel.character.value!!.religion
        //Level
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTitle.text = getString(R.string.level)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTxt.text = viewModel.character.value!!.level.toString()
        //Speed
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTitle.text = getString(R.string.speed)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTxt.text = viewModel.speed.value.toString()


        //Life
        view.findViewById<IndicComponent>(R.id.indic_life).indicTitle.text = getString(R.string.pv)
        view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.text = viewModel.character.value!!.life.value.toString()
        view.findViewById<IndicComponent>(R.id.indic_life).indicMax.text = viewModel.character.value!!.life.maxValue.toString()
        //Const
        view.findViewById<IndicComponent>(R.id.indic_const).indicTitle.text = getString(R.string.constitution)
        view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.text =  viewModel.character.value!!.const.value.toString()
        view.findViewById<IndicComponent>(R.id.indic_const).indicMax.text =  viewModel.character.value!!.const.maxValue.toString()
        //Mana
        view.findViewById<IndicComponent>(R.id.indic_mana).indicTitle.text = getString(R.string.mana)
        view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.text =  viewModel.character.value!!.mana.value.toString()
        view.findViewById<IndicComponent>(R.id.indic_mana).indicMax.text =  viewModel.character.value!!.mana.maxValue.toString()
        //Weight
        view.findViewById<IndicComponent>(R.id.indic_weight).indicTitle.text = getString(R.string.weight)
        view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.text =  viewModel.character.value!!.weight.value.toString()
        view.findViewById<IndicComponent>(R.id.indic_weight).indicMax.text =  viewModel.character.value!!.weight.maxValue.toString()

        /********* Stats ********/
        view.findViewById<TextView>(R.id.stat_vit).text = viewModel.character.value!!.vitality.toString()
        view.findViewById<TextView>(R.id.stat_vig).text = viewModel.character.value!!.vigor.toString()
        view.findViewById<TextView>(R.id.stat_for).text = viewModel.character.value!!.strength.toString()
        view.findViewById<TextView>(R.id.stat_dex).text = viewModel.character.value!!.dexterity.toString()
        view.findViewById<TextView>(R.id.stat_end).text = viewModel.character.value!!.endurance.toString()
        view.findViewById<TextView>(R.id.stat_mem).text = viewModel.character.value!!.memory.toString()
        view.findViewById<TextView>(R.id.stat_int).text = viewModel.character.value!!.intelligence.toString()
        view.findViewById<TextView>(R.id.stat_foi).text = viewModel.character.value!!.faith.toString()

        /********* Skills ******/
        //Diplo
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_diplo).catTitle.text = getString(R.string.skill_diplo)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_diplo).catTxt.text = viewModel.diplo.value.toString()
        //Psy
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_psy).catTitle.text = getString(R.string.skill_psy)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_psy).catTxt.text = viewModel.psy.value.toString()
        //Know
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_know).catTitle.text = getString(R.string.skill_know)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_know).catTxt.text = viewModel.know.value.toString()
        //Push
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_push).catTitle.text = getString(R.string.skill_push)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_push).catTxt.text = viewModel.push.value.toString()
        //Sneak
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_sneak).catTitle.text = getString(R.string.skill_sneak)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_sneak).catTxt.text = viewModel.sneak.value.toString()
        //Craft
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_craft).catTitle.text = getString(R.string.skill_craft)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_craft).catTxt.text = viewModel.craft.value.toString()


        //Don
        view.findViewById<IndicComponent>(R.id.don_cat).indicTitle.text = getString(R.string.don)
        view.findViewById<IndicComponent>(R.id.don_cat).indicCurrent.text = viewModel.character.value!!.don

        return view
    }



}
