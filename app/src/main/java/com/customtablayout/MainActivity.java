package com.customtablayout;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.customtablayout.interfaces.OnTabSelectedListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CustomTabLayout";

    private CustomTabLayout tabLayout;

    private View selectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(CustomTabLayout.Tab tab) {
                super.onTabSelected(tab);
                selectedTab = tab;
            }
        });

    }
}
