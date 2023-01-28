package com.android.project3.gameStructure.sceneManagement;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.android.project3.utils.Action;
import com.android.project3.scenes.DemoOne;

public class SceneManager {

    private Scene currentScene;
    private String levelName;

    public SceneManager(int currentSceneIdx, Action[] actionsArray, boolean loadSave, Context context) { // receive index of requested scene and init that scene
        switch (currentSceneIdx) {
            case 0:
                this.currentScene = new DemoOne(actionsArray, loadSave, context);
                this.levelName = Scene.Levels.values()[currentSceneIdx].name;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentSceneIdx);
        }
    }

    public void saveGame() {
        currentScene.saveGame();
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
