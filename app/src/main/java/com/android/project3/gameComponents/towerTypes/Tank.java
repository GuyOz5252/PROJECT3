package com.android.project3.gameComponents.towerTypes;

import android.content.Context;
import android.graphics.Rect;

import com.android.project3.data.TowerType;
import com.android.project3.data.User;
import com.android.project3.gameComponents.TowerBar;
import com.android.project3.gameComponents.abstractComponents.Tower;
import com.android.project3.gameComponents.managers.ProjectileManager;
import com.android.project3.gameComponents.managers.WaveManager;
import com.android.project3.data.GameValues;

public class Tank extends Tower {

    public Tank(double x, double y, Rect collider, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x, y, collider, TowerType.TANK, towerBar, waveManager, projectileManager, context);
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
                            // TODO add upgrade
                            break;
                        case 1:
                            // TODO add upgrade
                            break;
                        case 2:
                            // TODO add upgrade
                            break;
                        case 3:
                            // TODO add upgrade
                            break;
                    }
                } else {
                    // path two
                    switch (pathLevels[upgradePathIndex]) {
                        case 0:
                            // TODO add upgrade
                            break;
                        case 1:
                            // TODO add upgrade
                            break;
                        case 2:
                            // TODO add upgrade
                            break;
                        case 3:
                            // TODO add upgrade
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
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:

                        break;
                }
                moneySpent += towerUpgradePaths[upgradePathIndex].cost[pathLevels[upgradePathIndex]];
                pathLevels[upgradePathIndex]++;
                towerUpgradeUI.getUpgradeButtonPathOne().postUpgrade();
            } else {
                // path two
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
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
