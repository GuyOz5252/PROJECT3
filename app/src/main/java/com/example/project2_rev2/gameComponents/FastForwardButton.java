package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.Size;

public class FastForwardButton extends Button {

    public FastForwardButton(Display display, Context context) {
        super(18, display.size.height-180, R.drawable.ic_launcher_background, new Size(150, 150), context);
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        if (Scene.speedMultiplier == 1) {
            Scene.speedMultiplier = 2;
        } else {
            Scene.speedMultiplier = 1;
        }
    }
}
