package com.android.project3.gameComponents.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.android.project3.R;
import com.android.project3.data.TowerType;
import com.android.project3.data.User;
import com.android.project3.gameComponents.CoinTextUI;
import com.android.project3.gameComponents.ProgressBar;
import com.android.project3.gameComponents.abstractComponents.BitmapObject;
import com.android.project3.gameComponents.abstractComponents.Button;
import com.android.project3.gameComponents.TextUI;
import com.android.project3.gameComponents.abstractComponents.Tower;
import com.android.project3.gameComponents.TowerUpgradeUI;
import com.android.project3.listeners.OnCoinsChangeListener;
import com.android.project3.data.GameValues;
import com.android.project3.utils.Position;
import com.android.project3.utils.Size;

/**
 * a class that represents the tower upgrade button in the tower upgrade ui
 * on press, upgrade the tower to the next level if the upgrade is unlocked and player has enough money
 */

public class UpgradeButton extends Button implements OnCoinsChangeListener {

    private TowerUpgradeUI towerUpgradeUI;

    private int upgradePathIndex;
    private UpgradeButtonState upgradeButtonState;
    private TowerType.TowerUpgradePath upgradePath;
    private int pathLevel;
    private Tower tower;

    private TextUI upgradeNameText;
    private String upgradeNameString;

    private CoinTextUI coinTextUI;
    private String priceString;

    private TextUI maxLevelText;

    private TextUI xpReqText;
    private BitmapObject xpLock;
    private ProgressBar xpBar;

    private BitmapObject upgradeArrows;

    private BitmapObject[] levelIndicator;

