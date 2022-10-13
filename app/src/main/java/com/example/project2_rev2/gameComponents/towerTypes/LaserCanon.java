package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

public class LaserCanon extends Tower {

    public LaserCanon(double x, double y, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, TowerManager towerManager, Context context) {
        super(
                x,
                y,
                TowerType.LASER_CANON,
                towerBar,
                waveManager,
                projectileManager,
                towerManager,
                context
        );
    }

    @Override
    public boolean upgrade(int upgradeIndex) {
        return false;
    }
}
