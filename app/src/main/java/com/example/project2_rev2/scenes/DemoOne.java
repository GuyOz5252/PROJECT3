package com.example.project2_rev2.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameAssets.TowerBar;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;

public class DemoOne extends Scene {

    private Context context;

    // game components
    private TowerBar towerBar;

    public DemoOne(Display display, Context context) {
        this.context = context;

        towerBar = new TowerBar(display, context);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.background));

        towerBar.draw(canvas);
    }

    @Override
    public void update() {
        towerBar.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }
}
