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
                (position.x+size.width/2)-15,
                (position.y+size.height/2)+13,
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

    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        sellPriceTextUI.getPaint().setAlpha(alpha);
        sellPriceTextUI.getCoinPaint().setAlpha(alpha);
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
                setAlpha(255);
                towerManager.removeTower(tower);
                GameValues.setPlayerCoins(GameValues.getPlayerCoins() + currentPrice);
                tower.deselect();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setAlpha(100);
            }
        } else {
            setAlpha(255);
        }
        return false;
    }
}
