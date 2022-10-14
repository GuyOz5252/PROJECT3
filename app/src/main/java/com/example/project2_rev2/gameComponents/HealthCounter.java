package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.listeners.OnHealthChangeListener;

public class HealthCounter extends TextUI implements OnHealthChangeListener {

    private BitmapObject heartBitmap;
    private float size;

    public HealthCounter(double x, double y, String string, int color, float size, Paint.Align align, Context context) {
        super(x, y, string, color, size, align, context);
    }

    @Override
    public void onHealthChange() {

    }

    @Override
    public void changeText(String string) {
        super.changeText(string);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }
}
