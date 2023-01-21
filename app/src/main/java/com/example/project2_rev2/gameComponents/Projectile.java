package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.GameObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;

public class Projectile extends GameObject {

    private double radius;
    private Paint paint;

    private int velocityX;
    private int velocityY;
    protected boolean isActive;
    protected ProjectileType projectileType;

    protected Tower originTower;

    private Projectile(double x, double y, int velocityX, int velocityY, ProjectileType projectileType, Tower originTower, Context context) {
        super(x, y);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.isActive = true;
        this.projectileType = projectileType;

        this.radius = projectileType.radius;
        this.paint = new Paint();
        this.paint.setColor(ContextCompat.getColor(context, projectileType.color));

        this.originTower = originTower;
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
                enemy.receiveDamage(projectileType.damage, originTower);
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

    public static class ProjectileFactory {

        public Projectile createProjectile(double x, double y, int velocityX, int velocityY, ProjectileType projectileType, Tower originTower, Context context) {
            return new Projectile(x, y, velocityX, velocityY, projectileType, originTower, context);
        }
    }

    public enum ProjectileType {
        DEMO_BULLET(R.color.bullet_projectile, 100, 3, 40f, 10, false),

        TURRET_BULLETS(R.color.black, 120, 2, 35f, 5, false),
        TURRET_BULLETS_V2(R.color.black, 120, 4, 35f, 5, false),
        TURRET_BULLETS_V3(R.color.black, 100, 6, 35f, 8, false),
        TURRET_BULLETS_V4(R.color.black, 100, 6, 35f, 8, true),
        TANK_PROJECTILE(R.color.tank_projectile, 100, 20, 100f, 13, true);

        public int color;
        public int speed;
        public int damage;
        public float range;
        public int radius;
        public boolean isArmorPenetrating;

        ProjectileType(int color, int speed, int damage, float range, int radius, boolean isArmorPenetrating) {
            this.color = color;
            this.speed = speed;
            this.damage = damage;
            this.range = range;
            this.radius = radius;
            this.isArmorPenetrating = isArmorPenetrating;
        }
    }
}
