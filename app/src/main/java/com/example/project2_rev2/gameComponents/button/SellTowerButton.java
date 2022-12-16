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
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

/**
 * a class that represents the sell tower button from the tower upgrade ui
 * on press, remove tower from tower array list in tower manager
 */

public class SellTowerButton extends Button {

    private int currentPrice;
    private Tower tower;
    private CoinTextUI sellPriceTextUI;

    public SellTowerButton(Tower tower, Context context) {
        super(xCoordinate(60), yCoordinate(790), R.drawable.sell_button_background, new Size(230, 60), context);
        this.tower = tower;
        this.currentPrice = (int)(tower.getMoneySpent()*0.75);
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
                GameValues.setPlayerCoins(GameValues.getPlayerCoins() + currentPrice);
                tower.sell();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setPressEffect(true);
            }
        } else {
            setPressEffect(false);
        }
        return false;
    }
}
