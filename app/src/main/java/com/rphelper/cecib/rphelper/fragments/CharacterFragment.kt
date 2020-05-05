package com.rphelper.cecib.rphelper.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.CategoryVerticalComponent
import com.rphelper.cecib.rphelper.component.IndicComponent
import com.rphelper.cecib.rphelper.viewmodel.CharacterViewModel

class CharacterFragment : Fragment() {

    lateinit var viewModel : CharacterViewModel
    var profileIsOnEdit = false
    var lifeIsOnEdit = false
    var constIsOnEdit = false
    var manaIsOnEdit = false
    var weightIsOnEdit = false
    var statIsOnEdit = false
    var donIsOnEdit = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)

        viewModel = CharacterViewModel(context!!)

        initViewNotEditable(view)

        viewModel.character.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTxt.setText(it!!.name)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTxt.setText(it!!.race)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTxt.setText(it!!.origin)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTxt.setText(it!!.religion)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTxt.setText(it!!.level.toString())

            view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.setText(it!!.life.value.toString())
            view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.setText(it!!.const.value.toString())
            view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.setText(it!!.mana.value.toString())

            view.findViewById<TextView>(R.id.stat_vit).text = it!!.vitality.toString()
            view.findViewById<TextView>(R.id.stat_vig).text = it!!.vigor.toString()
            view.findViewById<TextView>(R.id.stat_for).text = it!!.strength.toString()
            view.findViewById<TextView>(R.id.stat_dex).text = it!!.dexterity.toString()
            view.findViewById<TextView>(R.id.stat_end).text = it!!.endurance.toString()
            view.findViewById<TextView>(R.id.stat_mem).text = it!!.memory.toString()
            view.findViewById<TextView>(R.id.stat_int).text = it!!.intelligence.toString()
            view.findViewById<TextView>(R.id.stat_foi).text = it!!.faith.toString()

            if(it!!.don.isNotEmpty())view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalCurrent.setText(it!!.don)
        })

        //Name
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTitle.text = getString(R.string.name)
        //Race
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTitle.text = getString(R.string.race)
        //Origin
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTitle.text = getString(R.string.origin)
        //Religion
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTitle.text = getString(R.string.religion)
        //Level
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTitle.text = getString(R.string.level)
        //Speed
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTitle.text = getString(R.string.speed)
        viewModel.speed.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTxt.setText(it.toString()) })
        //Life
        view.findViewById<IndicComponent>(R.id.indic_life).indicTitle.text = getString(R.string.pv)
        setOnClickListenerIndicDrop(R.id.indic_life, view)
        viewModel.lifeMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_life).indicMax.setText(viewModel.lifeMax.value.toString())
        })
        //Const
        view.findViewById<IndicComponent>(R.id.indic_const).indicTitle.text = getString(R.string.constitution)
        setOnClickListenerIndicDrop(R.id.indic_const, view)
        viewModel.constMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_const).indicMax.setText(viewModel.constMax.value.toString())
        })
        //Mana
        view.findViewById<IndicComponent>(R.id.indic_mana).indicTitle.text = getString(R.string.mana)
        setOnClickListenerIndicDrop(R.id.indic_mana, view)
        viewModel.manaMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_mana).indicMax.setText(viewModel.manaMax.value.toString())
        })
        //Weight
        view.findViewById<IndicComponent>(R.id.indic_weight).indicTitle.text = getString(R.string.weight)
        view.findViewById<IndicComponent>(R.id.indic_weight).indicReset.visibility = View.GONE
        setOnClickListenerIndicDrop(R.id.indic_weight, view)
        viewModel.weightMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_weight).indicMax.setText(viewModel.weightMax.value.toString())
        })
        viewModel.weight.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.setText(viewModel.weight.value.toString())
        })

        /********* Skills ******/
        //Diplo
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_diplo).catTitle.text = getString(R.string.skill_diplo)
        viewModel.diplo.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.skill_diplo).catTxt.setText(it.toString())
        })
        //Psy
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_psy).catTitle.text = getString(R.string.skill_psy)
        viewModel.psy.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.skill_psy).catTxt.setText(it.toString())
        })
        //Know
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_know).catTitle.text = getString(R.string.skill_know)
        viewModel.know.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.skill_know).catTxt.setText(it.toString())
        })
        //Push
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_push).catTitle.text = getString(R.string.skill_push)
        viewModel.push.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.skill_push).catTxt.setText(it.toString())
        })
        //Sneak
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_sneak).catTitle.text = getString(R.string.skill_sneak)
        viewModel.sneak.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.skill_sneak).catTxt.setText(it.toString())
        })
        //Craft
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_craft).catTitle.text = getString(R.string.skill_craft)
        viewModel.craft.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.skill_craft).catTxt.setText(it.toString())
        })

        //Don
        view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalTitle.text = getString(R.string.don)

        /************ EDIT ***********/
        view.findViewById<ImageView>(R.id.profile_edit).setOnClickListener {
            val nameView = view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTxt
            val raceView = view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTxt
            val originView = view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTxt
            val religionView = view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTxt
            val levelView = view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTxt
            if (profileIsOnEdit) {
                profileIsOnEdit = false
                viewModel.character.value!!.name = nameView.text.toString()
                viewModel.character.value!!.race = raceView.text.toString()
                viewModel.character.value!!.origin = originView.text.toString()
                viewModel.character.value!!.religion = religionView.text.toString()
                viewModel.character.value!!.level = if(levelView.text.toString().isNotEmpty())levelView.text.toString().toInt() else 0
                view.findViewById<ImageView>(R.id.profile_edit).setImageResource(R.drawable.ic_edit)
                nameView.setEnabled(false)
                raceView.setEnabled(false)
                originView.setEnabled(false)
                religionView.setEnabled(false)
                levelView.setEnabled(false)
                viewModel.editCharacter()
            }else{
                profileIsOnEdit = true
                view.findViewById<ImageView>(R.id.profile_edit).setImageResource(R.drawable.ic_check)
                nameView.setEnabled(true)
                raceView.setEnabled(true)
                originView.setEnabled(true)
                religionView.setEnabled(true)
                levelView.setEnabled(true)

                levelView.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }

        view.findViewById<IndicComponent>(R.id.indic_life).indicEdit.setOnClickListener {
            if (lifeIsOnEdit){
                lifeIsOnEdit = false
                viewModel.character.value!!.life.value = if(view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.text.toString().isNotEmpty())
                    view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.text.toString().toFloat() else 0F
                view.findViewById<IndicComponent>(R.id.indic_life).indicEdit.setImageResource(R.drawable.ic_edit)
                view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.setEnabled(false)
                viewModel.editCharacter()
            }else{
                lifeIsOnEdit = true
                view.findViewById<IndicComponent>(R.id.indic_life).indicEdit.setImageResource(R.drawable.ic_check)
                view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.setEnabled(true)
                view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.setCursorVisible(true)
            }
        }
        view.findViewById<IndicComponent>(R.id.indic_life).indicReset.setOnClickListener {
            viewModel.character.value!!.life.value = viewModel.lifeMax.value!!.toFloat()
            viewModel.editCharacter()
        }

        view.findViewById<IndicComponent>(R.id.indic_const).indicEdit.setOnClickListener {
            if (constIsOnEdit){
                constIsOnEdit = false
                viewModel.character.value!!.const.value = if (view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.text.toString().isNotEmpty())
                    view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.text.toString().toFloat() else 0F
                view.findViewById<IndicComponent>(R.id.indic_const).indicEdit.setImageResource(R.drawable.ic_edit)
                view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.setEnabled(false)
                viewModel.editCharacter()
            }else{
                constIsOnEdit = true
                view.findViewById<IndicComponent>(R.id.indic_const).indicEdit.setImageResource(R.drawable.ic_check)
                view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.setEnabled(true)
                view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
        view.findViewById<IndicComponent>(R.id.indic_const).indicReset.setOnClickListener {
            viewModel.character.value!!.const.value = viewModel.constMax.value!!.toFloat()
            viewModel.editCharacter()
        }

        view.findViewById<IndicComponent>(R.id.indic_mana).indicEdit.setOnClickListener {
            if (manaIsOnEdit){
                manaIsOnEdit = false
                viewModel.character.value!!.mana.value = if(view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.text.toString().isNotEmpty())
                    view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.text.toString().toFloat() else 0F
                view.findViewById<IndicComponent>(R.id.indic_mana).indicEdit.setImageResource(R.drawable.ic_edit)
                view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.setEnabled(false)
                viewModel.editCharacter()
            }else{
                manaIsOnEdit = true
                view.findViewById<IndicComponent>(R.id.indic_mana).indicEdit.setImageResource(R.drawable.ic_check)
                view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.setEnabled(true)
                view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
        view.findViewById<IndicComponent>(R.id.indic_mana).indicReset.setOnClickListener {
            viewModel.character.value!!.mana.value = viewModel.manaMax.value!!.toFloat()
            viewModel.editCharacter()
        }

        view.findViewById<IndicComponent>(R.id.indic_weight).indicEdit.setOnClickListener {
            if (weightIsOnEdit){
                weightIsOnEdit = false
                viewModel._weight.value = if (view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.text.toString().isNotEmpty())
                    view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.text.toString().toFloat() else 0F
                view.findViewById<IndicComponent>(R.id.indic_weight).indicEdit.setImageResource(R.drawable.ic_edit)
                view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.setEnabled(false)
                viewModel.editCharacter()
            }else{
                weightIsOnEdit = true
                view.findViewById<IndicComponent>(R.id.indic_weight).indicEdit.setImageResource(R.drawable.ic_check)
                view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.setEnabled(true)
                view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }

        view.findViewById<ImageView>(R.id.stat_edit).setOnClickListener {
            if (statIsOnEdit){
                statIsOnEdit = false
                viewModel.character.value!!.vitality = if (view.findViewById<TextView>(R.id.stat_vit).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_vit).text.toString().toInt() else 0
                viewModel.character.value!!.vigor = if(view.findViewById<TextView>(R.id.stat_vig).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_vig).text.toString().toInt() else 0
                viewModel.character.value!!.strength = if (view.findViewById<TextView>(R.id.stat_for).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_for).text.toString().toInt() else 0
                viewModel.character.value!!.dexterity = if (view.findViewById<TextView>(R.id.stat_dex).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_dex).text.toString().toInt() else 0
                viewModel.character.value!!.endurance = if (view.findViewById<TextView>(R.id.stat_end).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_end).text.toString().toInt() else 0
                viewModel.character.value!!.memory = if (view.findViewById<TextView>(R.id.stat_mem).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_mem).text.toString().toInt() else 0
                viewModel.character.value!!.intelligence = if (view.findViewById<TextView>(R.id.stat_int).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_int).text.toString().toInt() else 0
                viewModel.character.value!!.faith = if(view.findViewById<TextView>(R.id.stat_foi).text.toString().isNotEmpty())
                    view.findViewById<TextView>(R.id.stat_foi).text.toString().toInt() else 0

                view.findViewById<ImageView>(R.id.stat_edit).setImageResource(R.drawable.ic_edit)
                view.findViewById<TextView>(R.id.stat_vit).setEnabled(false)
                view.findViewById<TextView>(R.id.stat_vig).setEnabled(false)
                view.findViewById<TextView>(R.id.stat_for).setEnabled(false)
                view.findViewById<TextView>(R.id.stat_dex).setEnabled(false)
                view.findViewById<TextView>(R.id.stat_end).setEnabled(false)
                view.findViewById<TextView>(R.id.stat_mem).setEnabled(false)
                view.findViewById<TextView>(R.id.stat_int).setEnabled(false)
                view.findViewById<TextView>(R.id.stat_foi).setEnabled(false)
                viewModel.editCharacter()
            }else{
                statIsOnEdit = true
                view.findViewById<ImageView>(R.id.stat_edit).setImageResource(R.drawable.ic_check)
                view.findViewById<TextView>(R.id.stat_vit).setEnabled(true)
                view.findViewById<TextView>(R.id.stat_vig).setEnabled(true)
                view.findViewById<TextView>(R.id.stat_for).setEnabled(true)
                view.findViewById<TextView>(R.id.stat_dex).setEnabled(true)
                view.findViewById<TextView>(R.id.stat_end).setEnabled(true)
                view.findViewById<TextView>(R.id.stat_mem).setEnabled(true)
                view.findViewById<TextView>(R.id.stat_int).setEnabled(true)
                view.findViewById<TextView>(R.id.stat_foi).setEnabled(true)
            }
        }

        view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalEdit.setOnClickListener {
            if (donIsOnEdit){
                donIsOnEdit = false
                viewModel.character.value!!.don = view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalCurrent.text.toString()
                view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalEdit.setImageResource(R.drawable.ic_edit)
                view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalCurrent.setEnabled(false)
                viewModel.editCharacter()
            }else{
                donIsOnEdit = true
                view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalEdit.setImageResource(R.drawable.ic_check)
                view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalCurrent.hint = "Entrez vos dons"
                view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalCurrent.setEnabled(true)
            }
        }

        return view
    }

    fun initViewNotEditable(view: View) {
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTxt.setEnabled(false)

        view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.setEnabled(false)
        view.findViewById<IndicComponent>(R.id.indic_life).indicMax.setEnabled(false)
        view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.setEnabled(false)
        view.findViewById<IndicComponent>(R.id.indic_const).indicMax.setEnabled(false)
        view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.setEnabled(false)
        view.findViewById<IndicComponent>(R.id.indic_mana).indicMax.setEnabled(false)
        view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.setEnabled(false)
        view.findViewById<IndicComponent>(R.id.indic_weight).indicMax.setEnabled(false)

        view.findViewById<CategoryHorizontalComponent>(R.id.skill_diplo).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_psy).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_know).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_push).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_sneak).catTxt.setEnabled(false)
        view.findViewById<CategoryHorizontalComponent>(R.id.skill_craft).catTxt.setEnabled(false)

        view.findViewById<TextView>(R.id.stat_vit).setEnabled(false)
        view.findViewById<TextView>(R.id.stat_vig).setEnabled(false)
        view.findViewById<TextView>(R.id.stat_for).setEnabled(false)
        view.findViewById<TextView>(R.id.stat_dex).setEnabled(false)
        view.findViewById<TextView>(R.id.stat_end).setEnabled(false)
        view.findViewById<TextView>(R.id.stat_mem).setEnabled(false)
        view.findViewById<TextView>(R.id.stat_int).setEnabled(false)
        view.findViewById<TextView>(R.id.stat_foi).setEnabled(false)

        view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalCurrent.setEnabled(false)
    }

    fun setOnClickListenerIndicDrop(id:Int, view: View){
        view.findViewById<IndicComponent>(id).indicDrop.setOnClickListener {
            if(view.findViewById<IndicComponent>(id).indicButtonsLayout.visibility == View.GONE){
                view.findViewById<IndicComponent>(id).indicButtonsLayout.visibility = View.VISIBLE
                view.findViewById<IndicComponent>(id).indicDrop.setImageResource(R.drawable.ic_arrow_drop_up)
            }else{
                view.findViewById<IndicComponent>(id).indicButtonsLayout.visibility = View.GONE
                view.findViewById<IndicComponent>(id).indicDrop.setImageResource(R.drawable.ic_arrow_drop_down)
            }
        }
    }
}

