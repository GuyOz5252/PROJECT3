package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GaveValues.canvasDisplay;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.Size;

public class FastForwardButton extends Button {

    public FastForwardButton(Context context) {
        super(18, canvasDisplay.size.height-180, R.drawable.ic_launcher_background, new Size(150, 150), context);
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            System.out.println("clicked");
            Scene.isFastForwarded = !Scene.isFastForwarded;
        }
    }
}
