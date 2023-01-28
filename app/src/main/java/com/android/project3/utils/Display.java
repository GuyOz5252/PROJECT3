package com.android.project3.utils;

import android.util.DisplayMetrics;

public class Display {

    public Size size;

    public Display(DisplayMetrics displayMetrics) {
        this.size = new Size(
                displayMetrics.widthPixels,
                displayMetrics.heightPixels
        );
    }

    public Display(double width, double height) {
        this.size = new Size(
                width,
                height
        );
    }
}
