package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;

public class DemoTower extends Tower {

    public DemoTower(double x, double y, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(
                x,
                y,
                TowerType.DEMO_TOWER,
                towerBar,
                waveManager,
                projectileManager,
                context
        );
    }

    @Override
    public boolean upgrade(int upgradePathIndex) {
        if (upgradePathIndex == 0) {
            if (pathOneLevel < towerUpgradePathOne.value.length) {
                if (xp >= towerUpgradePathOne.xpReq[pathOneLevel] && GameValues.playerCoins >= towerUpgradePathOne.cost[pathOneLevel]) {
                    range = towerUpgradePathOne.value[pathOneLevel];
                    pathOneLevel++;
                    upgradeCount++;
                    return true;
                }
            }
        } else {
            if (pathOneLevel < towerUpgradePathTwo.value.length) {
                if (xp >= towerUpgradePathOne.xpReq[pathTwoLevel] && GameValues.playerCoins >= towerUpgradePathTwo.cost[pathTwoLevel]) {
                    cooldown = towerUpgradePathTwo.value[pathTwoLevel];
                    pathTwoLevel++;
                    upgradeCount++;
                    return true;
                }
            }
        }
        return false;
    }
}
