package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.getPlayerCoins;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.annotation.ColorRes;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.CoinTextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.TextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

public class UpgradeButton extends Button implements OnCoinsChangeListener {

    private UpgradeButtonState upgradeButtonState;
    private Tower.TowerUpgradePath upgradePath;
    private int upgradePathIndex;
    private Tower tower;
    private TextUI upgradeName;
    private String upgradeNameString;
    private CoinTextUI coinTextUI;
    private String priceString;

    public UpgradeButton(double y, int upgradePathIndex, Tower tower, Context context) {
        super(xCoordinate(30), y, R.drawable.ic_launcher_background, new Size(290, 150), context);
        GameValues.coinsChangeListenerArrayList.add(this);
        this.upgradePathIndex = upgradePathIndex;
        this.tower = tower;

        if (upgradePathIndex == 0) {
            if (tower.getUpgradeOneReady()) {
                upgradeButtonState = UpgradeButtonState.UPGRADE_READY;
            } else {
                upgradeButtonState = UpgradeButtonState.XP_REQ;
            }
            upgradePath = tower.getTowerUpgradePathOne();
            upgradeNameString = upgradePath.name;
            priceString = String.valueOf(upgradePath.cost[tower.getPathOneLevel()]);
        } else {
            if (tower.getUpgradeTwoReady()) {
                upgradeButtonState = UpgradeButtonState.UPGRADE_READY;
            } else {
                upgradeButtonState = UpgradeButtonState.XP_REQ;
            }
            upgradePath = tower.getTowerUpgradePathTwo();
            upgradeNameString = upgradePath.name;
            priceString = String.valueOf(upgradePath.cost[tower.getPathTwoLevel()]);
        }
        this.upgradeName = new TextUI(
                position.x+50,
                position.y+40,
                upgradeNameString,
                R.color.white,
                40,
                Paint.Align.LEFT,
                context
        ) {
            @Override
            public void update() {

            }
        };
        this.coinTextUI = new CoinTextUI(
                position.x+50,
                position.y+95,
                priceString,
                R.color.white,
                35,
                context
        );
    }

    public void handleState() { // TODO debug
        tower.checkIfUpgradeReady();
        if (upgradePathIndex == 0) {
            if (tower.getUpgradeOneReady()) {
                upgradeButtonState = UpgradeButtonState.UPGRADE_READY;
            } else {
                upgradeButtonState = UpgradeButtonState.XP_REQ;
            }
        } else {
            if (tower.getUpgradeTwoReady()) {
                upgradeButtonState = UpgradeButtonState.UPGRADE_READY;
            } else {
                upgradeButtonState = UpgradeButtonState.XP_REQ;
            }
        }
        // TODO check for MAX_LEVEL
    }

    public void handlePriceColor() {
        if (upgradePathIndex == 0) {
            if (GameValues.getPlayerCoins() >= upgradePath.cost[tower.getPathOneLevel()]) {
                coinTextUI.changeColor(R.color.white);
            } else {
                coinTextUI.changeColor(R.color.red);
            }
        } else {
            if (GameValues.getPlayerCoins() >= upgradePath.cost[tower.getPathTwoLevel()]) {
                coinTextUI.changeColor(R.color.white);
            } else {
                coinTextUI.changeColor(R.color.red);
            }
        }
    }

    public void handlePriceChange() {
        if (upgradePathIndex == 0) {
            coinTextUI.changeText(String.valueOf(tower.getTowerUpgradePathOne().cost[tower.getPathOneLevel()]));
        } else {
            coinTextUI.changeText(String.valueOf(tower.getTowerUpgradePathTwo().cost[tower.getPathTwoLevel()]));
        }
    }

    public void drawUpgradeReady(Canvas canvas) {
        upgradeName.draw(canvas);
        coinTextUI.draw(canvas);
    }

    public void drawRequireXP(Canvas canvas) {

    }

    private void drawMaxLevel(Canvas canvas) {

    }

    private void postUpgrade() {
        handleState();
        handlePriceChange();
        handlePriceColor();
    }

    @Override
    public void onCoinsChange() {
        if (upgradeButtonState != UpgradeButtonState.MAX_LEVEL) {
            handlePriceColor();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        switch (upgradeButtonState) {
            case XP_REQ:
                drawRequireXP(canvas);
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
