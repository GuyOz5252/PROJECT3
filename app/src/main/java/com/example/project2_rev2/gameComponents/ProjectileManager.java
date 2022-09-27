package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.abstractComponents.Enemy;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;

public class ProjectileManager {

    private Context context;
    private WaveManager waveManager;

    private ArrayList<Projectile> projectileArrayList;
    private Projectile.ProjectileType projectileType;

    public ProjectileManager(WaveManager waveManager, Context context) {
        this.projectileArrayList = new ArrayList<>();
        this.waveManager = waveManager;
        this.context = context;
    }

    // https://www.youtube.com/watch?v=_HhIvNIlEqM
    public void createNewProjectile(Tower tower, Enemy enemy) {
        projectileType = tower.getProjectileType();

        float xDistance = (float) (tower.getCenterPosition().x - enemy.getCenterPosition().x);
        float yDistance = (float) (tower.getCenterPosition().y - enemy.getCenterPosition().y);
        float distance = Math.abs(xDistance) + Math.abs(yDistance);

        float xPercent = Math.abs(xDistance) / distance;

        float velocityX = xPercent * projectileType.speed;
        float velocityY = projectileType.speed - velocityX;

        if (tower.getCenterPosition().x > enemy.getCenterPosition().x) {
            velocityX *= -1;
        }
        if (tower.getCenterPosition().y > enemy.getCenterPosition().y) {
            velocityY *= -1;
        }

        Projectile projectile = new Projectile(tower.getCenterPosition().x, tower.getCenterPosition().y, (int)velocityX, (int)velocityY, enemy, projectileType, context);
        projectileArrayList.add(projectile);
    }

    public void draw(Canvas canvas) {
        for (Projectile projectile : projectileArrayList) {
            if (projectile.getIsActive()) {
                projectile.draw(canvas);
            }
        }
    }

    public void update() {
        for (Projectile projectile : projectileArrayList) {
            if (projectile.getIsActive()) {
                projectile.update();
                projectile.hitEnemy(waveManager.getAliveList());
            }
        }
    }
}