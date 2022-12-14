package com.example.project2_rev2.gameStructure.sceneManagement;

import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.annotation.DrawableRes;

import com.example.project2_rev2.R;
import com.example.project2_rev2.menus.MainMenuFragment;

public abstract class Scene {

    public abstract void saveGame();
    public abstract void draw(Canvas canvas);
    public abstract void update();
    public abstract void onTouchEvent(MotionEvent motionEvent);

    public static final String[] sceneTitles = new String[] {
            "DEMO 1",
    };

    public enum Levels {
        DEMO_1("DEMO 1", R.drawable.level_thumbnail);

        public String name;
        public int thumbnail;

        Levels(String name, @DrawableRes int thumbnail) {
            this.name = name;
            this.thumbnail = thumbnail;
        }
    }
}
