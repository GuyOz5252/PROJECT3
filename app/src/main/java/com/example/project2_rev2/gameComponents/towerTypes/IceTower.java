package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;
import android.graphics.Rect;

import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;

public class IceTower extends Tower {

    public IceTower(double x, double y, Rect collider, TowerType towerType, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x, y, collider, towerType, towerBar, waveManager, projectileManager, context);
    }

    @Override
    public boolean upgrade(int upgradePathIndex) {
        return false;
    }
}
