package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.WaveManager;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

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
            if (towerUpgradePathOne.pathLevel < towerUpgradePathOne.value.length && xp >= towerUpgradePathOne.xpReq[towerUpgradePathOne.pathLevel]) {
                range = towerUpgradePathOne.value[towerUpgradePathOne.pathLevel];
                towerUpgradePathOne.pathLevel++;
                upgradeCount++;
                return true;
            }
        } else {
            if (towerUpgradePathTwo.pathLevel < towerUpgradePathTwo.value.length && xp >= towerUpgradePathOne.xpReq[towerUpgradePathOne.pathLevel]) {
                cooldown = towerUpgradePathTwo.value[towerUpgradePathTwo.pathLevel];
                towerUpgradePathTwo.pathLevel++;
                upgradeCount++;
                return true;
            }
        }
        return false;
    }
}
