package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.RectObject;
import com.example.project2_rev2.gameComponents.button.FastForwardButton;
import com.example.project2_rev2.gameComponents.button.StartWaveButton;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;

public class TowerBar extends RectObject {

    private Display display;
    private Paint borderPaint;

    private StartWaveButton startWaveButton;
    private FastForwardButton fastForwardButton;

    public TowerBar(Scene scene, Display display, WaveManager waveManager, Context context) {
        super(0, 0, 350, display.size.height, ContextCompat.getColor(context, R.color.towerBarBackground));
        this.display = display;
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(10);
        this.borderPaint.setColor(ContextCompat.getColor(context, R.color.black));

        this.startWaveButton = new StartWaveButton(waveManager, display, context);
        waveManager.setStartWaveButton(startWaveButton);

        this.fastForwardButton = new FastForwardButton(display, context);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(this.rect, borderPaint);
        canvas.drawLine(0, (float)display.size.height-210, 350, (float)display.size.height-210, borderPaint);
        startWaveButton.draw(canvas);
        fastForwardButton.draw(canvas);
    }

    @Override
    public void update() {

    }

    public void onTouchEvent(MotionEvent motionEvent) {
        startWaveButton.onTouchEvent(motionEvent);
        fastForwardButton.onTouchEvent(motionEvent);
    }
}
