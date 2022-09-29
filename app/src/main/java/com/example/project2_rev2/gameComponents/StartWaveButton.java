package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.Size;

public class StartWaveButton extends Button {

    private WaveManager waveManager;

    public StartWaveButton(double x, double y, int resourceId, Size size, WaveManager waveManager, Context context) {
        super(x, y, resourceId, size, context);
        this.waveManager = waveManager;
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        waveManager.startWave();
    }
}
