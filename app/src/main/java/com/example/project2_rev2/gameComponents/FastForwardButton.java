package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.Size;

public class FastForwardButton extends Button {

    private Scene scene;

    public FastForwardButton(Scene scene, Display display, Context context) {
        super(18, display.size.height-180, R.drawable.ic_launcher_background, new Size(150, 150), context);
        this.scene = scene;
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        if (scene.getSpeedMultiplier() == 1) {
            scene.setSpeedMultiplier(2);
        } else {
            scene.setSpeedMultiplier(1);
        }
    }
}
