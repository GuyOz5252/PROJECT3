package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromVectorDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.TextUI;
import com.example.project2_rev2.utils.Size;

public class CoinTextUI extends TextUI {

    private Context context;
    private BitmapObject coinBitmap;
    private float size;

    public CoinTextUI(double x, double y, String coins, @ColorRes int color, float size, Context context) {
        super(x, y, coins, color, size, Paint.Align.LEFT, context);
        this.context = context;
        this.coinBitmap = new BitmapObject(
                position.x+70,
                position.y-30,
                R.drawable.ic_lock_focused,
                new Size(40, 40),
                context
        ) {};
        this.size = size;
    }

    public void changeColor(@ColorRes int color) {
        paint.setColor(ContextCompat.getColor(context, color));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        coinBitmap.draw(canvas);
    }

    @Override
    public void update() {

    }
}
