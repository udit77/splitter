package com.example.udit.splitter;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by Udit on 25-10-2015.
 */
public class afterHome extends FragmentActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private HomeTabsPagerAdapter mAdapter;
    private ActionBar actionBar;

    ArrayList<Contacts> arrayList= new ArrayList<Contacts>();

    private String[] tabs = {"Partners","PickContacts"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterhome);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new HomeTabsPagerAdapter(getSupportFragmentManager(),bundle);

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    public class HomeTabsPagerAdapter extends FragmentPagerAdapter {

        Bundle bundle;

        public HomeTabsPagerAdapter(FragmentManager fm,Bundle bundle) {
            super(fm);
            this.bundle=bundle;
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    Partners partners = new Partners();
                    partners.setArguments(bundle);
                    return partners;

                case 1:
                    PickContacts pickContacts = new PickContacts();
                    pickContacts.setArguments(bundle);
                    return pickContacts;

            }

            return null;
        }

        @Override
        public int getCount() {
            // get item count - equal to number of tabs
            return 2;
        }
    }

}
