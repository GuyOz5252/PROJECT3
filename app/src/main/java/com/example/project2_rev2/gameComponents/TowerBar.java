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
import com.example.project2_rev2.gameComponents.buttons.FastForwardButton;
import com.example.project2_rev2.gameComponents.buttons.StartWaveButton;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

public class TowerBar extends RectObject {

    private Context context;

    private Paint borderPaint;

    private DragAndDropUI dragAndDropUI;

    private TowerManager towerManager;

    private StartWaveButton startWaveButton;
    private FastForwardButton fastForwardButton;

    public TowerBar(WaveManager waveManager, Context context) {
        super(xCoordinate(0), yCoordinate(0), new Size(350, gameDisplay.size.height), ContextCompat.getColor(context, R.color.towerBarBackground));
        this.context = context;
        this.borderPaint = new Paint();
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(10);
        this.borderPaint.setColor(ContextCompat.getColor(context, R.color.black));

        this.startWaveButton = new StartWaveButton(waveManager, context);
        waveManager.setStartWaveButton(startWaveButton);

        this.fastForwardButton = new FastForwardButton(context);

        GameValues.colliderArrayList.add(rect);
    }

    public Rect getTowerBarRect() {
        return rect;
    }

    public void setTowerManager(TowerManager towerManager) {
        this.towerManager = towerManager;
        this.dragAndDropUI = new DragAndDropUI(towerManager, context);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawRect(
                new Rect(
                        (int)xCoordinate(0),
                        (int)yCoordinate(gameDisplay.size.height-210),
                        (int)xCoordinate(350),
                        (int)yCoordinate(gameDisplay.size.height)
                ),
                paint
        );

        canvas.drawLine(
                (float)xCoordinate(0),
                (float)yCoordinate(gameDisplay.size.height-210),
                (float)xCoordinate(350),
                (float)yCoordinate(gameDisplay.size.height-210),
                borderPaint
        );

        startWaveButton.draw(canvas);
        fastForwardButton.draw(canvas);

        canvas.drawRect(rect, borderPaint);

        if (!towerManager.getIsAnyTowerSelected()) {
            dragAndDropUI.draw(canvas);
        }
    }

    @Override
    public void update() {
        dragAndDropUI.update();
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        startWaveButton.onTouchEvent(motionEvent);
        fastForwardButton.onTouchEvent(motionEvent);

        if (!towerManager.getIsAnyTowerSelected()) {
            dragAndDropUI.onTouchEvent(motionEvent);
        }
    }
}
