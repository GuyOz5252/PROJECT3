package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Paint;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameStructure.MainThread;

public class FPSCounter extends TextUI {

    private MainThread thread;

    public FPSCounter(Context context) {
        super(460, 100, "FPS: 0", R.color.devRed, 50, Paint.Align.LEFT, context);
    }

    public void setThread(MainThread thread) {
        this.thread = thread;
    }

    @Override
    public void update() {
        super.update();
        changeText("FPS: " + thread.getFPS());
    }
}
