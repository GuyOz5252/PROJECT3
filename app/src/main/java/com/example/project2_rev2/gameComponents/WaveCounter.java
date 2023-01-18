package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Paint;

import com.example.project2_rev2.R;

public class WaveCounter extends TextUI {

    public WaveCounter(Context context) {
        super(460, 55, "", R.color.white, 50, Paint.Align.LEFT, context);
        setBold();
        setShadow();
    }
}
