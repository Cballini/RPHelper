package com.rphelper.cecib.rphelper.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.component.CategoryHorizontalComponent
import com.rphelper.cecib.rphelper.component.CategoryVerticalComponent
import com.rphelper.cecib.rphelper.component.IndicComponent
import com.rphelper.cecib.rphelper.dto.Character
import com.rphelper.cecib.rphelper.dto.Equipment
import com.rphelper.cecib.rphelper.utils.DisplayUtils
import com.rphelper.cecib.rphelper.viewmodel.CharacterViewModel
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import com.bumptech.glide.Glide


class CharacterFragment : Fragment() {

    lateinit var viewModel : CharacterViewModel
    var profileIsOnEdit = false
    var statIsOnEdit = false
    var donIsOnEdit = false
    val storageRef = Firebase.storage.reference
    val pathRef = storageRef.child("images/${FirebaseAuth.getInstance().currentUser!!.uid}") //profilePicture storage
    private val READ_EXTERNAL_STORAGE_CODE = 2
    private val REQUEST_CODE = 1000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)

        viewModel = CharacterViewModel(context!!)

        initViewNotEditable(view)

        val liveData = viewModel.getDataSnapshotLiveData()
        liveData!!.observe(viewLifecycleOwner, Observer { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModel._character.value = dataSnapshot.child("character").getValue(Character::class.java)
                viewModel._equipment.value = dataSnapshot.child("equipment").getValue(Equipment::class.java)
                viewModel._weight.value = viewModel.getWeight()
                viewModel._weightMax.value = viewModel.getWeightMax()
                viewModel._lifeMax.value = viewModel.getLifeMax()
                viewModel._manaMax.value = viewModel.getManaMax()
                viewModel._constMax.value = viewModel.getConstMax()
                viewModel._speed.value = viewModel.getSpeed()

                viewModel._diplo.value = viewModel.getDiplo()
                viewModel._psy.value = viewModel.getPsy()
                viewModel._know.value = viewModel.getKnow()
                viewModel._push.value = viewModel.getPush()
                viewModel._sneak.value = viewModel.getSneak()
                viewModel._craft.value = viewModel.getCraft()
            }
        })

        viewModel.character.observe(viewLifecycleOwner, Observer {
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_name).catTxt.setText(it.name)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_race).catTxt.setText(it.race)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_origin).catTxt.setText(it.origin)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_religion).catTxt.setText(it.religion)
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_level).catTxt.setText(it.level.toString())
            it.let {
                view.findViewById<IndicComponent>(R.id.indic_life).indicCurrent.text = it!!.life.value.toString()
                view.findViewById<IndicComponent>(R.id.indic_const).indicCurrent.text = it!!.const.value.toString()
                view.findViewById<IndicComponent>(R.id.indic_mana).indicCurrent.text = it!!.mana.value.toString()

                fillStatsWithBonus(view, it!!)

                if (it.don.isNotEmpty()) view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalCurrent.setText(it.don)
            }
        })

        //ProfilePicture
        pathRef.downloadUrl.addOnSuccessListener {uri ->
            Glide.with(this)
                    .load(uri.toString())
                    .into(view.findViewById<ImageView>(R.id.profile_picture))
        }
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
            view.findViewById<CategoryHorizontalComponent>(R.id.profile_speed).catTxt.setText(it.toString())})
        //Life
        view.findViewById<IndicComponent>(R.id.indic_life).indicTitle.text = getString(R.string.pv)
        setOnClickListenerIndicDrop(R.id.indic_life, view)
        viewModel.lifeMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_life).indicMax.text =viewModel.lifeMax.value.toString()
        })
        viewModel.lifeBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_life).indicBonus.text = DisplayUtils.stringBonus(viewModel.lifeBonus.value!!)
        })
        //Const
        view.findViewById<IndicComponent>(R.id.indic_const).indicTitle.text = getString(R.string.constitution)
        setOnClickListenerIndicDrop(R.id.indic_const, view)
        viewModel.constMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_const).indicMax.text = viewModel.constMax.value.toString()
        })
        viewModel.constBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_const).indicBonus.text = DisplayUtils.stringBonus(viewModel.constBonus.value!!)
        })
        //Mana
        view.findViewById<IndicComponent>(R.id.indic_mana).indicTitle.text = getString(R.string.mana)
        setOnClickListenerIndicDrop(R.id.indic_mana, view)
        viewModel.manaMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_mana).indicMax.text = viewModel.manaMax.value.toString()
        })
        viewModel.manaBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_mana).indicBonus.setText(DisplayUtils.stringBonus(viewModel.manaBonus.value!!))
        })
        //Weight
        view.findViewById<IndicComponent>(R.id.indic_weight).indicTitle.text = getString(R.string.weight)
        view.findViewById<IndicComponent>(R.id.indic_weight).indicReset.visibility = View.GONE
        view.findViewById<IndicComponent>(R.id.indic_weight).indicEdit.visibility = View.GONE
        setOnClickListenerIndicDrop(R.id.indic_weight, view)
        viewModel.weightMax.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_weight).indicMax.text =viewModel.weightMax.value.toString()
        })
        viewModel.weight.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_weight).indicCurrent.text =viewModel.weight.value.toString()
        })
        viewModel.weightBonus.observe(viewLifecycleOwner, Observer {
            view.findViewById<IndicComponent>(R.id.indic_weight).indicBonus.text =DisplayUtils.stringBonus(viewModel.weightBonus.value!!)
        })

        //Help
        view.findViewById<ImageView>(R.id.indic_help).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.help))
                setMessage(getString(R.string.indic_help))
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }
        view.findViewById<ImageView>(R.id.stat_help).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.help))
                setMessage(getString(R.string.stat_help))
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }

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

        //Help
        view.findViewById<ImageView>(R.id.skill_help).setOnClickListener {
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle(getString(R.string.help))
                setMessage(getString(R.string.skill_help))
                setNeutralButton(getString(R.string.ok)) { dialog, which -> dialog.cancel() }
                show()
            }
        }

        //Don
        view.findViewById<CategoryVerticalComponent>(R.id.don_cat).catVerticalTitle.text = getString(R.string.don)

        /************ EDIT ***********/
        view.findViewById<ImageView>(R.id.profile_picture).setOnClickListener {
            if(ActivityCompat.checkSelfPermission(activity!!,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(activity!!,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        READ_EXTERNAL_STORAGE_CODE)
            }
            else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE)
            }
        }

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
           displayEditIndicDialog(getString(R.string.lifeEditTitle))
        }
        view.findViewById<IndicComponent>(R.id.indic_life).indicReset.setOnClickListener {
            viewModel.character.value!!.life.value = viewModel.lifeMax.value!!.toFloat()
            viewModel.editCharacter()
        }
        view.findViewById<IndicComponent>(R.id.indic_life).indicEditBonus.setOnClickListener {
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.lifeBonusTxt), Preferences.PREF_MODIFIER_LIFE_MAX_TEMP, {viewModel.updateCharacterBonus()})
        }

        view.findViewById<IndicComponent>(R.id.indic_const).indicEdit.setOnClickListener {
            displayEditIndicDialog(getString(R.string.constEditTitle))
        }
        view.findViewById<IndicComponent>(R.id.indic_const).indicReset.setOnClickListener {
            viewModel.character.value!!.const.value = viewModel.constMax.value!!.toFloat()
            viewModel.editCharacter()
        }
        view.findViewById<IndicComponent>(R.id.indic_const).indicEditBonus.setOnClickListener {
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.constBonusTxt), Preferences.PREF_MODIFIER_CONST_MAX_TEMP, {viewModel.updateCharacterBonus()})
        }

        view.findViewById<IndicComponent>(R.id.indic_mana).indicEdit.setOnClickListener {
            displayEditIndicDialog(getString(R.string.manaEditTitle))
        }
        view.findViewById<IndicComponent>(R.id.indic_mana).indicReset.setOnClickListener {
            viewModel.character.value!!.mana.value = viewModel.manaMax.value!!.toFloat()
            viewModel.editCharacter()
        }
        view.findViewById<IndicComponent>(R.id.indic_mana).indicEditBonus.setOnClickListener {
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.manaBonusTxt), Preferences.PREF_MODIFIER_MANA_MAX_TEMP, {viewModel.updateCharacterBonus()})
        }

        view.findViewById<IndicComponent>(R.id.indic_weight).indicEditBonus.setOnClickListener {
            DisplayUtils.displayEditIndicBonusDialog(context!!, getString(R.string.weightBonusTxt), Preferences.PREF_MODIFIER_WEIGHT_MAX_TEMP, {viewModel.updateCharacterBonus()})
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
                fillStatsWithBonus(view, viewModel.character.value!!)
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
                fillStatsNoBonus(view)
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

    fun displayEditIndicDialog(title: String) {
        val builder = AlertDialog.Builder(context)
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_indic, null)
        builder.setView(dialogLayout)
        builder.setTitle(title)
        val txtView = dialogLayout.findViewById<TextView>(R.id.edit_indic_txt)
        txtView.text = context!!.getString(R.string.editIndicTxt)
        val editText = dialogLayout.findViewById<EditText>(R.id.edit_indic_value)

        builder.setPositiveButton(context!!.getString(R.string.ok)) { dialogInterface, i ->
            dialogInterface.dismiss()
            when(title){
                getString(R.string.lifeEditTitle) -> {
                    if (editText.text.isNotEmpty()){viewModel.character.value!!.life.value = editText.text.toString().toFloat()}
                }
                getString(R.string.manaEditTitle) -> {
                    if (editText.text.isNotEmpty()){viewModel.character.value!!.mana.value = editText.text.toString().toFloat()}
                }
                getString(R.string.constEditTitle) -> {
                    if (editText.text.isNotEmpty()){viewModel.character.value!!.const.value = editText.text.toString().toFloat()}
                }
            }
            viewModel.editCharacter()
        }

        builder.show()
    }

    fun fillStatsWithBonus(view: View, it : Character){
        val vitPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_VIT, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_VIT, 0)
        if(vitPref!=0) view.findViewById<TextView>(R.id.stat_vit).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(vitPref!=0) view.findViewById<TextView>(R.id.stat_vit).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_vit).text = (it.vitality + vitPref).toString()

        val vigPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_VIG, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_VIG, 0)
        if(vigPref!=0) view.findViewById<TextView>(R.id.stat_vig).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(vigPref!=0) view.findViewById<TextView>(R.id.stat_vig).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_vig).text = (it.vigor + vigPref).toString()

        val forPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_FOR, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_FOR, 0)
        if(forPref!=0) view.findViewById<TextView>(R.id.stat_for).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(forPref!=0) view.findViewById<TextView>(R.id.stat_for).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_for).text = (it.strength + forPref).toString()

        val dexPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_DEX, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_DEX, 0)
        if(dexPref!=0) view.findViewById<TextView>(R.id.stat_dex).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(dexPref!=0) view.findViewById<TextView>(R.id.stat_dex).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_dex).text = (it.dexterity + dexPref).toString()

        val endPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_END, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_END, 0)
        if(endPref!=0) view.findViewById<TextView>(R.id.stat_end).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(endPref!=0) view.findViewById<TextView>(R.id.stat_end).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_end).text = (it.endurance + endPref).toString()

        val memPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_MEM, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_MEM, 0)
        if(memPref!=0) view.findViewById<TextView>(R.id.stat_mem).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(memPref!=0) view.findViewById<TextView>(R.id.stat_mem).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_mem).text = (it.memory + memPref).toString()

        val intPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_INT, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_INT, 0)
        if(intPref!=0) view.findViewById<TextView>(R.id.stat_int).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(intPref!=0) view.findViewById<TextView>(R.id.stat_int).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_int).text = (it.intelligence + intPref).toString()

        val foiPref = context!!.getSharedPreferences(Preferences.PREF_MODIFIER_FOI, Preferences.PRIVATE_MODE).getInt(Preferences.PREF_MODIFIER_FOI, 0)
        if(foiPref!=0) view.findViewById<TextView>(R.id.stat_foi).setTextColor(resources.getColor(R.color.colorPrimaryDark))
        if(foiPref!=0) view.findViewById<TextView>(R.id.stat_foi).setTypeface(null, Typeface.BOLD);
        view.findViewById<TextView>(R.id.stat_foi).text = (it.faith + foiPref).toString()
    }

    fun fillStatsNoBonus(view: View){
        view.findViewById<ImageView>(R.id.stat_edit).setImageResource(R.drawable.ic_check)
        view.findViewById<TextView>(R.id.stat_vit).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_vit).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_vit).text =viewModel.character.value!!.vitality.toString()

        view.findViewById<TextView>(R.id.stat_vig).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_vig).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_vig).text =viewModel.character.value!!.vigor.toString()

        view.findViewById<TextView>(R.id.stat_for).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_for).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_for).text =viewModel.character.value!!.strength.toString()

        view.findViewById<TextView>(R.id.stat_dex).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_dex).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_dex).text =viewModel.character.value!!.dexterity.toString()

        view.findViewById<TextView>(R.id.stat_end).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_end).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_end).text =viewModel.character.value!!.endurance.toString()

        view.findViewById<TextView>(R.id.stat_mem).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_mem).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_mem).text =viewModel.character.value!!.memory.toString()

        view.findViewById<TextView>(R.id.stat_int).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_int).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_int).text =viewModel.character.value!!.intelligence.toString()

        view.findViewById<TextView>(R.id.stat_foi).setTextColor(resources.getColor(R.color.colorTxt))
        view.findViewById<TextView>(R.id.stat_foi).setTypeface(null, Typeface.NORMAL);
        view.findViewById<TextView>(R.id.stat_foi).text =viewModel.character.value!!.faith.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            var uri = data?.data

            view!!.findViewById<ImageView>(R.id.profile_picture).setImageURI(data?.data) // affichage

            //Store in the cloud
            // [START upload_with_metadata]
            // Create file metadata including the content type
            var metadata = storageMetadata {
                contentType = "image/jpg"
            }

            var file = uri!!
            var uploadTask = pathRef.putFile(file, metadata)

            uploadTask.addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                println("Upload is $progress% done")
            }.addOnFailureListener {
                Log.e("upload", "fail")
            }.addOnSuccessListener {
                Log.e("upload", "success")
                //TODO snackbar
            }
        }
    }
}

