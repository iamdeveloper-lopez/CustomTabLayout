package com.customtablayout;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.customtablayout.interfaces.OnTabSelectedListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CustomTabLayout";

    private CustomTabLayout tabLayout;

    private CustomTabLayout.Tab selectedTab;

    private boolean hasFavorites = false;
    private boolean hasItinerary = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(CustomTabLayout.Tab selectedTab) {
                MainActivity.this.selectedTab = selectedTab;
            }

            @Override
            public void onTabReselected(CustomTabLayout.Tab tab) {
                if (hasFavorites) {
                    hasItinerary = true;
                }
                if (!hasFavorites) {
                    hasFavorites = true;
                }
                enableTabs(getEnableSize());
            }

            @Override
            public void onTabUnselected(CustomTabLayout.Tab var1) {
                for (int i = 0; i < tabLayout.getTabCount(); i++) {
                    if (i != tabLayout.getTabPosition(selectedTab)) {
                        CustomTabLayout.Tab tab = tabLayout.getTabAt(i);
                        if (tabLayout.getTabPosition(selectedTab) > i) {
                            tab.setSelected(true);
                        } else {
                            tab.setSelected(false);
                        }
                    }
                }
            }
        });

        tabLayout.setSelectedTabAt(0);

        enableTabs(getEnableSize());

    }

    private int getEnableSize() {
        int size = 1;
        if (hasFavorites) {
            size++;
        }
        if (hasItinerary) {
            size++;
        }
        return size;
    }

    private void enableTabs(int size) {
        if (tabLayout.getTabCount() >= size) {
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                CustomTabLayout.Tab tab = tabLayout.getTabAt(i);
                if ((i + 1) <= size) {
                    tab.setEnabled(true);
                    Log.d(TAG, "enableTabs: true " + tab.getText());
                } else {
                    tab.setEnabled(false);
                    Log.d(TAG, "enableTabs: false " + tab.getText());
                }
            }
        }
    }

}
