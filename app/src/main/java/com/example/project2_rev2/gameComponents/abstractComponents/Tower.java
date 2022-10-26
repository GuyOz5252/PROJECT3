package com.example.project2_rev2.gameComponents.abstractComponents;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;
import static com.example.project2_rev2.utils.HelperMethods.rotateBitmap;

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
import com.example.project2_rev2.utils.Size;

public abstract class Tower extends BitmapObject {

    private TowerBar towerBar;
    private WaveManager waveManager;
    private ProjectileManager projectileManager;
    private TowerType towerType;

    protected int range;
    protected int value;
    protected Projectile.ProjectileType projectileType;
    private Paint rangeCirclePaint;
    private Paint rangeBorderPaint;
    protected int cooldown;
    private int currentTick;

    private final Bitmap originalBitmap;

    private Rect towerRect;
    private boolean isSelected;

    private TowerUpgradeManager towerUpgradeManager;
    protected TowerUpgradePath[] towerUpgradePaths;
    protected int[] pathLevels;
    protected int upgradeCount;
    protected int xp;

    public Tower(double x, double y, TowerType towerType, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, TowerManager towerManager, Context context) {
        super(x-towerType.size.width/2, y-towerType.size.height/2, towerType.bitmap, towerType.size, context);
        this.towerBar = towerBar;
        this.waveManager = waveManager;
        this.projectileManager = projectileManager;
        this.towerType = towerType;
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
        this.currentTick = 0;
        this.originalBitmap = bitmap;
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
        this.towerUpgradeManager = new TowerUpgradeManager(this, towerManager, context);
    }

    public abstract boolean upgrade(int upgradeIndex);

    public Projectile.ProjectileType getProjectileType() {
        return projectileType;
    }

    public Bitmap getOriginalBitmap() {
        return originalBitmap;
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

    public void deselect() {
        isSelected = false;
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

        public String name;
        public int[] value;
        public int[] cost;
        public int[] xpReq;

        public TowerUpgradePath(String name, int[] value, int[] cost, int[] xpReq) {
            this.name = name;
            this.value = value;
            this.cost = cost;
            this.xpReq = xpReq;
        }
    }

    public enum TowerType {
        DEMO_TOWER(
                "Demo Tower",
                R.drawable.ic_launcher_background,
                300,
                30,
                70,
                new Size(85, 85),
                Projectile.ProjectileType.DEMO_BULLET,
                new TowerUpgradePath(
                        "Range",
                        new int[] {350, 400, 500},
                        new int[] {100, 200, 350},
                        new int[] {0, 0, 0}
                ),
                new TowerUpgradePath(
                        "ATK Speed",
                        new int[] {25, 23, 18},
                        new int[] {100, 200, 300},
                        new int[] {0, 0, 0}
                )

        ),
        LASER_CANON(
                "Laser Canon",
                R.drawable.ic_launcher_background,
                200,
                10,
                200,
                new Size(85, 85),
                Projectile.ProjectileType.LASER_BEAM,
                new TowerUpgradePath(
                        "None",
                        new int[] {0},
                        new int[] {0},
                        new int[] {0}
                ),
                new TowerUpgradePath(
                        "None",
                        new int[] {0},
                        new int[] {0},
                        new int[] {0}
                )
        );

        public String towerName;
        public int bitmap;
        public int range;
        public int cooldown;
        public int value;
        public Size size;
        public Projectile.ProjectileType projectileType;
        public TowerUpgradePath towerUpgradePathOne;
        public TowerUpgradePath towerUpgradePathTwo;

        TowerType(String towerName, int bitmap, int range, int cooldown, int value, Size size, Projectile.ProjectileType projectileType, TowerUpgradePath towerUpgradePathOne, TowerUpgradePath towerUpgradePathTwo) {
            this.towerName = towerName;
            this.bitmap = bitmap;
            this.range = range;
            this.cooldown = cooldown;
            this.value = value;
            this.size = size;
            this.projectileType = projectileType;
            this.towerUpgradePathOne = towerUpgradePathOne;
            this.towerUpgradePathTwo = towerUpgradePathTwo;
        }
    }
}
