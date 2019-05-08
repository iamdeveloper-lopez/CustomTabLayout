package com.customtablayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.customtablayout.interfaces.OnTabSelectedListener;
import com.customtablayout.interfaces.TabOnClickListener;
import com.customtablayout.utils.DrawableHelper;
import com.customtablayout.utils.SizeHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomTabLayout extends LinearLayout {

    private boolean scrollable;
    @LayoutRes
    private int tab_item_layout;
    private int tab_count;
    @ColorInt
    private int tab_item_text_color;

    private String[] titles;

    private OnTabSelectedListener onTabSelectedListener;

    private TabParent tabParent;

    private Tab selectedTab;

    public void addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.onTabSelectedListener = onTabSelectedListener;
    }

    public CustomTabLayout(Context context) {
        super(context);
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupAttibutes(attrs);
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttibutes(attrs);
        init();
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupAttibutes(attrs);
        init();
    }

    private void setupAttibutes(AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomTabLayout, 0, 0);
        try {
            scrollable = a.getBoolean(R.styleable.CustomTabLayout_ctl_scrollable, false);
            tab_item_layout = a.getResourceId(R.styleable.CustomTabLayout_ctl_tab_item_layout, 0);
            tab_count = a.getInteger(R.styleable.CustomTabLayout_ctl_tab_item_count, 0);
            tab_item_text_color = a.getColor(R.styleable.CustomTabLayout_ctl_tab_item_text_color, Color.WHITE);
            int titlesId = a.getResourceId(R.styleable.CustomTabLayout_ctl_tab_item_titles, 0);
            titles = a.getResources().getStringArray(titlesId);
        } finally {
            a.recycle();
        }
    }

    private void init() {
        tabParent = new TabParent(getContext());
        for (int i = 0; i < tab_count; i++) {
            Tab tab = new Tab(getContext(), tab_item_layout);
            tab.setText(titles[i]);
            if (scrollable) {
                tab.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            } else {
                tab.setWidth(SizeHelper.getDeviceWidthByPercentage(getContext(), 100) / tab_count);
            }
            if (i == 0) {
                tab.setTabPosition(TabPosition.START, String.valueOf(i + 1));
            } else if ((i + 1) == tab_count) {
                tab.setTabPosition(TabPosition.END, String.valueOf(i + 1));
            } else {
                tab.setTabPosition(TabPosition.CENTER, String.valueOf(i + 1));
            }
            tab.setTabOnClickListener(new TabOnClickListener() {
                @Override
                public void onTabClick(Tab tab) {
                    if (selectedTab == null) {
                        selectedTab = tab;
                        selectedTab.setSelected(true);
                        if (onTabSelectedListener != null) {
                            onTabSelectedListener.onTabSelected(tab);
                        }
                    } else {
                        if (selectedTab != tab) {
                            tab.setSelected(true);
                            if (onTabSelectedListener != null) {
                                onTabSelectedListener.onTabSelected(tab);
                            }
                            selectedTab.setSelected(false);
                            if (onTabSelectedListener != null)
                                onTabSelectedListener.onTabUnselected(selectedTab);
                            selectedTab = tab;
                        } else {
                            selectedTab.setSelected(true);
                            if (onTabSelectedListener != null) {
                                onTabSelectedListener.onTabReselected(tab);
                            }
                        }
                    }
                }
            });
            tabParent.addTab(tab);
        }
        addView(tabParent);
    }

    public Tab getSelectedTab() {
        return selectedTab;
    }

    public TabParent getTabParent() {
        return tabParent;
    }

    public Tab getTabAt(int i) {
        return tabParent.getTabAt(i);
    }

    public int getTabCount() {
        return tabParent.getTabCount();
    }

    public int getTabPosition(Tab tab) {
        return tabParent.getTabPosition(tab);
    }

    public interface BaseOnTabSelectedListener<T extends Tab> {

        void onTabSelected(T tab);

        void onTabReselected(T tab);

        void onTabUnselected(T tab);

    }

    public class TabParent extends LinearLayout {

        private List<Tab> tabs = new ArrayList<>();

        public TabParent(Context context) {
            super(context);
            init();
        }

        public TabParent(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public TabParent(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        public TabParent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }

        private void init() {
            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            setOrientation(LinearLayout.HORIZONTAL);
        }

        public void addTab(Tab tab) {
            Log.d("TabParent", "addTab()");
            addView(tab);
            tabs.add(tab);
        }

        public void removeTab(Tab tab) {
            removeView(tab);
            tabs.remove(tab);
        }

        public int getTabCount() {
            return tabs.size();
        }

        public Tab getTabAt(int index) {
            return tabs.get(index);
        }

        public int getTabPosition(Tab tab) {
            return tabs.indexOf(tab);
        }

    }

    public enum TabPosition {
        START,
        CENTER,
        END
    }

    public class Tab extends LinearLayout {

        private TextView text;
        private Indicator indicator;

        public Tab(Context context) {
            super(context);
            setup();
        }

        public Tab(Context context, @LayoutRes int layoutRes) {
            super(context);
            setup();
            init(layoutRes);
        }

        public Tab(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            setup();
        }

        public Tab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setup();
        }

        public Tab(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            setup();
        }

        public void setTabOnClickListener(final TabOnClickListener tabOnClickListener) {
            Log.d("Tab", "setTabOnClickListener()");
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    tabOnClickListener.onTabClick((Tab) v);
                }
            });
        }

        private void setup() {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            setLayoutParams(params);
            setPadding(0, 0, 0, 16);
            setOrientation(LinearLayout.VERTICAL);
            setClickable(true);
            setFocusable(true);
        }

        private void init(@LayoutRes int layoutRes) {
            View.inflate(getContext(), layoutRes, this);
            text = findViewById(R.id.text);
        }

        public void setCustomView(@LayoutRes int layoutRes) {
            View.inflate(getContext(), layoutRes, this);
            text = findViewById(R.id.text);
        }

        public void setWidth(int width) {
            setLayoutParams(new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT));
        }

        public void setCustomView(View customView) {
            addView(customView);
            text = customView.findViewById(R.id.text);
        }

        public void setText(String s) {
            text.setText(s);
        }

        public void setText(CharSequence s) {
            text.setText(s);
        }

        public Indicator getIndicator() {
            return indicator;
        }

        public void setTextColor(@ColorInt int textColor) {
            text.setTextColor(ColorStateList.valueOf(textColor));
        }

        public String getText() {
            return text.getText().toString();
        }

        public void setTabPosition(TabPosition tabPosition, String indicatorText) {
            Log.d("Tab", "view count : " + getChildCount());
            Log.d("Tab", "setTabPosition: " + tabPosition.name());
            indicator = new Indicator(getContext());
            indicator.setTabPosition(tabPosition);
            indicator.setText(indicatorText);
            addView(indicator);
        }

        public class Indicator extends RelativeLayout {

            public Indicator(Context context) {
                super(context);
                init();
            }

            public Indicator(Context context, AttributeSet attrs) {
                super(context, attrs);
                init();
            }

            public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
                super(context, attrs, defStyleAttr);
                init();
            }

            public Indicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
                super(context, attrs, defStyleAttr, defStyleRes);
                init();
            }

            private void init() {
                View.inflate(getContext(), R.layout.indicator, this);
            }

            public void setTabPosition(TabPosition tabPosition) {
                View indicator;
                ViewGroup.MarginLayoutParams params;
                if (tabPosition == TabPosition.START) {
                    indicator = new View(getContext());
                    indicator.setBackground(DrawableHelper.getVectorDrawable(getContext(), R.drawable.custom_tab_left_selector));
                    params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeHelper.dp(getContext(), 5));
                    params.topMargin = SizeHelper.dp(getContext(), 0);
                    params.rightMargin = SizeHelper.dp(getContext(), 1.5f);
                    params.leftMargin = SizeHelper.dp(getContext(), 3);
                } else if (tabPosition == TabPosition.END) {
                    indicator = new View(getContext());
                    indicator.setBackground(DrawableHelper.getVectorDrawable(getContext(), R.drawable.custom_tab_right_selector));
                    params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeHelper.dp(getContext(), 5));
                    params.topMargin = SizeHelper.dp(getContext(), 0);
                    params.leftMargin = SizeHelper.dp(getContext(), 1.5f);
                    params.rightMargin = SizeHelper.dp(getContext(), 3);
                } else {
                    indicator = new View(getContext());
                    indicator.setBackground(DrawableHelper.getVectorDrawable(getContext(), R.drawable.custom_tab_center_selector));
                    params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeHelper.dp(getContext(), 5));
                    params.topMargin = SizeHelper.dp(getContext(), 0);
                    params.rightMargin = SizeHelper.dp(getContext(), 1.5f);
                    params.leftMargin = SizeHelper.dp(getContext(), 1.5f);
                }
                indicator.setLayoutParams(params);
                indicator.setClickable(true);
                indicator.setFocusable(true);
                ((LinearLayout) findViewById(R.id.line_indicator)).addView(indicator);
                requestLayout();
            }

            public void setTextIndicatorHide() {
                findViewById(R.id.text_indicator).setVisibility(GONE);
            }

            public void setTextIndicatorShow() {
                findViewById(R.id.text_indicator).setVisibility(VISIBLE);
            }

            public void setText(String s) {
                ((TextView) findViewById(R.id.indicator_text_view)).setText(s);
                setTextIndicatorShow();
            }

        }

    }

}
