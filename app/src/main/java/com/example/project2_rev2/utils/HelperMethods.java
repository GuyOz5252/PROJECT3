package com.example.project2_rev2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

public class HelperMethods {

    // https://www.geeksforgeeks.org/how-to-convert-a-vector-to-bitmap-in-android/
    public static Bitmap getBitmapFromVectorDrawable(Context context,@DrawableRes int resourceId) {
        Drawable drawable = ContextCompat.getDrawable(context, resourceId);
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmapFromPicture(Context context,@DrawableRes int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        return bitmap;
    }

    public static double getHypoDistance(double towerX, double towerY, double enemyX, double enemyY) {
        return Math.hypot(Math.abs(towerX-enemyX), Math.abs(towerY-enemyY));
    }
}
