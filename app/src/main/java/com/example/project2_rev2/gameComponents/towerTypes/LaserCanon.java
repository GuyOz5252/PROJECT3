package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.WaveManager;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.utils.Size;

public class LaserCanon extends Tower {

    public LaserCanon(double x, double y, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(
                x,
                y,
                TowerType.LASER_CANON,
                towerBar,
                waveManager,
                projectileManager,
                context
        );
    }

    @Override
    public void upgradeOne() {

    }

    @Override
    public void upgradeTwo() {

    }

    @Override
    public void upgradeThree() {

    }
}
