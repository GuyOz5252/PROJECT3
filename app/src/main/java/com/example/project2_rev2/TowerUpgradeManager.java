package com.example.project2_rev2;

import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.UpgradeButton;

public class TowerUpgradeManager {

    private UpgradeButton[] upgradeButtons;
    private Tower tower;

    public TowerUpgradeManager(Tower tower, Context context) {
        this.upgradeButtons = new UpgradeButton[] {
                new UpgradeButton(yCoordinate(250), 0, tower, context),
                new UpgradeButton(yCoordinate(430), 1, tower, context)
        };
        this.tower = tower;
    }

    public void draw(Canvas canvas) {
        for (UpgradeButton upgradeButton : upgradeButtons) {
            upgradeButton.draw(canvas);
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        for (UpgradeButton upgradeButton : upgradeButtons) {
            upgradeButton.onTouchEvent(motionEvent);
        }
    }
}
