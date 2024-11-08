package com.android.project3.gameComponents;

import android.content.Context;
import android.graphics.Paint;

import com.android.project3.R;
import com.android.project3.gameStructure.MainThread;

public class FPSCounter extends TextUI {

    private MainThread thread;

    public FPSCounter(Context context) {
        super(10, 50, "FPS: 0", R.color.dev_red, 50, Paint.Align.LEFT, context);
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
