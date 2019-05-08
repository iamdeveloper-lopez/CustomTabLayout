package com.customtablayout.interfaces;

import android.view.View;

import com.customtablayout.CustomTabLayout;

public abstract class TabOnClickListener implements View.OnClickListener {

    public abstract void onTabClick(CustomTabLayout.Tab tab);

    @Override
    public void onClick(View v) {
        onTabClick((CustomTabLayout.Tab) v);
    }
}
