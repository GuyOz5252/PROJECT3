package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;
import android.graphics.Rect;

import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;

import java.util.ArrayList;

public class Railgun extends Tower {

    public Railgun(double x, double y, Rect collider, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x, y, collider, TowerType.RAILGUN, towerBar, waveManager, projectileManager, context);
    }

    @Override
    public void update() {
        position.x = centerPosition.x-(double)bitmap.getWidth()/2;
        position.y = centerPosition.y-(double)bitmap.getHeight()/2;

        currentTick++;

        for (int i = waveManager.getAliveList().size()-1; i >= 0; i--) {
            attack(waveManager.getAliveList().get(i));
        }
    }

    @Override
    public boolean upgrade(int upgradePathIndex) {
        return false;
    }

    @Override
    public void loadUpgrades(int upgradePathIndex, int level) {

    }
}
