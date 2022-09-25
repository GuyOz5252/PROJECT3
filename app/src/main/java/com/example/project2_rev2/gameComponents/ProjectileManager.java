package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.abstractComponents.EnemyUnit;
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
    public void createNewProjectile(Tower tower, EnemyUnit enemyUnit) {
        projectileType = tower.getProjectileType();

        float xDistance = (float) (tower.getCenterPosition().x - enemyUnit.getCenterPosition().x);
        float yDistance = (float) (tower.getCenterPosition().y - enemyUnit.getCenterPosition().y);
        float distance = Math.abs(xDistance) + Math.abs(yDistance);

        float xPercent = Math.abs(xDistance) / distance;

        float velocityX = xPercent * projectileType.speed;
        float velocityY = projectileType.speed - velocityX;

        if (tower.getCenterPosition().x > enemyUnit.getCenterPosition().x) {
            velocityX *= -1;
        }
        if (tower.getCenterPosition().y > enemyUnit.getCenterPosition().y) {
            velocityY *= -1;
        }

        projectileArrayList.add(new Projectile(tower.getCenterPosition().x, tower.getCenterPosition().y, (int)velocityX, (int)velocityY, context));
    }

    public void draw(Canvas canvas) {
        for (Projectile projectile : projectileArrayList) {
            projectile.draw(canvas);
        }
    }

    public void update() {
        for (Projectile projectile : projectileArrayList) {
            projectile.update();
        }
        for (EnemyUnit enemyUnit : waveManager.getAliveList()) {
            // TODO loop over projectiles and if contact damage enemy
        }
    }
}
