package com.customtablayout.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class SizeHelper {

    public static int dp(Context context, int dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public static int dp(Context context, float dp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public static int sp(Context context, int sp) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, dm);
    }

    public static int getDeviceWidthByPercentage(Context context, int percent) {
        double percentage = percent / 100f;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.widthPixels * percentage);
    }

    public static int getDeviceHeightByPercentage(Context context, int percent) {
        double percentage = percent / 100f;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) (metrics.heightPixels * percentage);
    }

    public static int getHeight(View view) {
        try {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            int height = params.height;
            if (height > 0) {
                return height;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getDeviceHeightByPercentage((Activity) view.getContext(), 100);
    }

}
