package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;

import android.content.Context;
import android.graphics.Paint;

import com.example.project2_rev2.R;
import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.utils.GameValues;

public class CoinCounter extends CoinTextUI implements OnCoinsChangeListener {

    public CoinCounter(Context context) {
        super(
                gameDisplay.size.width,
                55,
                String.valueOf(GameValues.getPlayerCoins()),
                R.color.coin,
                55,
                context
        );
        GameValues.coinsChangeListenerArrayList.add(this);
        this.paint.setTextAlign(Paint.Align.RIGHT);
        setBold();
        setShadow();
        setPosition(gameDisplay.size.width-paint.measureText("xxx")-170, position.y);
        changeText(String.valueOf(GameValues.getPlayerCoins()));
        coinBitmap.setPosition(position.x+10, position.y-size+8);
    }

    @Override
    public void changeText(String string) {
        this.string = string;
    }

    @Override
    public void onCoinsChange() {
        changeText(String.valueOf(GameValues.getPlayerCoins()));
    }
}
