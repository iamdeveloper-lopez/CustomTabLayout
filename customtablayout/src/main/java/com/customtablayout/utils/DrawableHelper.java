package com.customtablayout.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.core.content.ContextCompat;

import java.util.Objects;

public class DrawableHelper {

    public static GradientDrawable getThemeGradientBackground(Context context, int colorId) {
        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(SizeHelper.dp(context, 25));
        background.setColor(ColorHelper.get(context, colorId));
        return background;
    }

    public static GradientDrawable getGradientBackground(Context context, int colorId) {
        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(SizeHelper.dp(context, 25));
        background.setColor(ContextCompat.getColor(context, colorId));
        return background;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable getVectorDrawable(Context context, int drawableId) {
        return ContextCompat.getDrawable(context, drawableId);
    }

    public static Drawable getDrawableFromBitmap(Resources resources, Bitmap bitmap) {
        return new BitmapDrawable(resources, bitmap);
    }

}
