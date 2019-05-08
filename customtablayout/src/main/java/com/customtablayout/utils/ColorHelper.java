package com.customtablayout.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

public class ColorHelper {

    public static int get(Context context, int colorId) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(colorId, typedValue, true);
        return typedValue.data;
    }

    public static int alpha(int color, int transparent) {
        int alpha = Math.round(255 / transparent);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        return Color.argb(alpha, r, g, b);
    }

}