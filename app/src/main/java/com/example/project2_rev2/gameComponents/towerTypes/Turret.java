package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;

/**
 * a class that inherits Tower
 * include override for upgrade method to give specific upgrades
 */

public class Turret extends Tower {

    public Turret(double x, double y, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, TowerManager towerManager, Context context) {
        super(
                x,
                y,
                TowerType.TURRET,
                towerBar,
                waveManager,
                projectileManager,
                towerManager,
                context
        );
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
                            // double DMG
                            projectileType = Projectile.ProjectileType.TURRET_BULLETS_V2;
                            break;
                        case 1:
                            // bigger bullets
                            projectileType = Projectile.ProjectileType.TURRET_BULLETS_V3;
                            break;
                        case 2:
                            // double projectile
                            isDoubleShot = true;
                            break;
                        case 3:

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

                pathLevels[upgradePathIndex]++;
                upgradeCount++;
                GameValues.setPlayerCoins(GameValues.getPlayerCoins() - towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]-1]);
                return true;
            }
        }
        return false;
    }
}
