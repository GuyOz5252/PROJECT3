package com.example.project2_rev2.gameStructure.sceneManagement;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.scenes.DemoOne;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.GaveValues;

public class SceneManager {

    private Scene currentScene;
    private String levelName;

    public SceneManager(int currentSceneIdx, Display display, Context context) { // receive index of requested scene and init that scene
        GaveValues.display.size.width = display.size.width;
        GaveValues.display.size.height = display.size.height;
        switch (currentSceneIdx) {
            case 0:
                this.currentScene = new DemoOne(context);
                this.levelName = Scene.sceneTitles[currentSceneIdx];
        }
    }

    public void update() {
        currentScene.update();
    }

    public void draw(Canvas canvas) {
        currentScene.draw(canvas);
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        currentScene.onTouchEvent(motionEvent);
    }

    public String getLevelName() {
        return this.levelName;
    }
}
