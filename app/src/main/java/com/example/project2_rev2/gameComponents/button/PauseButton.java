package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.Action;
import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public class PauseButton extends Button {

    private Action pause;
    private BitmapObject pauseBitmap;

    public PauseButton(Action pause, Context context) {
        super(xCoordinate(360), yCoordinate(5), R.drawable.start_wave_button_background_active, new Size(90, 90), context);
        this.pauseBitmap = new BitmapObject(
                centerPosition.x-35,
                centerPosition.y-35,
                R.drawable.ic_pause,
                new Size(70, 70),
                context
        ) {};
        this.pause = pause;
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
                paint.setAlpha(255);
                pauseBitmap.getPaint().setAlpha(255);
                pause.action();
                return true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                paint.setAlpha(100);
                pauseBitmap.getPaint().setAlpha(100);
            }
        }
        return false;
    }
}

