package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;

public class DemoTower extends Tower {

    public DemoTower(double x, double y, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, TowerManager towerManager, Context context) {
        super(
                x,
                y,
                TowerType.DEMO_TOWER,
                towerBar,
                waveManager,
                projectileManager,
                towerManager,
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
                GameValues.setPlayerCoins(GameValues.getPlayerCoins() - towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]-1]);
                return true;
            }
        }
        return false;
    }
}