    public UpgradeButton(double y, int upgradePathIndex, Tower tower, TowerUpgradeUI towerUpgradeUI, Context context) {
        super(20, y, R.drawable.upgrade_button_bckground, new Size(310, 160), context);
        GameValues.coinsChangeListenerArrayList.add(this);
        this.towerUpgradeUI = towerUpgradeUI;
        this.upgradePathIndex = upgradePathIndex;
        this.tower = tower;
        this.upgradePath = tower.getTowerUpgradePaths()[upgradePathIndex]; //
        this.pathLevel = tower.getPathLevels()[upgradePathIndex]; //

        this.upgradeNameString = tower.getTowerUpgradePaths()[upgradePathIndex].name[tower.getPathLevels()[upgradePathIndex]];
        this.priceString = String.valueOf(tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]]);
        this.upgradeNameText = new TextUI(
                position.x+10,
                position.y+50,
                upgradeNameString,
                R.color.white,
                40,
                Paint.Align.LEFT,
                context
        );
        this.upgradeNameText.setBold();
        this.upgradeNameText.setShadow();
        this.coinTextUI = new CoinTextUI(
                position.x+10,
                position.y+100,
                priceString,
                R.color.white,
                35,
                context
        );
        this.maxLevelText = new TextUI(
                position.x+15,
                position.y+90,
                "MAX LEVEL",
                R.color.coin,
                52,
                Paint.Align.LEFT,
                context
        );
        this.maxLevelText.setBold();
        this.maxLevelText.setShadow();
        this.upgradeArrows = new BitmapObject(
                position.x+size.width-130,
                position.y-10,
                R.drawable.ic_upgrade_arrows_green,
                new Size(150, 150),
                context
        ) {};
        this.xpReqText = new TextUI(
                position.x+10,
                position.y+50,
                "XP Required",
                R.color.white,
                40,
                Paint.Align.LEFT,
                context
        );
        this.xpReqText.setBold();
        this.xpReqText.setShadow();
        this.xpLock = new BitmapObject(
                position.x+size.width-105,
                position.y+30,
                R.drawable.ic_lock_unfocused,
                new Size(100, 100),
                context
        ) {};
        this.xpBar = new ProgressBar(
                position.x+10,
                position.y+70,
                new Size(190, 30),
                ContextCompat.getColor(context, R.color.gray),
                ContextCompat.getColor(context, R.color.green),
                context
        );
        this.xpBar.setPercentage(
                (double)User.getInstance().getTowerXP(tower.getTowerType()) /
                        tower.getTowerUpgradePaths()[upgradePathIndex].xpReq[tower.getPathLevels()[upgradePathIndex]]
        );

        this.pressedBitmap = Bitmap.createScaledBitmap(originalBitmap, (int)(size.width-size.width/50), (int)(size.height-size.height/50), false);
        this.pressedPosition = new Position(position.x+size.width/100, position.y+size.height/100);

        this.levelIndicator = new BitmapObject[tower.getTowerUpgradePaths()[upgradePathIndex].name.length];

        handleState();
        handlePriceColor();
        handleLevelIndicator();
    }

    public void handleState() {
        if (tower.getPathLevels()[upgradePathIndex] >= tower.getTowerUpgradePaths()[upgradePathIndex].name.length) {
            upgradeButtonState = UpgradeButtonState.MAX_LEVEL;
        } else {
            if (tower.getXP() >= tower.getTowerUpgradePaths()[upgradePathIndex].xpReq[tower.getPathLevels()[upgradePathIndex]]) {
                upgradeButtonState = UpgradeButtonState.UPGRADE_READY;
            } else {
                upgradeButtonState = UpgradeButtonState.XP_REQ;
                xpBar.setPercentage(
                        (double)User.getInstance().getTowerXP(tower.getTowerType()) /
                                tower.getTowerUpgradePaths()[upgradePathIndex].xpReq[tower.getPathLevels()[upgradePathIndex]]
                );
            }
        }
    }

    public void handlePriceColor() {
        if (coinTextUI == null) return;
        if (tower.getPathLevels()[upgradePathIndex] < tower.getTowerUpgradePaths()[upgradePathIndex].name.length) {
            if (GameValues.getPlayerCoins() >= tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]]) {
                coinTextUI.changeColor(R.color.white);
                upgradeArrows.changeBitmap(R.drawable.ic_upgrade_arrows_green);
            } else {
                coinTextUI.changeColor(R.color.red);
                upgradeArrows.changeBitmap(R.drawable.ic_upgrade_arrows_red);
            }
        }
    }

    public void handlePriceChange() {
        if (tower.getPathLevels()[upgradePathIndex] < tower.getTowerUpgradePaths()[upgradePathIndex].name.length) {
            coinTextUI.changeText(String.valueOf(tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]]));
        }
    }

    public void handleLevelIndicator() {
        double xPosition = position.x-10;
        for (int i = 0; i < tower.getTowerUpgradePaths()[upgradePathIndex].name.length; i++) {
            levelIndicator[i] = new BitmapObject(
                    xPosition,
                    position.y+110,
                    (i < tower.getPathLevels()[upgradePathIndex]) ? R.drawable.ic_square_green : R.drawable.ic_square_gray,
                    new Size(80, 50),
                    context
            ) {};
            xPosition += 50;
        }
    }

    public void postUpgrade() {
        towerUpgradeUI.postUpgrade();
        pathLevel = tower.getPathLevels()[upgradePathIndex];
        if (tower.getPathLevels()[upgradePathIndex] < tower.getTowerUpgradePaths()[upgradePathIndex].name.length) {
            upgradeNameText.changeText(String.valueOf(tower.getTowerUpgradePaths()[upgradePathIndex].name[tower.getPathLevels()[upgradePathIndex]]));
        }

        handleState();
        handlePriceChange();
        handleLevelIndicator();
    }

    /**Upgrade Ready**/
    public void drawUpgradeReady(Canvas canvas) {
        upgradeArrows.draw(canvas);
        coinTextUI.draw(canvas);
        for (BitmapObject bitmapObject : levelIndicator) {
            bitmapObject.draw(canvas);
        }
        upgradeNameText.draw(canvas);
    }

    /**XP REQ**/
    public void drawRequireXP(Canvas canvas) {
        xpLock.draw(canvas);
        xpReqText.draw(canvas);
        xpBar.draw(canvas);
        for (BitmapObject bitmapObject : levelIndicator) {
            bitmapObject.draw(canvas);
        }
    }

    /**Max Level**/
    private void drawMaxLevel(Canvas canvas) {
        maxLevelText.draw(canvas);
        for (BitmapObject bitmapObject : levelIndicator) {
            bitmapObject.draw(canvas);
        }
    }

    @Override
    public void onCoinsChange() {
        handlePriceColor();
    }

    @Override
    public void setPressEffect(boolean b) {
        if (b) {
            bitmap = pressedBitmap;
            position = pressedPosition;
        } else {
            bitmap = originalBitmap;
            position = originalPosition;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        switch (upgradeButtonState) {
            case XP_REQ:
                drawRequireXP(canvas);
                break;
            case UPGRADE_READY:
                drawUpgradeReady(canvas);
                break;
            case MAX_LEVEL:
                drawMaxLevel(canvas);
                break;
        }
    }

    public boolean canUpgrade(MotionEvent motionEvent) {
        return motionEvent.getAction() == MotionEvent.ACTION_DOWN && upgradeButtonState == UpgradeButtonState.UPGRADE_READY &&
                (GameValues.getPlayerCoins() >= tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setPressEffect(false);
                if (tower.upgrade(upgradePathIndex)) {
                    postUpgrade();
                    return true;
                }
            } else if (canUpgrade(motionEvent)) {
                setPressEffect(true);
            }
        } else {
            setPressEffect(false);
        }
        return false;
    }

    private enum UpgradeButtonState {
        XP_REQ,
        UPGRADE_READY,
        MAX_LEVEL
    }
}
