package com.android.project3.gameComponents;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.android.project3.utils.GameValues;
import com.android.project3.utils.Position;

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

    public void calculateColliders() {
        for (int i = 1; i < positionArrayList.size(); i++) {
            if (positionArrayList.get(i-1).x < positionArrayList.get(i).x || positionArrayList.get(i-1).y < positionArrayList.get(i).y) {
                GameValues.colliderArrayList.add(new Rect(
                        (int)positionArrayList.get(i-1).x-48,
                        (int)positionArrayList.get(i-1).y-48,
                        (int)positionArrayList.get(i).x+48,
                        (int)positionArrayList.get(i).y+48
                ));
            } else {
                GameValues.colliderArrayList.add(new Rect(
                        (int)positionArrayList.get(i).x-48,
                        (int)positionArrayList.get(i).y-48,
                        (int)positionArrayList.get(i-1).x+48,
                        (int)positionArrayList.get(i-1).y+48
                ));
            }
        }
    }

    public void draw(Canvas canvas) {
        Paint testPaint = new Paint();
        testPaint.setColor(Color.RED);
        testPaint.setStrokeWidth(5);

        for (int i = 0; i < positionArrayList.size()-1; i++) {
            canvas.drawLine(
                    (float)positionArrayList.get(i).x,
                    (float)positionArrayList.get(i).y,
                    (float)positionArrayList.get(i+1).x,
                    (float)positionArrayList.get(i+1).y,
                    testPaint
            );
        }
    }
}
