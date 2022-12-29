package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Paint;

public class SellPriceTextUI extends CoinTextUI {

    public SellPriceTextUI(double x, double y, String coins, int color, float size, Context context) {
        super(x, y, "Sell "+coins, color, size, context);
        this.paint.setTextAlign(Paint.Align.CENTER);
        this.coinBitmap.setPosition((position.x+paint.measureText(this.string)/2)+8, position.y-size+5);
    }

    @Override
    public void changeText(String string) {
        super.changeText("Sell "+string);
        coinBitmap.setPosition((position.x+paint.measureText(this.string)/2)+8, position.y-size+5);
    }
}
