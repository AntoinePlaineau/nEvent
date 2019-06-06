package com.example.antoi.nevent.Front;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.antoi.nevent.DAO.BDHelper;
import com.example.antoi.nevent.GlobalClass;
import com.example.antoi.nevent.Metier.Event;
import com.example.antoi.nevent.R;


public class TabActivity extends AppCompatActivity {

    private static final String TAG = "TabActivity";
    public static TabActivity Instance;
    SectionsPageAdapter mSectionsPageAdapter;
    ViewPager mViewPager;
    GlobalClass globalVariable;
    Context context;
    BDHelper db;
    TabLayout tabLayout;

    private void init(){
        context = getApplicationContext();
        globalVariable = (GlobalClass) context;
        Instance = this;
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        db = new BDHelper(context);
        mViewPager =  findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs_main);
        init();



        //On passe l'évènement à traiter dans le GlobalClass afin que les fragments puissent y accéder
        Intent i = getIntent();

        // Titre
        Event e = db.searchEventById(Integer.parseInt(i.getStringExtra("event")));
        setTitle(e.getNomEvent());

        globalVariable.setE(db.searchEventById(Integer.parseInt(i.getStringExtra("event"))));
        // Set up the ViewPager with the sections adapter.
        setupViewPager(mViewPager);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Détails");
        adapter.addFragment(new Tab2Fragment(), "Participants");
        adapter.addFragment(new Tab3Fragment(), "Inviter");
        viewPager.setAdapter(adapter);
    }

}
