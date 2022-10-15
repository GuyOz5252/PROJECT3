package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.utils.GameValues;

public class CoinCounter extends CoinTextUI implements OnCoinsChangeListener {

    public CoinCounter(Context context) {
        super(xCoordinate(gameDisplay.size.width-400), yCoordinate(55), String.valueOf(GameValues.getPlayerCoins()), R.color.coin, 55, context);
        GameValues.coinsChangeListenerArrayList.add(this);
    }

    @Override
    public void onCoinsChange() {
        changeText(String.valueOf(GameValues.getPlayerCoins()));
    }
}
