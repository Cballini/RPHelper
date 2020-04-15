package com.rphelper.cecib.rphelper;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.support.v4.app.FragmentActivity;


import com.rphelper.cecib.rphelper.fragments.InventoryFragment;
import com.rphelper.cecib.rphelper.fragments.EquipmentFragment;
import com.rphelper.cecib.rphelper.fragments.FightFragment;
import com.rphelper.cecib.rphelper.fragments.SpellFragment;
import com.rphelper.cecib.rphelper.fragments.StatsFragment;

import static android.support.design.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED;

public class MainActivity extends FragmentActivity{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profil:
                    StatsFragment statFragment = new StatsFragment();
                    FragmentTransaction statTransaction = getSupportFragmentManager().beginTransaction();
                    statTransaction.replace(R.id.fragment_container, statFragment);
                    statTransaction.addToBackStack(null);
                    statTransaction.commit();
                    return true;
                case R.id.navigation_equipments:
                    EquipmentFragment equipFragment = new EquipmentFragment();
                    FragmentTransaction equipTransaction = getSupportFragmentManager().beginTransaction();
                    equipTransaction.replace(R.id.fragment_container, equipFragment);
                    equipTransaction.addToBackStack(null);
                    equipTransaction.commit();
                    return true;
                case R.id.navigation_spells:
                    SpellFragment spellFragment = new SpellFragment();
                    FragmentTransaction spellTransaction = getSupportFragmentManager().beginTransaction();
                    spellTransaction.replace(R.id.fragment_container, spellFragment);
                    spellTransaction.addToBackStack(null);
                    spellTransaction.commit();
                    return true;
                case R.id.navigation_inventory:
                    InventoryFragment inventoryFragment = new InventoryFragment();
                    FragmentTransaction bagTransaction = getSupportFragmentManager().beginTransaction();
                    bagTransaction.replace(R.id.fragment_container, inventoryFragment);
                    bagTransaction.addToBackStack(null);
                    bagTransaction.commit();
                    return true;
                case R.id.navigation_fight:
                    FightFragment fightFragment = new FightFragment();
                    FragmentTransaction fightTransaction = getSupportFragmentManager().beginTransaction();
                    fightTransaction.replace(R.id.fragment_container, fightFragment);
                    fightTransaction.addToBackStack(null);
                    fightTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StatsFragment fragment = new StatsFragment();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setItemHorizontalTranslationEnabled(false);
        navigation.setLabelVisibilityMode(LABEL_VISIBILITY_UNLABELED);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
