package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.gameComponents.CoinTextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.TextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.TowerUpgradeManager;
import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

/**
 * a class that represents the tower upgrade button in the tower upgrade ui
 * on press, upgrade the tower to the next level if the upgrade is unlocked and player has enough money
 */

public class UpgradeButton extends Button implements OnCoinsChangeListener {

    private TowerUpgradeManager towerUpgradeManager;

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

    private BitmapObject upgradeArrows;

    private BitmapObject[] levelIndicator;

    public UpgradeButton(double y, int upgradePathIndex, Tower tower, TowerUpgradeManager towerUpgradeManager, Context context) {
        super(xCoordinate(20), y, R.drawable.upgrade_button_bckground, new Size(310, 160), context);
        GameValues.coinsChangeListenerArrayList.add(this);
        this.towerUpgradeManager = towerUpgradeManager;
        this.upgradePathIndex = upgradePathIndex;
        this.tower = tower;
        this.upgradePath = tower.getTowerUpgradePaths()[upgradePathIndex];
        this.pathLevel = tower.getPathLevels()[upgradePathIndex];

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
                position.x+10,
                position.y+100,
                "MAX LEVEL",
                R.color.coin,
                35,
                Paint.Align.LEFT,
                context
        );
        this.upgradeArrows = new BitmapObject(
                position.x+size.width-130,
                position.y-10,
                R.drawable.ic_upgrade_arrows_green,
                new Size(150, 150),
                context
        ) {};

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
            }
        }
    }

    public void handlePriceColor() {
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

    /**Upgrade Ready**/
    public void drawUpgradeReady(Canvas canvas) {
        upgradeArrows.draw(canvas);
        coinTextUI.draw(canvas);
        for (BitmapObject bitmapObject : levelIndicator) {
            bitmapObject.draw(canvas);
        }
    }

    public void handleLevelIndicator() {
        double xPosition = position.x;
        for (int i = 0; i < tower.getTowerUpgradePaths()[upgradePathIndex].name.length; i++) {
            if (i < tower.getPathLevels()[upgradePathIndex]) {
                levelIndicator[i] = new BitmapObject(
                        xPosition,
                        position.y+110,
                        R.drawable.ic_square_green,
                        new Size(80, 50),
                        context
                ) {};
            } else {
                levelIndicator[i] = new BitmapObject(
                        xPosition,
                        position.y+110,
                        R.drawable.ic_square_gray,
                        new Size(80, 50),
                        context
                ) {};
            }
            xPosition += 50;
        }
    }

    public void postUpgrade() {
        towerUpgradeManager.postUpgrade();
        pathLevel = tower.getPathLevels()[upgradePathIndex];
        if (tower.getPathLevels()[upgradePathIndex] < tower.getTowerUpgradePaths()[upgradePathIndex].name.length) {
            upgradeNameText.changeText(String.valueOf(tower.getTowerUpgradePaths()[upgradePathIndex].name[tower.getPathLevels()[upgradePathIndex]]));
        }

        handleState();
        handlePriceChange();
        handleLevelIndicator();
    }

    @Override
    public void onCoinsChange() {
        handlePriceColor();
    }

    /**XP REQ**/
    public void drawRequireXP(Canvas canvas) {

    }

    /**Max Level**/
    private void drawMaxLevel(Canvas canvas) {
        maxLevelText.draw(canvas);
        for (BitmapObject bitmapObject : levelIndicator) {
            bitmapObject.draw(canvas);
        }
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
        upgradeNameText.draw(canvas);
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
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && upgradeButtonState == UpgradeButtonState.UPGRADE_READY &&
                    (GameValues.getPlayerCoins() >= tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]])) {
                setPressEffect(true);
            }
        } else {
            setPressEffect(false);
        }
        return false;
    }

    public enum UpgradeButtonState {
        XP_REQ,
        UPGRADE_READY,
        MAX_LEVEL
    }
}
