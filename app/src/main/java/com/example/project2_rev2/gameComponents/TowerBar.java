package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GaveValues.canvasDisplay;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.RectObject;
import com.example.project2_rev2.gameComponents.button.FastForwardButton;
import com.example.project2_rev2.gameComponents.button.StartWaveButton;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;

public class TowerBar extends RectObject {

    private Paint borderPaint;

    private StartWaveButton startWaveButton;
    private FastForwardButton fastForwardButton;

    public TowerBar(Scene scene, WaveManager waveManager, Context context) {
        super(0, 0, 350, canvasDisplay.size.height, ContextCompat.getColor(context, R.color.towerBarBackground));
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(10);
        this.borderPaint.setColor(ContextCompat.getColor(context, R.color.black));

        this.startWaveButton = new StartWaveButton(waveManager, context);
        waveManager.setStartWaveButton(startWaveButton);

        this.fastForwardButton = new FastForwardButton(context);
    }

    public Rect getTowerBarRect() {
        return rect;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(rect, borderPaint);
        canvas.drawLine(0, (float)canvasDisplay.size.height-210, 350, (float)canvasDisplay.size.height-210, borderPaint);
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
