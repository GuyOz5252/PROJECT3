package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.utils.Size;

public class CoinTextUI extends TextUI {

    private Context context;
    protected BitmapObject coinBitmap;
    protected float size;

    public CoinTextUI(double x, double y, String coins, @ColorRes int color, float size, Context context) {
        super(x, y, coins, color, size, Paint.Align.LEFT, context);
        this.context = context;
        this.coinBitmap = new BitmapObject(
                position.x+paint.measureText(coins)+5,
                position.y-size+8,
                R.drawable.coin_icon,
                new Size(size, size),
                context
        ) {};
        this.size = size;
    }

    public void changeColor(@ColorRes int color) {
        paint.setColor(ContextCompat.getColor(context, color));
    }

    @Override
    public void changeText(String string) {
        super.changeText(string);
        coinBitmap.setPosition(position.x+paint.measureText(string)+5, position.y-size+8);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        coinBitmap.draw(canvas);
    }
}
