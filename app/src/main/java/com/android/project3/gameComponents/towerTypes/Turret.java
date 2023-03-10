package com.android.project3.gameComponents.towerTypes;

import static com.android.project3.utils.HelperMethods.getBitmapFromPicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.android.project3.R;
import com.android.project3.data.TowerType;
import com.android.project3.data.User;
import com.android.project3.gameComponents.Projectile;
import com.android.project3.gameComponents.TowerBar;
import com.android.project3.gameComponents.abstractComponents.Tower;
import com.android.project3.gameComponents.managers.ProjectileManager;
import com.android.project3.gameComponents.managers.WaveManager;
import com.android.project3.data.GameValues;

/**
 * a class that inherits Tower
 * include override for upgrade method to give specific upgrades
 */

public class Turret extends Tower {

    private Bitmap turretBase;
    private Bitmap turretHead2;

    public Turret(double x, double y, Rect collider, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x, y, collider, TowerType.TURRET, towerBar, waveManager, projectileManager, context);
        this.turretBase = Bitmap.createScaledBitmap(
                getBitmapFromPicture(context, R.drawable.turret_base),
                (int)size.width,
                (int)size.height,
                false
        );
        this.turretHead2 = Bitmap.createScaledBitmap(
                getBitmapFromPicture(context, R.drawable.turret_head_2),
                (int)size.width,
                (int)size.height,
                false
        );
        this.isCamoDetecting = true;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(
                turretBase,
                (int)centerPosition.x-turretBase.getWidth()/2,
                (int)centerPosition.y-turretBase.getHeight()/2,
                paint
        );
        super.draw(canvas);
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
                            // double DMG
                            projectileType = Projectile.ProjectileType.TURRET_BULLETS_V2;
                            break;
                        case 1:
                            // bigger bullets
                            projectileType = Projectile.ProjectileType.TURRET_BULLETS_V3;
                            break;
                        case 2:
                            // double projectile
                            bitmap = turretHead2;
                            originalBitmap = turretHead2;
                            isDoubleShot = true;
                            break;
                        case 3:
                            // breaking all
                            projectileType = Projectile.ProjectileType.TURRET_BULLETS_V4;
                            break;
                    }
                } else {
                    // path two
                    switch (pathLevels[upgradePathIndex]) {
                        case 0:
                            // range
                            range = 350;
                            break;
                        case 1:
                            // ATK speed
                            cooldown = 4;
                            break;
                        case 2:
                            break;
                        case 3:
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
                        // double DMG
                        projectileType = Projectile.ProjectileType.TURRET_BULLETS_V2;
                        break;
                    case 1:
                        // bigger bullets
                        projectileType = Projectile.ProjectileType.TURRET_BULLETS_V3;
                        break;
                    case 2:
                        // double projectile
                        bitmap = turretHead2;
                        originalBitmap = turretHead2;
                        isDoubleShot = true;
                        break;
                    case 3:
                        // breaking all
                        projectileType = Projectile.ProjectileType.TURRET_BULLETS_V4;
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
                        range = 350;
                        break;
                    case 1:
                        // ATK speed
                        cooldown = 4;
                        break;
                    case 2:
                        cooldown = 1;
                        break;
                    case 3:
                        break;
                }
                moneySpent += towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]];
                pathLevels[upgradePathIndex]++;
                towerUpgradeUI.getUpgradeButtonPathTwo().postUpgrade();
            }
        }
    }
}
