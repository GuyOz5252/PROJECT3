package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;

public class Tank extends Tower {

    public Tank(double x, double y, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, TowerManager towerManager, Context context) {
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
        if (pathLevels[upgradePathIndex] < towerUpgradePaths[upgradePathIndex].value.length) {
            if (xp >= towerUpgradePaths[upgradePathIndex].xpReq[pathLevels[upgradePathIndex]] &&
                    GameValues.getPlayerCoins() >= towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]]) {

                if (upgradePathIndex == 0) {
                    // path one
                } else {
                    // path two
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
