package com.example.project2_rev2.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;

public class DemoOne extends Scene {

    public DemoOne(Display display, Context context) {

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
    }

    @Override
    public void update() {

    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }
}
