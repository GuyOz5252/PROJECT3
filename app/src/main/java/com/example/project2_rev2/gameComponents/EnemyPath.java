package com.example.project2_rev2.gameComponents;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.project2_rev2.utils.Position;

import java.util.ArrayList;

public class EnemyPath {

    private ArrayList<Position> positionArrayList;

    public EnemyPath() {
        this.positionArrayList = new ArrayList<>();
    }

    public ArrayList<Position> getPositionArrayList() {
        return positionArrayList;
    }

    public void add(Position position) {
        positionArrayList.add(position);

    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        Paint testPaint = new Paint();
        testPaint.setColor(Color.GRAY);
        testPaint.setStrokeWidth(120);

        for (int i = 0; i < positionArrayList.size()-1; i++) {
            canvas.drawLine(
                    (float)positionArrayList.get(i).x,
                    (float)positionArrayList.get(i).y,
                    (float)positionArrayList.get(i+1).x,
                    (float)positionArrayList.get(i+1).y,
                    testPaint
            );
            canvas.drawRect(new Rect(
                    (int)positionArrayList.get(i).x-60,
                    (int)positionArrayList.get(i).y-60,
                    (int)positionArrayList.get(i).x+60,
                    (int)positionArrayList.get(i).y+60
            ), testPaint);
        }
    }
}
