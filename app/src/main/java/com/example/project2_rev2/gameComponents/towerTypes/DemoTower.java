package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;
import android.graphics.Rect;

import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;

public class DemoTower extends Tower {

    public DemoTower(double x, double y, Rect collider, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(
                x,
                y,
                collider,
                TowerType.DEMO_TOWER,
                towerBar,
                waveManager,
                projectileManager,
                context
        );
    }

    @Override
    public boolean upgrade(int upgradePathIndex) {
        if (pathLevels[upgradePathIndex] < towerUpgradePaths[upgradePathIndex].name.length) {
            if (xp >= towerUpgradePaths[upgradePathIndex].xpReq[pathLevels[upgradePathIndex]] &&
                    GameValues.getPlayerCoins() >= towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]]) {

                if (upgradePathIndex == 0) {
                    switch (pathLevels[upgradePathIndex]) {
                        case 0:
                            range = 350;
                            break;
                        case 1:
                            range = 400;
                            break;
                        case 2:
                            range = 500;
                            break;
                    }
                } else {
                    switch (pathLevels[upgradePathIndex]) {
                        case 0:
                            cooldown = 25;
                            break;
                        case 1:
                            cooldown = 23;
                            break;
                        case 2:
                            cooldown = 18;
                            break;
                    }
                }

                pathLevels[upgradePathIndex]++;
                upgradeCount++;
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadUpgrades(int upgradePathIndex, int level) {
        for (int i = 0; i < level; i++) {
            if (upgradePathIndex == 0) {
                switch (i) {
                    case 0:
                        range = 350;
                        break;
                    case 1:
                        range = 400;
                        break;
                    case 2:
                        range = 500;
                        break;
                }
                pathLevels[upgradePathIndex]++;
                upgradeCount++;
                towerUpgradeManager.getUpgradeButtonPathOne().postUpgrade();
            } else {
                switch (i) {
                    case 0:
                        cooldown = 25;
                        break;
                    case 1:
                        cooldown = 23;
                        break;
                    case 2:
                        cooldown = 18;
                        break;
                }
                pathLevels[upgradePathIndex]++;
                upgradeCount++;
                towerUpgradeManager.getUpgradeButtonPathTwo().postUpgrade();
            }
        }
    }
}
