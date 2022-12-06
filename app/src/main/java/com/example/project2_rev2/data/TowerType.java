package com.example.project2_rev2.data;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.utils.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * an enum that includes all tower types
 * all values required to create a tower
 * implements Serializable to pass with extras
 */

public enum TowerType implements Serializable {
    DEMO_TOWER(
            "Demo Tower",
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            300,
            30,
            70,
            new Size(85, 85),
            Projectile.ProjectileType.DEMO_BULLET,
            new TowerUpgradePath(
                    new String[] {"Range", "Range", "Range"},
                    new int[] {100, 200, 350},
                    new int[] {0, 0, 0},
                    new String[] {"", "", ""}
            ),
            new TowerUpgradePath(
                    new String[] {"ATK Speed", "ATK Speed", "ATK Speed"},
                    new int[] {100, 200, 300},
                    new int[] {0, 0, 0},
                    new String[] {"", "", ""}
            )

    ),

    TURRET(
            "Turret",
            R.drawable.turret_head_1,
            R.drawable.turret_icon,
            250,
            8,
            300,
            new Size(150, 150),
            Projectile.ProjectileType.TURRET_BULLETS,
            new TowerUpgradePath(
                    new String[] {"Double DMG", "Bigger Bullets", "Double Barrel"},
                    new int[] {180, 200, 320},
                    new int[] {0, 400, 950},
                    new String[] {
                            "bullets do double the damage",
                            "bullets are now bigger, they hurt more",
                            "additional barrel to the turret, double the bullets shot"
                    }
            ),
            new TowerUpgradePath(
                    new String[] {"Range", "ATK Speed"},
                    new int[] {150, 230},
                    new int[] {200, 800},
                    new String[] {
                            "increase the turret's range",
                            "increase the turret's attack speed, shoot faster"
                    }
            )
    ),
    FIRE_SPREADER(
            "Fire Spreader",
            R.drawable.fire_spreader_base,
            R.drawable.fire_spreader_base,
            180,
            50,
            250,
            new Size(110, 110),
            null,
            new TowerUpgradePath(
                    new String[] {"Longer Burn", "Hot Flames", "Violent Fire", "Agidyne"},
                    new int[] {270, 300, 420, 500},
                    new int[] {0, 250, 1200, 2200},
                    new String[] {
                            "enemies burn for longer, dealing more damage over more time",
                            "damage received by flames increased",
                            "burn for more, in the sane time",
                            "significantly increase damage, and the flames burns more"
                    }
            ),
            new TowerUpgradePath(
                    new String[] {"Range", "Multi Burn", "Hotter", "Carmen"},
                    new int[] {300, 360, 480, 600},
                    new int[] {100, 950, 1800, 2800},
                    new String[] {
                            "increase the flames reach",
                            "burn more enemies in the fire's range",
                            "burn even more enemies in the fire's range and increase damage done by the fire",
                            "increase the fire's reach to its limit, and burn every enemy in it's path"
                    }
            )
    );

    public final String towerName;
    public final int icon;
    public final int bitmap;
    public final int range;
    public final int cooldown;
    public final int value;
    public final Size size;
    public final Projectile.ProjectileType projectileType;
    public final TowerUpgradePath towerUpgradePathOne;
    public final TowerUpgradePath towerUpgradePathTwo;

    TowerType(String towerName, int bitmap, int icon, int range, int cooldown, int value, Size size, Projectile.ProjectileType projectileType, TowerUpgradePath towerUpgradePathOne, TowerUpgradePath towerUpgradePathTwo) {
        this.towerName = towerName;
        this.bitmap = bitmap;
        this.icon = icon;
        this.range = range;
        this.cooldown = cooldown;
        this.value = value;
        this.size = size;
        this.projectileType = projectileType;
        this.towerUpgradePathOne = towerUpgradePathOne;
        this.towerUpgradePathTwo = towerUpgradePathTwo;
    }

    public static class TowerUpgradePath {

        public String[] name;
        public int[] cost;
        public int[] xpReq;
        public String[] upgradeInfo;

        public TowerUpgradePath(String[] name, int[] cost, int[] xpReq, String[] upgradeInfo) {
            this.name = name;
            this.cost = cost;
            this.xpReq = xpReq;
            this.upgradeInfo = upgradeInfo;
        }
    }
}
