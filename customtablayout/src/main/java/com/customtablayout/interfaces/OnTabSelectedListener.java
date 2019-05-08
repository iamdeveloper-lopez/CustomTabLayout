package com.customtablayout.interfaces;

import android.util.Log;

import com.customtablayout.CustomTabLayout;

public class OnTabSelectedListener implements CustomTabLayout.BaseOnTabSelectedListener<CustomTabLayout.Tab> {

    private static final String TAG = CustomTabLayout.class.getSimpleName();

    @Override
    public void onTabSelected(CustomTabLayout.Tab tab) {
        Log.d(tab.getText(), "onTabSelected()");
    }

    @Override
    public void onTabReselected(CustomTabLayout.Tab tab) {
        Log.d(tab.getText(), "onTabReselected()");
    }

    @Override
    public void onTabUnselected(CustomTabLayout.Tab tab) {
        Log.d(tab.getText(), "onTabUnselected()");
    }
}
