package com.android.project3.gameComponents;

import android.content.Context;
import android.graphics.Paint;

import com.android.project3.R;

public class WaveCounter extends TextUI {

    public WaveCounter(Context context) {
        super(460, 55, "", R.color.white, 50, Paint.Align.LEFT, context);
        setBold();
        setShadow();
    }
}
