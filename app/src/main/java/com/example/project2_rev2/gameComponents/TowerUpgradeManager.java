package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.TextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.SellTowerButton;
import com.example.project2_rev2.gameComponents.button.UpgradeButton;

public class TowerUpgradeUI {

    private Context context;
    private boolean show;

    private UpgradeButton upgradeButtonPathOne;
    private UpgradeButton upgradeButtonPathTwo;
    private Tower tower;
    private SellTowerButton sellTowerButton;
    private TextUI towerNameText;

    public TowerUpgradeUI(Tower tower, Context context) {
        this.context = context;
        this.show = false;

        this.upgradeButtonPathOne = new UpgradeButton(yCoordinate(250), 0, tower, context);
        this.upgradeButtonPathTwo = new UpgradeButton(yCoordinate(430), 1, tower, context);
        this.tower = tower;

        this.sellTowerButton = new SellTowerButton(context);

        this.towerNameText = new TextUI(
                xCoordinate(175),
                yCoordinate(225),
                tower.getName(),
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

    public boolean getShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void updateUI() {

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(tower.getOriginalBitmap(), 150, 150, false), (float)xCoordinate(100), (float)yCoordinate(20), null);

        upgradeButtonPathOne.draw(canvas);
        upgradeButtonPathTwo.draw(canvas);

        sellTowerButton.draw(canvas);
        towerNameText.draw(canvas);
    }

    public void update() {

    }

    public void onTouchEvent(MotionEvent motionEvent) {
        if (upgradeButtonPathOne.onTouchEvent(motionEvent)) {
            updateUI();
        }
        if (upgradeButtonPathTwo.onTouchEvent(motionEvent)) {
            updateUI();
        }

        sellTowerButton.onTouchEvent(motionEvent);
    }
}
