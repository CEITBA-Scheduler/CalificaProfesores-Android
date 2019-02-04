package com.gnd.calificaprofesores.AdapterClassFrontPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.gnd.calificaprofesores.R;

public class AdapterClassFrontPage extends FragmentPagerAdapter  {

    private final String[] TITLES = {"VISTA GENERAL",
            "OPINIONES RECIENTES", "OPINIONES IMPORTANTES" , "TU OPINIÓN"};


    public AdapterClassFrontPage(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
    @Override
    public int getCount() {
        return TITLES.length;
    }
    @Override
    public Fragment getItem(int position) {
        return ActivityQual.newInstance(position);
    }
}
