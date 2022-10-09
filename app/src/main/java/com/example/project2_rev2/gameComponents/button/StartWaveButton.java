package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Size;

public class StartWaveButton extends Button {

    private WaveManager waveManager;

    public StartWaveButton(WaveManager waveManager, Context context) {
        super(xCoordinate(183), yCoordinate(gameDisplay.size.height-180), R.drawable.ic_launcher_background, new Size(150, 150), context);
        this.waveManager = waveManager;
    }

    @Override
    public void setIsActive(boolean isActive) {
        super.setIsActive(isActive);
        // change button bitmap
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (isActive) {
                waveManager.startWave();
            }
            System.out.println("press");
        }
        return true;
    }
}
