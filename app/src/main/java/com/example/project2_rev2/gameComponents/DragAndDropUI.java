package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;
import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromVectorDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class DragAndDropUI {

    private Context context;
    private ArrayList<Bitmap> towerBitmapArrayList;
    private int[] towerBitmapHeightArray;
    private Bitmap towerBackground;

    private float deltaY;
    private boolean scroll;
    private Rect scrollTouchArea;

    public DragAndDropUI(Context context) {
        this.context = context;
        ArrayList<Tower.TowerType> towerTypeArrayList = new ArrayList<>(Arrays.asList(Tower.TowerType.values()));
        this.towerBitmapArrayList = new ArrayList<>();
        towerTypeArrayList.forEach(towerType -> towerBitmapArrayList.add(
                Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(context, towerType.icon), 250, 250, false)
        ));
        this.towerBitmapHeightArray = new int[towerBitmapArrayList.size()];
        int y = 170;
        for (int i = 0; i < towerBitmapArrayList.size(); i++) {
            towerBitmapHeightArray[i] = y;
            y += 280;
        }
        this.towerBackground = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(context, R.drawable.tower_background), 260, 260, false);

        this.scroll = false;
        this.scrollTouchArea = new Rect(
                (int)xCoordinate(0),
                (int)yCoordinate(0),
                (int)xCoordinate(350),
                (int)yCoordinate(gameDisplay.size.height-210)
        );
    }

    public void drawTowerIcon(Canvas canvas, int y, Bitmap towerBitmap) {
        canvas.drawBitmap(towerBackground, 50, y-5, null);
        canvas.drawBitmap(towerBitmap, 55, y, null);
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < towerBitmapArrayList.size(); i++) {
            drawTowerIcon(canvas, towerBitmapHeightArray[i], towerBitmapArrayList.get(i));
        }
    }

    public void update() {

    }

    public void onTouchEvent(MotionEvent motionEvent) {
        if (scrollTouchArea.contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                deltaY = motionEvent.getY();
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if (deltaY < motionEvent.getY()) {
                    deltaY = motionEvent.getY() - deltaY;
                } else {
                    deltaY -= motionEvent.getY();
                    deltaY = -deltaY;
                }
                scroll = true;
            }
            if (scroll) {
                for (int i = 0; i < towerBitmapHeightArray.length; i++) {
                    towerBitmapHeightArray[i] += deltaY/10;
                }
                scroll = false;
                deltaY = motionEvent.getY();
            }
        }
    }
}
