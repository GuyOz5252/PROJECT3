package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

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
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;

public class TowerBar extends RectObject {

    private Paint borderPaint;

    private TowerManager towerManager;

    private StartWaveButton startWaveButton;
    private FastForwardButton fastForwardButton;

    public TowerBar(WaveManager waveManager, Context context) {
        super(xCoordinate(0), yCoordinate(0), 350, gameDisplay.size.height, ContextCompat.getColor(context, R.color.towerBarBackground));
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

    public void setTowerManager(TowerManager towerManager) {
        this.towerManager = towerManager;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(rect, borderPaint);

        canvas.drawLine(
                (float)xCoordinate(0),
                (float)yCoordinate(gameDisplay.size.height-210),
                (float)xCoordinate(350),
                (float)yCoordinate(gameDisplay.size.height-210),
                borderPaint
        );

        startWaveButton.draw(canvas);
        fastForwardButton.draw(canvas);
    }

    @Override
    public void update() {

    }

    public void onTouchEvent(MotionEvent motionEvent) {
        startWaveButton.onTouchEvent(motionEvent);
        fastForwardButton.onTouchEvent(motionEvent);

        if (!towerManager.getIsAnyTowerSelected()) {
            // listen to drag n drop ui
        }
    }
}
