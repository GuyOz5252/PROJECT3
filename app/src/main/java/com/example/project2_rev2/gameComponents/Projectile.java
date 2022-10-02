package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Enemy;
import com.example.project2_rev2.gameComponents.abstractComponents.GameObject;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;

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

        this.radius = 10;
        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, R.color.projectileBullet));
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
        DEMO_BULLET(100, 1, 40f);

        public int speed;
        public int damage;
        public float range;

        ProjectileType(int speed, int damage, float range) {
            this.speed = speed;
            this.damage = damage;
            this.range = range;
        }
    }
}
