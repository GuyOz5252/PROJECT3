package com.example.project2_rev2.gameStructure.sceneManagement;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.scenes.DemoOne;

public class SceneManager {

    private Scene currentScene;
    private String levelName;

    public SceneManager(int currentSceneIdx, Action[] actionsArray, boolean loadSave, Context context) { // receive index of requested scene and init that scene
        switch (currentSceneIdx) {
            case 0:
                this.currentScene = new DemoOne(actionsArray, loadSave, context);
                this.levelName = Scene.sceneTitles[currentSceneIdx];
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
