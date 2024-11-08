package com.android.project3.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.android.project3.R;
import com.android.project3.gameComponents.abstractComponents.RectObject;
import com.android.project3.utils.Size;

public class ProgressBar extends RectObject {

    private Paint borderPaint;
    private Paint progressPaint;

    private double percentage;

    public ProgressBar(double posX, double posY, Size size, int color, int progressColor, Context context) {
        super(posX, posY, size, color);
        this.borderPaint = new Paint();
        this.borderPaint.setColor(ContextCompat.getColor(context, R.color.black));
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(3);
        this.progressPaint = new Paint();
        this.progressPaint.setColor(progressColor);
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void update() {
        System.out.println(percentage);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRect(new Rect(
                        (int)position.x,
                        (int)position.y,
                        (int)(position.x+((size.width)*percentage)),
                        (int)(position.y+size.height)
                ),
                progressPaint
        );
        canvas.drawRect(rect, borderPaint);
    }
}
