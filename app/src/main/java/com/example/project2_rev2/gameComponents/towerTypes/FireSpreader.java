package com.example.project2_rev2.gameComponents.towerTypes;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.HelperMethods;

public class FireSpreader extends Tower {

    private Bitmap[] firingBitmapArr;

    private int animationTick;
    private int animationIndex;
    private boolean areEnemiesInRange;

    private int damage;
    private int duration;
    private int interval;

    public FireSpreader(double x, double y, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, TowerManager towerManager, Context context) {
        super(x, y, TowerType.FIRE_SPREADER, towerBar, waveManager, projectileManager, towerManager, context);
        this.firingBitmapArr = new Bitmap[] {
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.start_wave_button_background_inactive),
                        range*2,
                        range*2,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.ic_launcher_background),
                        range*2,
                        range*2,
                        false
                )
        };
        this.animationTick = 0;
        this.animationIndex = 0;
        this.damage = 11;
        this.duration = 300;
        this.interval = 3;
    }

    public void animateFiring() {
        if (animationTick > 20) {
            if (animationIndex == 0) {
                animationIndex = 1;
            } else {
                animationIndex = 0;
            }
            animationTick = 0;
        } else {
            animationTick++;
        }
    }

    public void areEnemiesInRangeCheck() {
        areEnemiesInRange = false;
        for (Enemy enemy : waveManager.getAliveList()) {
            if (getHypoDistance(centerPosition.x, centerPosition.y, enemy.getCenterPosition().x, enemy.getCenterPosition().y) < range) {
                areEnemiesInRange = true;
                break;
            }
        }
    }

    @Override
    public void attack(Enemy enemy) {
        if (getHypoDistance(centerPosition.x, centerPosition.y, enemy.getCenterPosition().x, enemy.getCenterPosition().y) < range) {
            if (!enemy.getIsOnFire()) {
                enemy.setOnFire(damage, duration, interval, this);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (areEnemiesInRange) {
            canvas.drawBitmap(
                    firingBitmapArr[animationIndex],
                    (int)centerPosition.x-firingBitmapArr[animationIndex].getWidth()/2,
                    (int)centerPosition.y-firingBitmapArr[animationIndex].getHeight()/2,
                    paint
            );
        }
        super.draw(canvas);
    }

    @Override
    public void update() {
        super.update();
        areEnemiesInRangeCheck();
        if (areEnemiesInRange) {
            animateFiring();
        }
    }

    @Override
    public boolean upgrade(int upgradePathIndex) {
        return false;
    }
}
