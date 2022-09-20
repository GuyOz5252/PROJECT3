package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.RectObject;
import com.example.project2_rev2.utils.Display;

public class TowerBar extends RectObject {

    private Paint borderPaint;

    public TowerBar(Display display, Context context) {
        super(0, 0, 350, display.size.height, ContextCompat.getColor(context, R.color.cardview_dark_background));
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(5);
        borderPaint.setColor(Color.BLACK);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(this.rect, borderPaint);
    }

    @Override
    public void update() {

    }
}
