package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.CoinTextUI;
import com.example.project2_rev2.gameComponents.SellPriceTextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

public class SellTowerButton extends Button {

    private int currentPrice;
    private Tower tower;
    private TowerManager towerManager;
    private CoinTextUI sellPriceTextUI;

    public SellTowerButton(Tower tower, TowerManager towerManager, Context context) {
        super(xCoordinate(60), yCoordinate(790), R.drawable.sell_button_background, new Size(230, 60), context);
        this.tower = tower;
        this.towerManager = towerManager;
        this.currentPrice = tower.getValue() + 100*tower.getUpgradeCount();
        this.sellPriceTextUI = new SellPriceTextUI(
                (centerPosition.x)-15,
                (centerPosition.y)+13,
                String.valueOf(currentPrice),
                R.color.white,
                35,
                context
        );
    }

    public void updateSellPrice(int newPrice) {
        currentPrice = newPrice;
        sellPriceTextUI.changeText(String.valueOf(newPrice));
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
        sellPriceTextUI.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setPressEffect(false);
                //towerManager.removeTower(tower);
                GameValues.setPlayerCoins(GameValues.getPlayerCoins() + currentPrice);
                tower.deselect();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setPressEffect(true);
            }
        } else {
            setPressEffect(false);
        }
        return false;
    }
}
