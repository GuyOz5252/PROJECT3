package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.TowerDragButton;
import com.example.project2_rev2.utils.Size;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class DragAndDropUI {

    private Context context;
    private ArrayList<TowerDragButton> towerDragButtonArrayList;

    public DragAndDropUI(Context context) {
        this.context = context;
        ArrayList<Tower.TowerType> towerTypeArrayList = new ArrayList<>(Arrays.asList(Tower.TowerType.values()));
        this.towerDragButtonArrayList = new ArrayList<>();
        towerTypeArrayList.forEach(towerType -> towerDragButtonArrayList.add(
                new TowerDragButton(
                        80,
                        R.drawable.tower_background,
                        towerType,
                        new Size(190, 190),
                        context
                )
        ));
    }

    public void drawTowerButtons(Canvas canvas) {
        if (towerDragButtonArrayList.size() > 3) {

        } else {
            AtomicInteger y = new AtomicInteger(150);
            towerDragButtonArrayList.forEach(towerDragButton -> {
                towerDragButton.setY(y.get());
                y.addAndGet(210);
                towerDragButton.draw(canvas);
            });
        }
    }

    public void draw(Canvas canvas) {
        drawTowerButtons(canvas);
    }

    public void update() {

    }

    public void onTouchEvent(MotionEvent motionEvent) {
        towerDragButtonArrayList.forEach(towerDragButton -> towerDragButton.onTouchEvent(motionEvent));
    }
}
