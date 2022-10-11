package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.CoinTextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.TextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

public class UpgradeButton extends Button implements OnCoinsChangeListener {

    private int upgradePathIndex;
    private UpgradeButtonState upgradeButtonState;
    private Tower.TowerUpgradePath upgradePath;
    private int pathLevel;
    private Tower tower;
    private TextUI upgradeName;
    private String upgradeNameString;
    private CoinTextUI coinTextUI;
    private String priceString;
    private Bitmap greenArrowUpgradeButton;
    private Bitmap redArrowUpgradeButton;

    public UpgradeButton(double y, int upgradePathIndex, Tower tower, Context context) {
        super(xCoordinate(30), y, R.drawable.ic_launcher_background, new Size(290, 150), context);
        GameValues.coinsChangeListenerArrayList.add(this);
        this.upgradePathIndex = upgradePathIndex;
        this.tower = tower;
        this.upgradePath = tower.getTowerUpgradePaths()[upgradePathIndex];
        this.pathLevel = tower.getPathLevels()[upgradePathIndex];

        this.upgradeNameString = tower.getTowerUpgradePaths()[upgradePathIndex].name;
        this.priceString = String.valueOf(tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]]);
        this.upgradeName = new TextUI(
                position.x+50,
                position.y+40,
                upgradeNameString,
                R.color.white,
                40,
                Paint.Align.LEFT,
                context
        );
        this.coinTextUI = new CoinTextUI(
                position.x+50,
                position.y+95,
                priceString,
                R.color.white,
                35,
                context
        );

        handleState();
        handlePriceColor();
    }

    public void handleState() {
        if (tower.getPathLevels()[upgradePathIndex] >= tower.getTowerUpgradePaths()[upgradePathIndex].value.length) {
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
        if (tower.getPathLevels()[upgradePathIndex] < tower.getTowerUpgradePaths()[upgradePathIndex].value.length) {
            if (GameValues.getPlayerCoins() >= tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]]) {
                coinTextUI.changeColor(R.color.white);
                //bitmap = greenArrowUpgradeButton;
            } else {
                coinTextUI.changeColor(R.color.red);
                //bitmap = redArrowUpgradeButton;
            }
        }
    }

    public void handlePriceChange() {
        if (tower.getPathLevels()[upgradePathIndex] < tower.getTowerUpgradePaths()[upgradePathIndex].value.length) {
            coinTextUI.changeText(String.valueOf(tower.getTowerUpgradePaths()[upgradePathIndex].cost[tower.getPathLevels()[upgradePathIndex]]));
        }
    }

    /**Upgrade Ready**/
    public void drawUpgradeReady(Canvas canvas) {
        upgradeName.draw(canvas);
        coinTextUI.draw(canvas);
    }

    private void postUpgrade() {
        pathLevel = tower.getPathLevels()[upgradePathIndex];

        handleState();
        handlePriceChange();
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

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (tower.upgrade(upgradePathIndex)) {
                postUpgrade();
                return true;
            }
        }
        return false;
    }

    public enum UpgradeButtonState {
        XP_REQ,
        UPGRADE_READY,
        MAX_LEVEL
    }
}
