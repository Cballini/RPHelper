package com.rphelper.cecib.rphelper


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.util.ExtraConstants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.rphelper.cecib.rphelper.dto.*
import com.rphelper.cecib.rphelper.fragments.*
import com.rphelper.cecib.rphelper.utils.DisplayUtils
import com.rphelper.cecib.rphelper.viewmodel.MainViewModel
import org.json.JSONObject


class MainActivity : FragmentActivity() {
    private val WRITE_EXTERNAL_STORAGE_CODE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startActivity(AuthActivity.createIntent(this))
            finish()
            return
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_CODE)
        }
        else{
           //setupUI(findViewById(R.id.fragment_container), this)
            initView()
            if(this.getSharedPreferences(Preferences.FIRST_CONNEXION, Preferences.PRIVATE_MODE).getBoolean(Preferences.FIRST_CONNEXION, true)) {
                initDatabase()
            }
            loadData()
        }
    }

    fun initView(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = CharacterFragment()
        fragmentTransaction.add(R.id.fragment_container, fragment as Fragment)
        fragmentTransaction.commit()

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.isItemHorizontalTranslationEnabled = false
        navigation.labelVisibilityMode = LABEL_VISIBILITY_UNLABELED
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun initDatabase(){
        val database = Firebase.database
        //character
        val charRef = database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/character")
        val char = Services.getJsonCharacter(applicationContext)
        charRef.setValue(char)

        //equipment
        val equipmentRef = database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/equipment")
        val equipment = Services.getJsonEquipment(applicationContext)
        equipmentRef.setValue(equipment)

        //fight
        val fightRef = database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/fight")
        val fight = Services.getJsonFight(applicationContext)
        fightRef.setValue(fight)

        //spells
        val spellsRef = database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/spells")
        val spells = Services.getJsonSpells(applicationContext)
        spellsRef.setValue(spells)

        //inventory
        val inventoryRef = database.getReference("user/" + FirebaseAuth.getInstance().currentUser!!.uid +"/inventory")
        val inventory = Services.getJsonInventory(applicationContext)
        inventoryRef.setValue(inventory)

        //update pref
        val sharedPref: SharedPreferences = this.getSharedPreferences(Preferences.FIRST_CONNEXION, Preferences.PRIVATE_MODE)
        sharedPref.edit().putBoolean(Preferences.FIRST_CONNEXION, false).apply()

    }

    fun loadData(){
        viewModel = MainViewModel(applicationContext)
        val liveData = viewModel.getDataSnapshotLiveData()
        liveData!!.observe(this, Observer { dataSnapshot ->
            if (dataSnapshot != null) {
                viewModel._character.value = dataSnapshot.child("character").getValue(Character::class.java)
                viewModel._equipment.value = dataSnapshot.child("equipment").getValue(Equipment::class.java)
                viewModel._fight.value = dataSnapshot.child("fight").getValue(Fight::class.java)
                viewModel._inventory.value = dataSnapshot.child("inventory").getValue(Inventory::class.java)

                var allStuff = java.util.ArrayList<Any>()
                //Weapon
                var valueWeapon = java.util.ArrayList<java.util.HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("weapons").value?.let{
                    valueWeapon = dataSnapshot.child("inventory").child("weapons").value as java.util.ArrayList<java.util.HashMap<String, Any>>
                    for (_value in valueWeapon) {
                        // Convert HashMap to Weapon
                        val jsonWeapon = JSONObject(_value as Map<*, *>).toString()
                        val weapon = Gson().fromJson<Weapon>(jsonWeapon, Weapon::class.java)
                        allStuff.add(weapon)
                    }
                }

                //Shield
                var valueShield = java.util.ArrayList<java.util.HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("shields").value?.let{
                    valueShield = dataSnapshot.child("inventory").child("shields").value as java.util.ArrayList<java.util.HashMap<String, Any>>
                    for (_value in valueShield) {
                        // Convert HashMap to Shield
                        val jsonShield = JSONObject(_value as Map<*, *>).toString()
                        val shield = Gson().fromJson<Shield>(jsonShield, Shield::class.java)
                        allStuff.add(shield)
                    }
                }

                //Armor
                var valueArmor = java.util.ArrayList<java.util.HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("armors").value?.let{
                    valueArmor = dataSnapshot.child("inventory").child("armors").value as java.util.ArrayList<java.util.HashMap<String, Any>>
                    for (_value in valueArmor) {
                        // Convert HashMap to Armor
                        val jsonArmor = JSONObject(_value as Map<*, *>).toString()
                        val armor = Gson().fromJson<Armor>(jsonArmor, Armor::class.java)
                        allStuff.add(armor)
                    }
                }
                //Jewel
                var valueJewel = java.util.ArrayList<java.util.HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("jewels").value?.let{
                    valueJewel = dataSnapshot.child("inventory").child("jewels").value as java.util.ArrayList<java.util.HashMap<String, Any>>
                    for (_value in valueJewel) {
                        // Convert HashMap to Jewel
                        val jsonJewel = JSONObject(_value as Map<*, *>).toString()
                        val jewel = Gson().fromJson<Jewel>(jsonJewel, Jewel::class.java)
                        allStuff.add(jewel)
                    }
                }

                //Item
                var valueItem = java.util.ArrayList<java.util.HashMap<String, Any>>()
                dataSnapshot.child("inventory").child("item").value?.let{
                    valueItem = dataSnapshot.child("inventory").child("item").value as java.util.ArrayList<java.util.HashMap<String, Any>>
                    for (_value in valueItem) {
                        // Convert HashMap to Item
                        val jsonItem = JSONObject(_value as Map<*, *>).toString()
                        val item = Gson().fromJson<Item>(jsonItem, Item::class.java)
                        allStuff.add(item)
                    }
                }

                viewModel._stuff.value = allStuff

                var allSpells = ArrayList<Spell>()
                val value = dataSnapshot.child("spells").value as ArrayList<HashMap<String, Any>>
                for (_value in value) {
                    // Convert HashMap to Spell
                    val jsonSpell = JSONObject(_value as Map<*, *>).toString()
                    val spell = Gson().fromJson<Spell>(jsonSpell, Spell::class.java)
                    allSpells.add(spell)
                }
                viewModel._allSpells.value = allSpells

                viewModel._catalyst.value = dataSnapshot.child("equipment").child("catalyst").getValue(Weapon::class.java)
            }
        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profil -> {
                val charFragment = CharacterFragment()
                val statTransaction = supportFragmentManager.beginTransaction()
                statTransaction.replace(R.id.fragment_container, charFragment as Fragment)
                statTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_equipments -> {
                val equipFragment = EquipmentFragment()
                val equipTransaction = supportFragmentManager.beginTransaction()
                equipTransaction.replace(R.id.fragment_container, equipFragment as Fragment)
                equipTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_spells -> {
                val spellFragment = SpellFragment()
                val spellTransaction = supportFragmentManager.beginTransaction()
                spellTransaction.replace(R.id.fragment_container, spellFragment as Fragment)
                spellTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_inventory -> {
                val inventoryFragment = InventoryFragment()
                val bagTransaction = supportFragmentManager.beginTransaction()
                bagTransaction.replace(R.id.fragment_container, inventoryFragment as Fragment)
                bagTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_fight -> {
                val fightFragment = FightFragment()
                val fightTransaction = supportFragmentManager.beginTransaction()
                fightTransaction.replace(R.id.fragment_container, fightFragment as Fragment)
                fightTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                    initView()
                } else {
                    this.finish()
                }
                return
            }
        }
    }

    companion object{
        lateinit var viewModel : MainViewModel
        @JvmStatic
        open fun createIntent(context: Context, response: IdpResponse?): Intent? {
            return Intent().setClass(context, MainActivity::class.java)
                    .putExtra(ExtraConstants.IDP_RESPONSE, response)
        }

        /*@JvmStatic
        fun setupUI(view: View, activity: Activity) {
            // Set up touch listener for non-text box views to hide keyboard.
            if (view !is EditText) {
                view.setOnTouchListener(OnTouchListener { v, event ->
                    DisplayUtils.hideSoftKeyboard(activity)
                    false
                })
            }

            //If a layout container, iterate over children and seed recursion.
            if (view is ViewGroup) {
                for (i in 0 until (view as ViewGroup).childCount) {
                    val innerView: View = (view as ViewGroup).getChildAt(i)
                    setupUI(innerView, activity)
                }
            }
        }*/
    }
}
