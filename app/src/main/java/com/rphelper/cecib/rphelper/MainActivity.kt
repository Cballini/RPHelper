package com.rphelper.cecib.rphelper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle


import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rphelper.cecib.rphelper.fragments.*

class MainActivity : FragmentActivity() {
    private val WRITE_EXTERNAL_STORAGE_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    WRITE_EXTERNAL_STORAGE_CODE)
        }
        else{
            initView()
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
}
