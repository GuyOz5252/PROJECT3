package com.example.project2_rev2.gameComponents.towerTypes;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
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

    public FireSpreader(double x, double y, Rect collider, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x, y, collider, TowerType.FIRE_SPREADER, towerBar, waveManager, projectileManager, context);
        initFiringBitmapArr(range);
        this.animationTick = 0;
        this.animationIndex = 0;
        this.damage = 8;
        this.duration = 300;
        this.interval = 3;
    }

    public void initFiringBitmapArr(int range) {
        firingBitmapArr = new Bitmap[] {
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range-range/2,
                        range-range/2,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range-50,
                        range-50,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range-35,
                        range-35,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range-25,
                        range-25,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range,
                        range,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range+range/2,
                        range+range/2,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2-25,
                        range*2-25,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2-15,
                        range*2-15,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2-7,
                        range*2-7,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2,
                        range*2,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2+7,
                        range*2+7,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2+15,
                        range*2+15,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2+7,
                        range*2+7,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2,
                        range*2,
                        false
                ),
                Bitmap.createScaledBitmap(
                        HelperMethods.getBitmapFromPicture(context, R.drawable.fire_spreader_fire),
                        range*2-7,
                        range*2-7,
                        false
                ),
        };
    }

    public void animateFiring() {
        if (animationTick > 0) {
            if (animationIndex != 13) {
                animationIndex++;
            } else {
                animationIndex = 7;
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
        } else {
            animationIndex = 0;
        }
    }

    @Override
    public boolean upgrade(int upgradePathIndex) {
        // checks that the user has enough money and xp to upgrade
        if (pathLevels[upgradePathIndex] < towerUpgradePaths[upgradePathIndex].name.length) {
            if (User.getInstance().getTowerXP(towerType) >= towerUpgradePaths[upgradePathIndex].xpReq[pathLevels[upgradePathIndex]] &&
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
                            damage = 22;
                            interval = 10;
                            break;
                    }
                } else {
                    // path two
                    switch (pathLevels[upgradePathIndex]) {
                        case 0:
                            // range
                            initFiringBitmapArr(220);
                            range = 220;
                            break;
                        case 1:
                            // multi burn
                            cooldown = 38;
                            break;
                        case 2:
                            // hotter
                            cooldown = 28;
                            damage = 15;
                            break;
                        case 3:
                            // carmen
                            initFiringBitmapArr(250);
                            range = 250;
                            cooldown = 0;
                            break;
                    }
                }

                moneySpent += towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]];
                pathLevels[upgradePathIndex]++;
                GameValues.setPlayerCoins(GameValues.getPlayerCoins() - towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]-1]);
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadUpgrades(int upgradePathIndex, int level) {
        for (int i = 0; i < level; i++) {
            if (upgradePathIndex == 0) {
                // path one
                switch (i) {
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
                        damage = 22;
                        interval = 10;
                        break;
                }
                moneySpent += towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]];
                pathLevels[upgradePathIndex]++;
                towerUpgradeUI.getUpgradeButtonPathOne().postUpgrade();
            } else {
                // path two
                switch (i) {
                    case 0:
                        // range
                        initFiringBitmapArr(220);
                        range = 220;
                        break;
                    case 1:
                        // multi burn
                        cooldown = 38;
                        break;
                    case 2:
                        // hotter
                        cooldown = 28;
                        damage = 15;
                        break;
                    case 3:
                        // carmen
                        initFiringBitmapArr(250);
                        range = 250;
                        cooldown = 0;
                        break;
                }
                moneySpent += towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]];
                pathLevels[upgradePathIndex]++;
                towerUpgradeUI.getUpgradeButtonPathTwo().postUpgrade();
            }
        }
    }
}
