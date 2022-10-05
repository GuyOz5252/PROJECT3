package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GaveValues.xCoordinate;
import static com.example.project2_rev2.utils.GaveValues.yCoordinate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.TextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.SellTowerButton;
import com.example.project2_rev2.gameComponents.button.UpgradeButton;

public class TowerUpgradeUI {

    private boolean show;

    private Tower tower;
    private UpgradeButton[] upgradeButtons;
    private SellTowerButton sellTowerButton;
    private TextUI towerNameText;

    public TowerUpgradeUI(Context context) {
        this.show = false;

        this.upgradeButtons = new UpgradeButton[] {
                new UpgradeButton(yCoordinate(250), context),
                new UpgradeButton(yCoordinate(430), context),
                new UpgradeButton(yCoordinate(610), context),
        };
        this.sellTowerButton = new SellTowerButton(context);
        this.towerNameText = new TextUI(
                xCoordinate(175),
                yCoordinate(225),
                "name",
                R.color.black,
                50f,
                Paint.Align.CENTER,
                context
        ) {
            @Override
            public void update() {

            }
        };
    }

    public Tower getTower() {
        return tower;
    }

    public boolean getShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
        this.towerNameText.changeText(tower.getName());
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(tower.getOriginalBitmap(), 150, 150, false), (float)xCoordinate(100), (float)yCoordinate(20), null);

        for (UpgradeButton upgradeButton : upgradeButtons) {
            upgradeButton.draw(canvas);
        }
        sellTowerButton.draw(canvas);
        towerNameText.draw(canvas);
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
