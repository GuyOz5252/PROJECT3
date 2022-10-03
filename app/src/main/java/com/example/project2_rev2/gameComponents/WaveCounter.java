package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GaveValues.xCoordinate;
import static com.example.project2_rev2.utils.GaveValues.yCoordinate;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.TextUI;

public class WaveCounter extends TextUI {

    public WaveCounter(Context context) {
        super(xCoordinate(360), yCoordinate(50), "", R.color.white, context);
    }

    @Override
    public void update() {

    }
}
