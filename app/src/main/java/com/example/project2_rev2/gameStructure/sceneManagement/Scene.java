package com.example.project2_rev2.gameStructure.sceneManagement;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.listeners.OnHealthChangeListener;

public abstract class Scene implements OnHealthChangeListener {

    public abstract void draw(Canvas canvas);
    public abstract void update();
    public abstract void onTouchEvent(MotionEvent motionEvent);

    public static final String[] sceneTitles = new String[] {
            "DEMO 1",
    };
}
