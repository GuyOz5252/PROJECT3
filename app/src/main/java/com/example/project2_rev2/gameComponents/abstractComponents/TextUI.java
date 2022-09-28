package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.GameObject;

public abstract class TextUI extends GameObject {

    private String string;
    private Paint paint;

    public TextUI(double x, double y, String string, int color, Context context) {
        super(x, y);
        this.string = string;
        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, color));
        this.paint.setTextSize(50f);
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
}
