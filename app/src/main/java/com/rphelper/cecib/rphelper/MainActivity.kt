package com.rphelper.cecib.rphelper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.MenuItem
import android.support.v4.app.FragmentActivity


import com.rphelper.cecib.rphelper.fragments.InventoryFragment
import com.rphelper.cecib.rphelper.fragments.EquipmentFragment
import com.rphelper.cecib.rphelper.fragments.FightFragment
import com.rphelper.cecib.rphelper.fragments.SpellFragment
import com.rphelper.cecib.rphelper.fragments.StatsFragment
import com.rphelper.cecib.rphelper.utils.KeyboardUtils

import android.support.design.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View

class MainActivity : FragmentActivity() {
    private val WRITE_EXTERNAL_STORAGE_CODE = 1

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_profil -> {
                val statFragment = StatsFragment()
                val statTransaction = supportFragmentManager.beginTransaction()
                statTransaction.replace(R.id.fragment_container, statFragment)
                statTransaction.addToBackStack(null)
                statTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_equipments -> {
                val equipFragment = EquipmentFragment()
                val equipTransaction = supportFragmentManager.beginTransaction()
                equipTransaction.replace(R.id.fragment_container, equipFragment)
                equipTransaction.addToBackStack(null)
                equipTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_spells -> {
                val spellFragment = SpellFragment()
                val spellTransaction = supportFragmentManager.beginTransaction()
                spellTransaction.replace(R.id.fragment_container, spellFragment)
                spellTransaction.addToBackStack(null)
                spellTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_inventory -> {
                val inventoryFragment = InventoryFragment()
                val bagTransaction = supportFragmentManager.beginTransaction()
                bagTransaction.replace(R.id.fragment_container, inventoryFragment)
                bagTransaction.addToBackStack(null)
                bagTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_fight -> {
                val fightFragment = FightFragment()
                val fightTransaction = supportFragmentManager.beginTransaction()
                fightTransaction.replace(R.id.fragment_container, fightFragment)
                fightTransaction.addToBackStack(null)
                fightTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

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
        val fragment = StatsFragment()
        fragmentTransaction.add(R.id.fragment_container, fragment)
        fragmentTransaction.commit()

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.isItemHorizontalTranslationEnabled = false
        navigation.labelVisibilityMode = LABEL_VISIBILITY_UNLABELED
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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
