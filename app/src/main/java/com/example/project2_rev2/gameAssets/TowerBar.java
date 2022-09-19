package com.example.project2_rev2.gameAssets;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.utils.Display;

public class TowerBar extends RectObject {

    public TowerBar(Display display, Context context) {
        super(0, 0, display.size.width/8, display.size.height, ContextCompat.getColor(context, R.color.cardview_dark_background));
    }

    @Override
    public void update() {

    }
}
