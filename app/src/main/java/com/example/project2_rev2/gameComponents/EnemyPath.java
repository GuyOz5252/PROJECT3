package com.example.project2_rev2.gameComponents;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.project2_rev2.utils.GameValues;
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

    public void calculateColliders() {
        for (int i = 1; i < positionArrayList.size(); i++) {
//            GameValues.colliderArrayList.add(new Rect(
//                        (int)positionArrayList.get(i-1).x-60,
//                        (int)positionArrayList.get(i-1).y-60,
//                        (int)positionArrayList.get(i).x+60,
//                        (int)positionArrayList.get(i).y+60
//            ));
            if (positionArrayList.get(i-1).x < positionArrayList.get(i).x || positionArrayList.get(i-1).y < positionArrayList.get(i).y) {
                GameValues.colliderArrayList.add(new Rect(
                        (int)positionArrayList.get(i-1).x-60,
                        (int)positionArrayList.get(i-1).y-60,
                        (int)positionArrayList.get(i).x+60,
                        (int)positionArrayList.get(i).y+60
                ));
            } else {
                GameValues.colliderArrayList.add(new Rect(
                        (int)positionArrayList.get(i).x-60,
                        (int)positionArrayList.get(i).y-60,
                        (int)positionArrayList.get(i-1).x+60,
                        (int)positionArrayList.get(i-1).y+60
                ));
            }
        }
    }

    public void draw(Canvas canvas) {
        Paint testPaint = new Paint();
        testPaint.setColor(Color.GRAY);
        testPaint.setStrokeWidth(96);

        for (int i = 0; i < positionArrayList.size()-1; i++) {
            canvas.drawLine(
                    (float)positionArrayList.get(i).x,
                    (float)positionArrayList.get(i).y,
                    (float)positionArrayList.get(i+1).x,
                    (float)positionArrayList.get(i+1).y,
                    testPaint
            );
            canvas.drawRect(new Rect(
                    (int)positionArrayList.get(i).x-48,
                    (int)positionArrayList.get(i).y-48,
                    (int)positionArrayList.get(i).x+48,
                    (int)positionArrayList.get(i).y+48
            ), testPaint);
        }
    }
}
