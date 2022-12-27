package com.example.project2_rev2.gameComponents;

import android.content.Context;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;

public class RailgunProjectile extends Projectile {

    private int pierceAmount;

    public RailgunProjectile(double x, double y, int pierceAmount, int velocityX, int velocityY, ProjectileType projectileType, Tower originTower, Context context) {
        super(x, y, velocityX, velocityY, projectileType, originTower, context);
        this.pierceAmount = pierceAmount;
    }

    @Override
    public void hitEnemy(ArrayList<Enemy> enemyArrayList) {
        for (Enemy enemy : enemyArrayList) {
            float range = projectileType.range;
            float xDistance = (float) (position.x - enemy.getCenterPosition().x);
            float yDistance = (float) (position.y - enemy.getCenterPosition().y);
            float distance = (float) Math.hypot(xDistance, yDistance);
            if (distance <= range) {
                enemy.receiveDamage(projectileType.damage, originTower);
                pierceAmount--;
                if (pierceAmount <= 0) {
                    isActive = false;
                }
            }
        }
    }
}
