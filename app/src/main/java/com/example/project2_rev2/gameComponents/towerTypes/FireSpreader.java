package com.example.project2_rev2.gameComponents.towerTypes;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;
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
                        HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                        range*2-25,
                        range*2-25,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                        range*2-50,
                        range*2-50,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                        range*2,
                        range*2,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                        range*2+15,
                        range*2+15,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                        range*2+30,
                        range*2+30,
                        false
                ),
        };
        this.animationTick = 0;
        this.animationIndex = 0;
        this.damage = 8;
        this.duration = 300;
        this.interval = 3;
    }

    public void animateFiring() {
        if (animationTick > 7) {
            if (animationIndex == 0) {
                animationIndex = 1;
            } else if (animationIndex == 1) {
                animationIndex = 2;
            } else if (animationIndex == 2) {
                animationIndex = 3;
            } else if (animationIndex == 3) {
                animationIndex = 4;
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
        if (currentTick >= cooldown) {
            if (getHypoDistance(centerPosition.x, centerPosition.y, enemy.getCenterPosition().x, enemy.getCenterPosition().y) < range) {
                if (!enemy.getIsOnFire()) {
                    enemy.setOnFire(damage, duration, interval, this);
                    currentTick = 0;
                }
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
        // checks that the user has enough money and xp to upgrade
        if (pathLevels[upgradePathIndex] < towerUpgradePaths[upgradePathIndex].name.length) {
            if (xp >= towerUpgradePaths[upgradePathIndex].xpReq[pathLevels[upgradePathIndex]] &&
                    GameValues.getPlayerCoins() >= towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]]) {
                // check which path is being upgraded

                if (upgradePathIndex == 0) {
                    // path one
                    switch (pathLevels[upgradePathIndex]) {
                        case 0:
                            // longer burn
                            duration = 500;
                            interval = 5;
                            break;
                        case 1:
                            // hot flames
                            damage = 15;
                            break;
                        case 2:
                            // violent fire
                            interval = 7;
                            break;
                        case 3:
                            // agidyne
                            damage = 20;
                            interval = 10;
                            break;
                    }
                } else {
                    // path two
                    switch (pathLevels[upgradePathIndex]) {
                        case 0:
                            // range
                            range = 220;
                            firingBitmapArr = new Bitmap[] {
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2-25,
                                            range*2-25,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2-50,
                                            range*2-50,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2,
                                            range*2,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2+15,
                                            range*2+15,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2+30,
                                            range*2+30,
                                            false
                                    ),
                            };
                            break;
                        case 1:
                            // multi burn
                            cooldown = 38;
                            break;
                        case 2:
                            //

                            break;
                        case 3:
                            // carmen
                            range = 250;
                            firingBitmapArr = new Bitmap[] {
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2-25,
                                            range*2-25,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2-50,
                                            range*2-50,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2,
                                            range*2,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2+15,
                                            range*2+15,
                                            false
                                    ),
                                    Bitmap.createScaledBitmap(
                                            HelperMethods.getBitmapFromVectorDrawable(context, R.drawable.fire_spreader_fire),
                                            range*2+30,
                                            range*2+30,
                                            false
                                    ),
                            };
                            cooldown = 0;
                            break;
                    }
                }

                pathLevels[upgradePathIndex]++;
                upgradeCount++;
                GameValues.setPlayerCoins(GameValues.getPlayerCoins() - towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]-1]);
                return true;
            }
        }
        return false;
    }
}
