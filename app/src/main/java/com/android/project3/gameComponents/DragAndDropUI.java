package com.android.project3.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.android.project3.R;
import com.android.project3.data.TowerType;
import com.android.project3.gameComponents.buttons.NextTowerPageButton;
import com.android.project3.gameComponents.buttons.PrevTowerPageButton;
import com.android.project3.gameComponents.buttons.TowerDragButton;
import com.android.project3.gameComponents.managers.TowerManager;
import com.android.project3.utils.Size;

import java.util.ArrayList;
import java.util.Arrays;

public class DragAndDropUI {

    private ArrayList<TowerDragButton> towerDragButtonArrayList;
    private int startTowerPageIndex;
    private TextUI towerNameText;

    private PrevTowerPageButton prevTowerPageButton;
    private NextTowerPageButton nextTowerPageButton;

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
        setStartTowerPageIndex(0);
        this.towerNameText = new TextUI(
                175,
                80,
                "Towers",
                R.color.on_container_text_color,
                70f,
                Paint.Align.CENTER,
                context
        );
        this.towerNameText.setBold();
        this.prevTowerPageButton = new PrevTowerPageButton(this, context);
        this.nextTowerPageButton = new NextTowerPageButton(this, context);
    }

    public int getStartTowerPageIndex() {
        return startTowerPageIndex;
    }

    public void setStartTowerPageIndex(int startTowerPageIndex) {
        this.startTowerPageIndex = startTowerPageIndex;
        for (int i = startTowerPageIndex, y = 110; i < startTowerPageIndex+3; i++, y+=220) {
            if (i < towerDragButtonArrayList.size()) {
                towerDragButtonArrayList.get(i).setY(y);
            }
        }
    }

    public ArrayList<TowerDragButton> getTowerDragButtonArrayList() {
        return towerDragButtonArrayList;
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
        prevTowerPageButton.draw(canvas);
        nextTowerPageButton.draw(canvas);
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
        prevTowerPageButton.onTouchEvent(motionEvent);
        nextTowerPageButton.onTouchEvent(motionEvent);
    }
}
