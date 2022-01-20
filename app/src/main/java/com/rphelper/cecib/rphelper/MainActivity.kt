package com.rphelper.cecib.rphelper


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
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
import com.rphelper.cecib.rphelper.viewmodel.MainViewModel
import org.json.JSONObject


class MainActivity : FragmentActivity() {
    private val WRITE_EXTERNAL_STORAGE_CODE = 1
    private val NUM_PAGES = 5
    private lateinit var mPager: ViewPager2

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
            loadData()
        }
    }

    fun initView(){
        /*val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = CharacterFragment()
        fragmentTransaction.add(R.id.fragment_container, fragment as Fragment)
        fragmentTransaction.commit()*/

        // Instantiate a ViewPager and a PagerAdapter.
       mPager = findViewById(R.id.fragment_container)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter
        mPager.currentItem = 0
        mPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> findViewById<BottomNavigationView>(R.id.navigation).selectedItemId = R.id.navigation_profil
                    1 -> findViewById<BottomNavigationView>(R.id.navigation).selectedItemId = R.id.navigation_equipments
                    2 -> findViewById<BottomNavigationView>(R.id.navigation).selectedItemId = R.id.navigation_fight
                    3 -> findViewById<BottomNavigationView>(R.id.navigation).selectedItemId = R.id.navigation_spells
                    4 -> findViewById<BottomNavigationView>(R.id.navigation).selectedItemId = R.id.navigation_inventory
                    else -> findViewById<BottomNavigationView>(R.id.navigation).selectedItemId = R.id.navigation_profil
                }
                super.onPageSelected(position)
            }
        })

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.isItemHorizontalTranslationEnabled = false
        navigation.labelVisibilityMode = LABEL_VISIBILITY_UNLABELED
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    /**
     * Exemple mais pas compatible menu ?
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> CharacterFragment()
                1 -> EquipmentFragment()
                2 -> FightFragment()
                3 -> SpellFragment()
                4 -> InventoryFragment()
                else -> CharacterFragment()
            }
        }
    }

    //Récup données en local
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
    }

    fun loadData(){
        val liveData = viewModel.getDataSnapshotLiveData()
        liveData!!.observe(this, Observer { dataSnapshot ->
            if (dataSnapshot != null && dataSnapshot.hasChildren()) {
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
            }else initDatabase()
        })
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profil -> {
                mPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_equipments -> {
                mPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_fight -> {
                mPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_spells -> {
                mPager.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_inventory -> {
                mPager.currentItem = 4
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
                    loadData()
                } else {
                    this.finish()
                }
                return
            }
        }
    }

    init{
        viewModel = MainViewModel(this)
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
