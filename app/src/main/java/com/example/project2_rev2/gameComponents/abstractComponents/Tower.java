package com.example.project2_rev2.gameComponents.abstractComponents;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;
import static com.example.project2_rev2.utils.HelperMethods.rotateBitmap;
import static com.example.project2_rev2.utils.GameValues.CONTEXT;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.TowerUpgradeManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

import java.io.Serializable;
import java.util.Arrays;

/**
 * a class that includes all the fields of a tower
 * all methods and logic a tower needs
 */

public abstract class Tower extends BitmapObject {

    private TowerBar towerBar;
    protected WaveManager waveManager;
    private ProjectileManager projectileManager;
    protected TowerType towerType;
    private Rect collider;
    protected boolean isActive;

    protected int range;
    protected int value;
    protected Projectile.ProjectileType projectileType;
    private Paint rangeCirclePaint;
    private Paint rangeBorderPaint;
    protected int cooldown;
    protected int currentTick;

    protected Bitmap originalBitmap;
    protected int icon;

    private Rect towerRect;
    private boolean isSelected;

    private TowerUpgradeManager towerUpgradeManager;
    protected TowerUpgradePath[] towerUpgradePaths;
    protected int[] pathLevels;
    protected int upgradeCount;
    protected int xp;

    protected boolean isDoubleShot;

    public Tower(double x, double y, Rect collider, TowerType towerType, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x-towerType.size.width/2, y-towerType.size.height/2, towerType.bitmap, towerType.size, context);
        this.towerBar = towerBar;
        this.waveManager = waveManager;
        this.projectileManager = projectileManager;
        this.towerType = towerType;
        this.collider = collider;
        this.isActive = true;
        this.projectileType = towerType.projectileType;
        this.value = towerType.value;
        this.range = towerType.range;
        this.cooldown = towerType.cooldown;
        this.rangeCirclePaint = new Paint();
        this.rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.rangeCircle));
        this.rangeBorderPaint = new Paint();
        this.rangeBorderPaint.setColor(ContextCompat.getColor(context, R.color.white));
        this.rangeBorderPaint.setStyle(Paint.Style.STROKE);
        this.rangeBorderPaint.setStrokeWidth(2);
        this.currentTick = cooldown;
        this.originalBitmap = bitmap;
        this.icon = towerType.icon;
        this.towerRect = new Rect(
                (int)(x-towerType.size.width/2),
                (int)(y-towerType.size.height/2),
                (int)(x+towerType.size.width/2),
                (int)(y+towerType.size.height/2)
        );
        this.isSelected = false;
        this.towerUpgradePaths = new TowerUpgradePath[] {
                towerType.towerUpgradePathOne,
                towerType.towerUpgradePathTwo
        };
        this.pathLevels = new int[] {0, 0};
        this.upgradeCount = 0;
        setXP(0);
        this.towerUpgradeManager = new TowerUpgradeManager(this, context);
        this.isDoubleShot = false;
    }

    public abstract boolean upgrade(int upgradePathIndex);

    public Projectile.ProjectileType getProjectileType() {
        return projectileType;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return towerType.towerName;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public TowerUpgradePath[] getTowerUpgradePaths() {
        return towerUpgradePaths;
    }

    public int[] getPathLevels() {
        return pathLevels;
    }

    public int getValue() {
        return value;
    }

    public int getUpgradeCount() {
        return upgradeCount;
    }

    public int getXP() {
        return xp;
    }

    public boolean getIsDoubleShot() {
        return isDoubleShot;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setXP(int xp) {
        this.xp = xp;
    }

    public void attack(Enemy enemy) {
        if (currentTick >= cooldown) {
            if (getHypoDistance(centerPosition.x, centerPosition.y, enemy.getCenterPosition().x, enemy.getCenterPosition().y) < range) {
                float angle = projectileManager.createNewProjectile(this, enemy);
                handleTowerRotation(angle);
                currentTick = 0;
            }
        }
    }

    public void sell() {
        isSelected = false;
        isActive = false;
        GameValues.colliderArrayList.remove(collider);
    }

    public void drawRange(Canvas canvas) {
        canvas.drawCircle(
                (float)centerPosition.x,
                (float)centerPosition.y,
                range,
                rangeCirclePaint
        );
        canvas.drawCircle(
                (float)centerPosition.x,
                (float)centerPosition.y,
                range,
                rangeBorderPaint
        );
    }

    public void drawTowerUpgradeUI(Canvas canvas) {
        towerUpgradeManager.draw(canvas);
    }

    public void onTowerUpgradeTouchEvent(MotionEvent motionEvent) {
        towerUpgradeManager.onTouchEvent(motionEvent);
    }

    public void handleTowerRotation(float angle) {
        bitmap = rotateBitmap(originalBitmap, angle);
    }

    public boolean isPressed(MotionEvent motionEvent) {
        return towerRect.contains((int)motionEvent.getX(), (int)motionEvent.getY());
    }

    @Override
    public void draw(Canvas canvas) {
        if (isSelected) {
            drawRange(canvas);
        }
        super.draw(canvas);
    }

    @Override
    public void update() {
        position.x = centerPosition.x-(double)bitmap.getWidth()/2;
        position.y = centerPosition.y-(double)bitmap.getHeight()/2;

        currentTick++;

        for (Enemy enemy : waveManager.getAliveList()) {
            attack(enemy);
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (!isSelected) {
                if (isPressed(motionEvent)) {
                    isSelected = true;
                }
            } else {
                if (!towerBar.getTowerBarRect().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    isSelected = false;
                }
            }
        }
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
                CONTEXT.getResources().getString(R.string.turret_name),
                R.drawable.turret_head_1,
                R.drawable.turret_icon,
                CONTEXT.getResources().getInteger(R.integer.turret_range),
                CONTEXT.getResources().getInteger(R.integer.turret_cooldown),
                CONTEXT.getResources().getInteger(R.integer.turret_value),
                new Size(
                        CONTEXT.getResources().getIntArray(R.array.turret_size)[0],
                        CONTEXT.getResources().getIntArray(R.array.turret_size)[1]
                ),
                Projectile.ProjectileType.TURRET_BULLETS,
                new TowerUpgradePath(
                        CONTEXT.getResources().getStringArray(R.array.turret_upgrade_names_path1),
                        CONTEXT.getResources().getIntArray(R.array.turret_upgrade_price_path1),
                        CONTEXT.getResources().getIntArray(R.array.turret_upgrade_xp_req_path1),
                        CONTEXT.getResources().getStringArray(R.array.turret_upgrade_info_path1)
                ),
                new TowerUpgradePath(
                        CONTEXT.getResources().getStringArray(R.array.turret_upgrade_names_path2),
                        CONTEXT.getResources().getIntArray(R.array.turret_upgrade_price_path2),
                        CONTEXT.getResources().getIntArray(R.array.turret_upgrade_xp_req_path2),
                        CONTEXT.getResources().getStringArray(R.array.turret_upgrade_info_path2)
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
                        new int[] {0, 0, 0, 0},
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
                        new int[] {0, 0, 0, 0},
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
        public int xp;

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
            this.xp = 0;
        }
    }
}
