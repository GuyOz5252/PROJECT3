package com.example.project2_rev2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
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
        return BitmapFactory.decodeResource(context.getResources(), resourceId);
    }

    public static Rect getGameCoordinatesRect(Rect rect) {
        return new Rect(
                (int)(rect.left + GameValues.xOffset),
                (int)(rect.top + GameValues.yOffset),
                (int)(rect.right + GameValues.xOffset),
                (int)(rect.bottom + GameValues.yOffset)
        );
    }

    public static Rect getDisplayCoordinatesRect(Rect rect) {
        return new Rect(
                (int)(rect.left - GameValues.xOffset),
                (int)(rect.top - GameValues.yOffset),
                (int)(rect.right - GameValues.xOffset),
                (int)(rect.bottom - GameValues.yOffset)
        );
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        return Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }
}
