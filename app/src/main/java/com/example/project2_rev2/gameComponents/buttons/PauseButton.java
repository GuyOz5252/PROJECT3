package com.example.project2_rev2.gameComponents.buttons;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Size;

/**
 * a class that represents the pause button
 * on press, create the pause menu dialog from game view
 */

public class PauseButton extends Button {

    private Action pause;
    private BitmapObject pauseBitmap;
    private BitmapObject originalPauseBitmap;
    private BitmapObject pressedPauseBitmap;

    public PauseButton(Action pause, Context context) {
        super(xCoordinate(360), yCoordinate(5), R.drawable.start_wave_button_background_active, new Size(90, 90), context);
        this.originalPauseBitmap = new BitmapObject(
                centerPosition.x-35,
                centerPosition.y-35,
                R.drawable.ic_pause,
                new Size(70, 70),
                context
        ) {};
        this.pressedPauseBitmap = new BitmapObject(
                centerPosition.x-30.5,
                centerPosition.y-30.5,
                R.drawable.ic_pause,
                new Size(61, 61),
                context
        ) {};
        this.pauseBitmap = originalPauseBitmap;
        this.pause = pause;
    }

    @Override
    public void setPressEffect(boolean b) {
        if (b) {
            bitmap = pressedBitmap;
            position = pressedPosition;
            pauseBitmap = pressedPauseBitmap;
        } else {
            bitmap = originalBitmap;
            position = originalPosition;
            pauseBitmap = originalPauseBitmap;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        pauseBitmap.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                pause.action();
                setPressEffect(false);
                return true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setPressEffect(true);
            }
        } else {
            setPressEffect(false);
        }
        return false;
    }
}

