package com.example.caloriecounter.ui.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.caloriecounter.Fragments.TodayCalorie;
import com.example.caloriecounter.Fragments.TodayMenu;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    int recommendedCalorieAmount;

    private static final String[] TAB_TITLES = new String[]{"Main", "Menu"};


    public SectionsPagerAdapter(FragmentManager fm, int recommendedCalorieAmount) {
        super(fm);
        this.recommendedCalorieAmount = recommendedCalorieAmount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = TodayCalorie.newInstance(recommendedCalorieAmount);
                break;
            case 1:
                fragment = TodayMenu.newInstance();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}