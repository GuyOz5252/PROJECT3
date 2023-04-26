package com.android.project3.gameComponents.managers;

import android.content.Context;
import android.graphics.Canvas;

import com.android.project3.gameComponents.Enemy;
import com.android.project3.gameComponents.Projectile;
import com.android.project3.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;

public class ProjectileManager {

    private Context context;
    private WaveManager waveManager;

    private Projectile.ProjectileFactory projectileFactory;

    private ArrayList<Projectile> projectileArrayList;
    private Projectile.ProjectileType projectileType;

    public ProjectileManager(WaveManager waveManager, Context context) {
        this.context = context;
        this.projectileArrayList = new ArrayList<>();
        this.waveManager = waveManager;
        this.projectileFactory = new Projectile.ProjectileFactory();
    }

    public float createNewProjectile(Tower tower, Enemy enemy) {
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

        float rotate;

        float arcValue = (float) Math.atan(yDistance / xDistance);
        rotate = (float) Math.toDegrees(arcValue);

        if (xDistance < 0)
            rotate += 180;

        if (tower.getIsDoubleShot()) {
            if (yDistance < 0) {
                projectileArrayList.add(projectileFactory.createProjectile(tower.getCenterPosition().x-10, tower.getCenterPosition().y-10, (int)velocityX, (int)velocityY, projectileType, tower, context));
                projectileArrayList.add(projectileFactory.createProjectile(tower.getCenterPosition().x+10, tower.getCenterPosition().y+10, (int)velocityX, (int)velocityY, projectileType, tower, context));
            } else {
                projectileArrayList.add(projectileFactory.createProjectile(tower.getCenterPosition().x+10, tower.getCenterPosition().y-10, (int)velocityX, (int)velocityY, projectileType, tower, context));
                projectileArrayList.add(projectileFactory.createProjectile(tower.getCenterPosition().x-10, tower.getCenterPosition().y+10, (int)velocityX, (int)velocityY, projectileType, tower, context));
            }
        } else {
            projectileArrayList.add(projectileFactory.createProjectile(tower.getCenterPosition().x, tower.getCenterPosition().y, (int)velocityX, (int)velocityY, projectileType, tower, context));
        }

        return rotate;
    }

    public void draw(Canvas canvas) {
        projectileArrayList.forEach(projectile -> {
            if (projectile.getIsActive()) {
                projectile.draw(canvas);
            }
        });
    }

    public void update() {
        projectileArrayList.forEach(projectile -> {
            if (projectile.getIsActive()) {
                projectile.update();
                projectile.hitEnemy(waveManager.getAliveList());
            }
        });
        projectileArrayList.removeIf(projectile -> !projectile.getIsActive());
    }
}
