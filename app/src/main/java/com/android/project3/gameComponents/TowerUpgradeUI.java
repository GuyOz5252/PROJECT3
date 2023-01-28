package com.android.project3.gameComponents;

import static com.android.project3.utils.HelperMethods.getBitmapFromPicture;
import static com.android.project3.utils.HelperMethods.getBitmapFromVectorDrawable;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.android.project3.R;
import com.android.project3.gameComponents.abstractComponents.Tower;
import com.android.project3.gameComponents.buttons.SellTowerButton;
import com.android.project3.gameComponents.buttons.UpgradeButton;
import com.android.project3.menus.TowerUpgradeInfo;

public class TowerUpgradeUI {

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

    public TowerUpgradeUI(Tower tower, Context context) {
        this.context = context;
        this.upgradeButtonPathOne = new UpgradeButton(260, 0, tower, this, context);
        this.upgradeButtonPathTwo = new UpgradeButton(450, 1, tower, this, context);
        this.tower = tower;

        this.sellTowerButton = new SellTowerButton(tower, context);

        this.towerNameText = new TextUI(
                175,
                235,
                tower.getName(),
                R.color.on_container_text_color,
                50f,
                Paint.Align.CENTER,
                context
        );
        towerNameText.setBold();

        this.towerBackground = Bitmap.createScaledBitmap(getBitmapFromPicture(context, R.drawable.tower_background), 160, 160, false);
        this.towerBitmap = Bitmap.createScaledBitmap(getBitmapFromPicture(context, tower.getIcon()), 150, 150, false);
        this.pressedTowerBitmap = Bitmap.createScaledBitmap(getBitmapFromPicture(context, tower.getIcon()), 140, 140, false);
        this.info = getBitmapFromVectorDrawable(context, R.drawable.ic_info);

        this.isTowerPressed = false;
        this.towerInfoButton = new Rect(
                95,
                25,
                95+towerBackground.getWidth(),
                25+towerBackground.getHeight()
        );
    }

    public UpgradeButton getUpgradeButtonPathOne() {
        return upgradeButtonPathOne;
    }

    public UpgradeButton getUpgradeButtonPathTwo() {
        return upgradeButtonPathTwo;
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
        sellTowerButton.updateSellPrice((int)(tower.getMoneySpent()*0.75));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(
                towerBackground,
                95f,
                25f,
                null
        );
        if (isTowerPressed) {
            canvas.drawBitmap(
                    pressedTowerBitmap,
                    105f,
                    35f,
                    null
            );
        } else {
            canvas.drawBitmap(
                    towerBitmap,
                    100f,
                    30f,
                    null
            );
        }
        canvas.drawBitmap(
                info,
                (float)95+towerBackground.getWidth()-info.getWidth(),
                (float)25+towerBackground.getHeight()-info.getHeight(),
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
