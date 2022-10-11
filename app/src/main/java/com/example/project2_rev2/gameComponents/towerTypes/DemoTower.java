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
                if (xp >= towerUpgradePathOne.xpReq[pathOneLevel] && GameValues.getPlayerCoins() >= towerUpgradePathOne.cost[pathOneLevel]) {
                    range = towerUpgradePathOne.value[pathOneLevel];
                    pathOneLevel++;
                    upgradeCount++;
                    GameValues.setPlayerCoins(GameValues.getPlayerCoins() - towerUpgradePathOne.cost[pathOneLevel-1]);
                    return true;
                }
            }
        } else {
            if (pathTwoLevel < towerUpgradePathTwo.value.length) {
                if (xp >= towerUpgradePathTwo.xpReq[pathTwoLevel] && GameValues.getPlayerCoins() >= towerUpgradePathTwo.cost[pathTwoLevel]) {
                    cooldown = towerUpgradePathTwo.value[pathTwoLevel];
                    pathTwoLevel++;
                    upgradeCount++;
                    GameValues.setPlayerCoins(GameValues.getPlayerCoins() - towerUpgradePathTwo.cost[pathTwoLevel-1]);
                    return true;
                }
            }
        }
        return false;
    }
}
