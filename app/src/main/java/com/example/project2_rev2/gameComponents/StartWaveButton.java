package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Display;

public class StartWaveButton extends Button {

    private Paint textPaint;
    private String string;

    public StartWaveButton(Display display, Context context) {
        super(75, display.size.height-150, 200, 100, ContextCompat.getColor(context, R.color.primaryColor));
        this.string = "Start Wave";
        this.textPaint = new Paint();
        this.textPaint.setTextAlign(Paint.Align.CENTER);
        this.textPaint.setTextSize(55);
        this.textPaint.setColor(ContextCompat.getColor(context, R.color.primaryTextColor));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawText(string, (float)(position.x+size.width/2), (float)(position.y+size.height/2+25), textPaint);
    }
}
