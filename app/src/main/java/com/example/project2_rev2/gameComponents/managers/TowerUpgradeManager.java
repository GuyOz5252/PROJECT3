package com.example.project2_rev2.gameComponents.managers;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.TextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.SellTowerButton;
import com.example.project2_rev2.gameComponents.button.UpgradeButton;

public class TowerUpgradeManager {

    private UpgradeButton upgradeButtonPathOne;
    private UpgradeButton upgradeButtonPathTwo;
    private Tower tower;
    private SellTowerButton sellTowerButton;
    private TextUI towerNameText;

    public TowerUpgradeManager(Tower tower, TowerManager towerManager, Context context) {
        this.upgradeButtonPathOne = new UpgradeButton(yCoordinate(260), 0, tower, this, context);
        this.upgradeButtonPathTwo = new UpgradeButton(yCoordinate(450), 1, tower, this, context);
        this.tower = tower;

        this.sellTowerButton = new SellTowerButton(tower, towerManager, context);

        this.towerNameText = new TextUI(
                xCoordinate(175),
                yCoordinate(235),
                tower.getName(),
                R.color.white,
                50f,
                Paint.Align.CENTER,
                context
        );
    }

    public void postUpgrade() {
        sellTowerButton.updateSellPrice((int)(tower.getValue()*0.75 + 100*tower.getUpgradeCount()));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(Bitmap.createScaledBitmap(tower.getOriginalBitmap(), 150, 150, false), (float)xCoordinate(100), (float)yCoordinate(30), null);

        upgradeButtonPathOne.draw(canvas);
        upgradeButtonPathTwo.draw(canvas);

        sellTowerButton.draw(canvas);
        towerNameText.draw(canvas);
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        upgradeButtonPathOne.onTouchEvent(motionEvent);
        upgradeButtonPathTwo.onTouchEvent(motionEvent);
        sellTowerButton.onTouchEvent(motionEvent);
    }
}
