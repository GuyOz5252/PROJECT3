package com.example.project2_rev2.gameStructure.sceneManagement;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Scene {

    protected int speedMultiplier;

    public abstract void draw(Canvas canvas);
    public abstract void update();
    public abstract void onTouchEvent(MotionEvent motionEvent);

    public static final String[] sceneTitles = new String[] {
            "DEMO 1",
    };

    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public int getSpeedMultiplier() {
        return speedMultiplier;
    }
}
