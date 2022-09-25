package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.GameObject;

public class Projectile extends GameObject {

    private double radius;
    private Paint paint;

    private int velocityX;
    private int velocityY;
    private boolean isActive;

    public Projectile(double x, double y, double radius, Context context) {
        super(x, y);
        this.velocityX = 0;
        this.velocityY = 0;
        this.isActive = true;

        this.radius = radius;
        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, R.color.projectileBullet));
    }

    public void movement() {
        position.x += velocityX;
        position.y += velocityY;
    }

    @Override
    public void update() {
        if (isActive) {
            movement();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float)position.x, (float)position.y, (float)radius, paint);
    }
}
