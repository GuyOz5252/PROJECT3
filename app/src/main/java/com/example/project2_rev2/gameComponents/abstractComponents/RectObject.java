package com.example.project2_rev2.gameComponents.abstractComponents;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.project2_rev2.utils.Size;

public abstract class RectObject extends GameObject {

    protected Rect rect;
    protected Size size;
    protected Paint paint;

    public RectObject(double posX, double posY, double width, double height, int color) {
        super(posX, posY);
        this.size = new Size(width, height);
        this.paint = new Paint();
        this.paint.setColor(color);
        this.rect = new Rect(
                (int)position.x,
                (int)position.y,
                (int)(position.x+size.width),
                (int)(position.y+size.height)
        );
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rect, paint);
    }
}
