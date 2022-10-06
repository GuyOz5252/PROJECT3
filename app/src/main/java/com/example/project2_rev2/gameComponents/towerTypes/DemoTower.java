package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.WaveManager;
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
    public int upgrade(int upgradeIndex) {
        if (upgradeCount < 5 && towerUpgrades[upgradeIndex].indexLevel < 3) { //GameValue.money >= towerUpgrades[0].cost[towerUpgrades[0].indexLevel] &&
            switch (towerUpgrades[upgradeIndex].towerUpgradeType) {
                case RANGE:
                    range = towerUpgrades[upgradeIndex].value[towerUpgrades[upgradeIndex].indexLevel];
                    break;
                case COOLDOWN:
                    cooldown = towerUpgrades[upgradeIndex].value[towerUpgrades[upgradeIndex].indexLevel];
                    break;
            }
            towerUpgrades[upgradeIndex].indexLevel++;
            upgradeCount++;
        }
        return towerUpgrades[upgradeIndex].indexLevel;
    }
}
