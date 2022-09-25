package com.example.project2_rev2.gameComponents.towerTypes;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.WaveManager;
import com.example.project2_rev2.utils.Size;

public class DemoTower extends Tower {

    public DemoTower(double x, double y, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(
                x,
                y,
                R.drawable.ic_launcher_background,
                200,
                60,
                new Size(120, 120),
                Projectile.ProjectileType.DEMO_BULLET,
                waveManager,
                projectileManager,
                context);
    }
}
