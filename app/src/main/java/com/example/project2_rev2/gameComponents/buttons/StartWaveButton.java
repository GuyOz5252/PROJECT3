package com.example.project2_rev2.gameComponents.buttons;

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

/**
 * a class that represents the start wave button in the tower bar
 * on press, start the next wave
 * if a wave is currently active the button is inactive
 */

public class StartWaveButton extends Button {

    private WaveManager waveManager;
    private BitmapObject startWaveIconIcon;
    private BitmapObject originalStartWaveIconIcon;
    private BitmapObject pressedStartWaveIconIcon;

    public StartWaveButton(WaveManager waveManager, Context context) {
        super(xCoordinate(183), yCoordinate(gameDisplay.size.height-180), R.drawable.start_wave_button_background_active, new Size(150, 150), context);
        this.waveManager = waveManager;
        this.originalStartWaveIconIcon = new BitmapObject(
                centerPosition.x-60,
                centerPosition.y-60,
                R.drawable.ic_start_wave_active,
                new Size(120, 120),
                context
        ) {};
        this.pressedStartWaveIconIcon = new BitmapObject(
                centerPosition.x-52.5,
                centerPosition.y-52.5,
                R.drawable.ic_start_wave_active,
                new Size(105, 105),
                context
        ) {};
        startWaveIconIcon = originalStartWaveIconIcon;
    }

    @Override
    public void setIsActive(boolean isActive) {
        super.setIsActive(isActive);
        if (isActive) {
            bitmap = originalBitmap;
            startWaveIconIcon.changeBitmap(R.drawable.ic_start_wave_active);
        }
    }

    @Override
    public void setPressEffect(boolean b) {
        if (b) {
            bitmap = pressedBitmap;
            position = pressedPosition;
            startWaveIconIcon = pressedStartWaveIconIcon;
        } else {
            bitmap = originalBitmap;
            position = originalPosition;
            startWaveIconIcon = originalStartWaveIconIcon;
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
                if (isActive) {
                    waveManager.startWave();
                    setPressEffect(false);
                    changeBitmap(R.drawable.start_wave_button_background_inactive);
                    startWaveIconIcon.changeBitmap(R.drawable.ic_start_wave_inactive);
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && isActive) {
                setPressEffect(true);
            }
        } else if (isActive) {
            setPressEffect(false);
        }
        return true;
    }
}