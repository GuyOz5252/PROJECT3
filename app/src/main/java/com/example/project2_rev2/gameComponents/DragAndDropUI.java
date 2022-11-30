package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.TowerDragButton;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.utils.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class DragAndDropUI {

    private ArrayList<TowerDragButton> towerDragButtonArrayList;
    private int startTowerPageIndex;
    private TextUI towerNameText;

    public DragAndDropUI(TowerManager towerManager, Context context) {
        ArrayList<TowerType> towerTypeArrayList = new ArrayList<>(Arrays.asList(TowerType.values()));
        this.towerDragButtonArrayList = new ArrayList<>();
        towerTypeArrayList.forEach(towerType -> this.towerDragButtonArrayList.add(
                new TowerDragButton(
                        R.drawable.tower_background,
                        towerManager,
                        towerType,
                        new Size(190, 190),
                        context
                )
        ));
        this.startTowerPageIndex = 0;
        for (int i = startTowerPageIndex, y = 110; i < startTowerPageIndex+3; i++, y+=220) {
            if (i < towerDragButtonArrayList.size()) {
                towerDragButtonArrayList.get(i).setY(y);
            }
        }
        this.startTowerPageIndex = 3;
        for (int i = startTowerPageIndex, y = 110; i < startTowerPageIndex+3; i++, y+=220) {
            if (i < towerDragButtonArrayList.size()) {
                towerDragButtonArrayList.get(i).setY(y);
            }
        }
        this.startTowerPageIndex = 0;
        this.towerNameText = new TextUI(
                xCoordinate(175),
                yCoordinate(80),
                "Towers",
                R.color.white,
                70f,
                Paint.Align.CENTER,
                context
        );
        this.towerNameText.setBold();
    }

    public void drawTowerButtons(Canvas canvas) {
        for (int i = startTowerPageIndex; i < startTowerPageIndex+3; i++) {
            if (i < towerDragButtonArrayList.size()) {
                towerDragButtonArrayList.get(i).draw(canvas);
            }
        }
    }

    public void draw(Canvas canvas) {
        towerNameText.draw(canvas);
        drawTowerButtons(canvas);
    }

    public void update() {
        for (int i = startTowerPageIndex; i < startTowerPageIndex+3; i++) {
            if (i < towerDragButtonArrayList.size()) {
                towerDragButtonArrayList.get(i).update();
            }
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        for (int i = startTowerPageIndex; i < startTowerPageIndex+3; i++) {
            if (i < towerDragButtonArrayList.size()) {
                towerDragButtonArrayList.get(i).onTouchEvent(motionEvent);
            }
        }
    }
}
