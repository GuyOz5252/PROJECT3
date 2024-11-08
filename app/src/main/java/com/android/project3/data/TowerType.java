package com.android.project3.data;

import com.android.project3.R;
import com.android.project3.gameComponents.Projectile;
import com.android.project3.utils.Size;

import java.io.Serializable;

/**
 * an enum that includes all tower types
 * all values required to create a tower
 * implements Serializable to pass with extras
 */

public enum TowerType implements Serializable {
    FOOT_SOLDIER(
            "Foot Soldier",
            R.drawable.foot_soldier_base,
            R.drawable.foot_soldier_base,
            300,
            30,
            70,
            new Size(130, 130),
            Projectile.ProjectileType.GUN_BULLET,
            new TowerUpgradePath(
                    new String[] {""},
                    new int[] {0},
                    new int[] {0},
                    new String[] {""}
            ),
            new TowerUpgradePath(
                    new String[] {""},
                    new int[] {0},
                    new int[] {0},
                    new String[] {""}
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
                    new String[] {"Double DMG", "Bigger Bullets", "Double Barrel", "Breaking All"},
                    new int[] {180, 200, 320, 700},
                    new int[] {0, 4000, 9500, 17000},
                    new String[] {
                            "bullets do double the damage",
                            "bullets are now bigger, they hurt more",
                            "add an additional barrel to the turret, doubling the bullets shot",
                            "the turret's bullets are now armor penetrating"
                    }
            ),
            new TowerUpgradePath(
                    new String[] {"Range", "ATK Speed", "Rapid Fire"},
                    new int[] {150, 230, 600},
                    new int[] {2000, 8000, 12000},
                    new String[] {
                            "increase the turret's range",
                            "increase the turret's attack speed, shoot faster",
                            "shoot significantly faster"
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
                    new int[] {0, 2500, 12000, 22000},
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
                    new int[] {1000, 9500, 18000, 28000},
                    new String[] {
                            "increase the flame's reach",
                            "burn more enemies in the fire's range",
                            "burn even more enemies in the fire's range and increase damage done by the fire",
                            "increase the fire's reach to its limit, and burn every enemy in it's path"
                    }
            )
    ),
    TANK(
            "Tank",
            R.drawable.common_google_signin_btn_icon_dark_normal_background,
            R.drawable.common_google_signin_btn_icon_dark_normal_background,
            350,
            65,
            250,
            new Size(120, 120),
            Projectile.ProjectileType.TANK_PROJECTILE,
            new TowerUpgradePath(
                    new String[] {""},
                    new int[] {0},
                    new int[] {0},
                    new String[] {""}
            ),
            new TowerUpgradePath(
                    new String[] {""},
                    new int[] {0},
                    new int[] {0},
                    new String[] {""}
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
