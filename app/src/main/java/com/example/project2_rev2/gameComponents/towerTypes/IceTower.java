package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;

public class IceTower extends Tower {

    public IceTower(double x, double y, TowerType towerType, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, TowerManager towerManager, Context context) {
        super(x, y, towerType, towerBar, waveManager, projectileManager, towerManager, context);
    }

    @Override
    public boolean upgrade(int upgradePathIndex) {
        return false;
    }
}