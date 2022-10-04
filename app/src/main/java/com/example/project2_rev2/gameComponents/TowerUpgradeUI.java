package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GaveValues.xCoordinate;
import static com.example.project2_rev2.utils.GaveValues.yCoordinate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.SellTowerButton;
import com.example.project2_rev2.gameComponents.button.UpgradeButton;

public class TowerUpgradeUI {

    private Tower tower;
    private UpgradeButton[] upgradeButtons;
    private SellTowerButton sellTowerButton;

    public TowerUpgradeUI(Context context) {
        this.upgradeButtons = new UpgradeButton[] {
                new UpgradeButton(250, context),
                new UpgradeButton(430, context),
                new UpgradeButton(610, context),
        };
        this.sellTowerButton = new SellTowerButton(context);
    }

    public Tower getTower() {
        return tower;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(tower.getOriginalBitmap(), 150, 150, false), (float)xCoordinate(100), (float)yCoordinate(20), null);

        for (UpgradeButton upgradeButton : upgradeButtons) {
            upgradeButton.draw(canvas);
        }
        sellTowerButton.draw(canvas);
    }

    public void update() {

    }

    public void onTouchEvent(MotionEvent motionEvent) {
        for (UpgradeButton upgradeButton : upgradeButtons) {
            upgradeButton.onTouchEvent(motionEvent);
        }
        sellTowerButton.onTouchEvent(motionEvent);
    }
}
