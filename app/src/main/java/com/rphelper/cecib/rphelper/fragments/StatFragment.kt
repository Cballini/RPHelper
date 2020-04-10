package com.rphelper.cecib.rphelper.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.IndicComponent

class StatsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stats, container, false)

        //Name
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTitle.text = getString(R.string.name)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTxt.text = "Till"
        //Race
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTitle.text = getString(R.string.race)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTxt.text = "Humaine"
        //Origin
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTitle.text = getString(R.string.origin)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTxt.text = "Oolacile"
        //Religion
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTitle.text = getString(R.string.religion)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTxt.text = "Les arbres"
        //Level
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTitle.text = getString(R.string.level)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTxt.text = "4"
        //Speed
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTitle.text = getString(R.string.speed)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTxt.text = "25"


        //Life
        view.findViewById<IndicComponent>(R.id.indic_life).indicTitle.text = getString(R.string.pv)
        view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.text = "300"
        view.findViewById<IndicComponent>(R.id.indic_life).indicMax.text = "400"
        //Const
        view.findViewById<IndicComponent>(R.id.indic_const).indicTitle.text = getString(R.string.constitution)
        view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.text = "250"
        view.findViewById<IndicComponent>(R.id.indic_const).indicMax.text = "260"
        //Mana
        view.findViewById<IndicComponent>(R.id.indic_mana).indicTitle.text = getString(R.string.mana)
        view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.text = "140"
        view.findViewById<IndicComponent>(R.id.indic_mana).indicMax.text = "150"
        //Weight
        view.findViewById<IndicComponent>(R.id.indic_weight).indicTitle.text = getString(R.string.weight)
        view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.text = "24.5"
        view.findViewById<IndicComponent>(R.id.indic_weight).indicMax.text = "47"

        /********* Stats ********/
        view.findViewById<TextView>(R.id.stat_vit).text = "10"
        view.findViewById<TextView>(R.id.stat_vig).text = "10"
        view.findViewById<TextView>(R.id.stat_for).text = "10"
        view.findViewById<TextView>(R.id.stat_dex).text = "10"
        view.findViewById<TextView>(R.id.stat_end).text = "10"
        view.findViewById<TextView>(R.id.stat_mem).text = "10"
        view.findViewById<TextView>(R.id.stat_int).text = "10"
        view.findViewById<TextView>(R.id.stat_foi).text = "10"

        /********* Skills ******/
        //Diplo
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_diplo).catTitle.text = getString(R.string.skill_diplo)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_diplo).catTxt.text = "51"
        //Psy
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_psy).catTitle.text = getString(R.string.skill_psy)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_psy).catTxt.text = "51"
        //Know
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_know).catTitle.text = getString(R.string.skill_know)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_know).catTxt.text = "51"
        //Push
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_push).catTitle.text = getString(R.string.skill_push)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_push).catTxt.text = "51"
        //Sneak
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_sneak).catTitle.text = getString(R.string.skill_sneak)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_sneak).catTxt.text = "51"
        //Craft
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_craft).catTitle.text = getString(R.string.skill_craft)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_craft).catTxt.text = "51"


        //Don
        view.findViewById<IndicComponent>(R.id.don_cat).indicTitle.text = getString(R.string.don)
        view.findViewById<IndicComponent>(R.id.don_cat).indicCurrent.text = "Alliée de la nature : peut faire du thé de Till en trouvant des ingrédients dans la nature."

        return view
    }



}
