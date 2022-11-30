package com.example.project2_rev2.gameComponents.managers;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;
import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromVectorDrawable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.TextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.button.SellTowerButton;
import com.example.project2_rev2.gameComponents.button.UpgradeButton;
import com.example.project2_rev2.menus.TowerUpgradeInfo;
import com.example.project2_rev2.utils.HelperMethods;

public class TowerUpgradeManager {

    private Context context;
    private UpgradeButton upgradeButtonPathOne;
    private UpgradeButton upgradeButtonPathTwo;
    private Tower tower;
    private SellTowerButton sellTowerButton;
    private TextUI towerNameText;

    private Bitmap towerBackground;
    private Bitmap towerBitmap;
    private Bitmap pressedTowerBitmap;
    private Bitmap info;

    private boolean isTowerPressed;
    private Rect towerInfoButton;

    public TowerUpgradeManager(Tower tower, Context context) {
        this.context = context;
        this.upgradeButtonPathOne = new UpgradeButton(yCoordinate(260), 0, tower, this, context);
        this.upgradeButtonPathTwo = new UpgradeButton(yCoordinate(450), 1, tower, this, context);
        this.tower = tower;

        this.sellTowerButton = new SellTowerButton(tower, context);

        this.towerNameText = new TextUI(
                xCoordinate(175),
                yCoordinate(235),
                tower.getName(),
                R.color.white,
                50f,
                Paint.Align.CENTER,
                context
        );
        towerNameText.setBold();

        this.towerBackground = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(context, R.drawable.tower_background), 160, 160, false);
        this.towerBitmap = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(context, tower.getIcon()), 150, 150, false);
        this.pressedTowerBitmap = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(context, tower.getIcon()), 140, 140, false);
        this.info = Bitmap.createScaledBitmap(getBitmapFromVectorDrawable(context, R.drawable.ic_info), 40, 40, false);

        this.isTowerPressed = false;
        this.towerInfoButton = new Rect(
                (int)xCoordinate(95),
                (int)yCoordinate(25),
                (int)xCoordinate(95+towerBackground.getWidth()),
                (int)yCoordinate(25+towerBackground.getHeight())
        );
    }

    public void clickedTowerInfo(MotionEvent motionEvent) {
        if (towerInfoButton.contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(context, TowerUpgradeInfo.class);
                intent.putExtra("hasNavbar", false);
                intent.putExtra("towerType", tower.getTowerType());
                context.startActivity(intent);
                isTowerPressed = false;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                isTowerPressed = true;
            }
        } else {
            isTowerPressed = false;
        }
    }

    public void postUpgrade() {
        sellTowerButton.updateSellPrice((int)(tower.getValue()*0.75 + 100*tower.getUpgradeCount()));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(
                towerBackground,
                (float)xCoordinate(95),
                (float)yCoordinate(25),
                null
        );
        if (isTowerPressed) {
            canvas.drawBitmap(
                    pressedTowerBitmap,
                    (float)xCoordinate(105),
                    (float)yCoordinate(35),
                    null
            );
        } else {
            canvas.drawBitmap(
                    towerBitmap,
                    (float)xCoordinate(100),
                    (float)yCoordinate(30),
                    null
            );
        }
        canvas.drawBitmap(
                info,
                (float)xCoordinate(95+towerBackground.getWidth()-info.getWidth()),
                (float)yCoordinate(25+towerBackground.getHeight()-info.getHeight()),
                null
        );

        upgradeButtonPathOne.draw(canvas);
        upgradeButtonPathTwo.draw(canvas);

        sellTowerButton.draw(canvas);
        towerNameText.draw(canvas);
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        upgradeButtonPathOne.onTouchEvent(motionEvent);
        upgradeButtonPathTwo.onTouchEvent(motionEvent);
        sellTowerButton.onTouchEvent(motionEvent);
        clickedTowerInfo(motionEvent);
    }
}
