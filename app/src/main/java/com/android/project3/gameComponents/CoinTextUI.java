package com.android.project3.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.android.project3.R;
import com.android.project3.gameComponents.abstractComponents.BitmapObject;
import com.android.project3.utils.Size;

public class CoinTextUI extends TextUI {

    private Context context;
    protected BitmapObject coinBitmap;
    protected float size;

    public CoinTextUI(double x, double y, String coins, @ColorRes int color, float size, Context context) {
        super(x, y, coins, color, size, Paint.Align.LEFT, context);
        this.context = context;
        this.coinBitmap = new BitmapObject(
                position.x+paint.measureText(coins)+8,
                position.y-size+5,
                R.drawable.coin_icon,
                new Size(size, size),
                context
        ) {};
        this.size = size;
    }

    public Paint getCoinPaint() {
        return coinBitmap.getPaint();
    }

    public void changeColor(@ColorRes int color) {
        paint.setColor(ContextCompat.getColor(context, color));
    }

    @Override
    public void changeText(String string) {
        super.changeText(string);
        coinBitmap.setPosition(position.x+paint.measureText(string)+8, position.y-size+5);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        coinBitmap.draw(canvas);
    }
}
