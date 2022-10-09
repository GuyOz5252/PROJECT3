package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.GameObject;

import java.util.ArrayList;

public class Projectile extends GameObject {

    private double radius;
    private Paint paint;

    private int velocityX;
    private int velocityY;
    private boolean isActive;
    private ProjectileType projectileType;

    public Projectile(double x, double y, int velocityX, int velocityY, ProjectileType projectileType, Context context) {
        super(x, y);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.isActive = true;
        this.projectileType = projectileType;

        this.radius = projectileType.radius;
        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, projectileType.color));
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void movement() {
        position.x += velocityX;
        position.y += velocityY;
    }

    public void hitEnemy(ArrayList<Enemy> enemyArrayList) {
        for (Enemy enemy : enemyArrayList) {
            float range = projectileType.range;
            float xDistance = (float) (position.x - enemy.getCenterPosition().x);
            float yDistance = (float) (position.y - enemy.getCenterPosition().y);
            float distance = (float) Math.hypot(xDistance, yDistance);
            if (distance <= range) {
                enemy.receiveDamage(projectileType.damage);
                isActive = false;
            }
        }
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

    public enum ProjectileType {
        DEMO_BULLET(R.color.bulletProjectile, 100, 3, 40f, 10),
        LASER_BEAM(R.color.laserBeamProjectile, 50, 3, 40f, 25);

        public int color;
        public int speed;
        public int damage;
        public float range;
        public int radius;

        ProjectileType(int color, int speed, int damage, float range, int radius) {
            this.color = color;
            this.speed = speed;
            this.damage = damage;
            this.range = range;
            this.radius = radius;
        }
    }
}
