package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.GameObject;

public class TextUI extends GameObject {

    protected String string;
    protected Paint paint;

    public TextUI(double x, double y, String string, @ColorRes int color, float size, Paint.Align align, Context context) {
        super(x, y);
        this.string = string;
        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, color));
        this.paint.setTextSize(size);
        this.paint.setTextAlign(align);
        //Typeface typeface = Typeface.createFromAsset(context.getAssets(), R.font.font_name);
        //paint.setTypeface(typeface);
    }

    public void changeText(String string) {
        this.string = string;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(string, (float)position.x, (float)position.y, paint);
    }

    @Override
    public void update() {

    }
}
