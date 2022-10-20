package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Size;

public class StartWaveButton extends Button {

    private WaveManager waveManager;
    private BitmapObject startWaveIconIcon;

    public StartWaveButton(WaveManager waveManager, Context context) {
        super(xCoordinate(183), yCoordinate(gameDisplay.size.height-180), R.drawable.start_wave_button_background_active, new Size(150, 150), context);
        this.waveManager = waveManager;
        this.startWaveIconIcon = new BitmapObject(
                centerPosition.x-60,
                centerPosition.y-60,
                R.drawable.ic_start_wave_active,
                new Size(120, 120),
                context
        ) {};
    }

    @Override
    public void setIsActive(boolean isActive) {
        super.setIsActive(isActive);
        if (isActive) {
            changeBitmap(R.drawable.start_wave_button_background_active);
            startWaveIconIcon.changeBitmap(R.drawable.ic_start_wave_active);
        } else {
            changeBitmap(R.drawable.start_wave_button_background_inactive);
            startWaveIconIcon.changeBitmap(R.drawable.ic_start_wave_inactive);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        startWaveIconIcon.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                paint.setAlpha(255);
                startWaveIconIcon.getPaint().setAlpha(255);
                if (isActive) {
                    waveManager.startWave();
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                paint.setAlpha(100);
                startWaveIconIcon.getPaint().setAlpha(100);
            }
        }
        return true;
    }
}
