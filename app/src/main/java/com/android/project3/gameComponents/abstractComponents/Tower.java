package com.android.project3.gameComponents.abstractComponents;

import static com.android.project3.utils.HelperMethods.rotateBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.android.project3.R;
import com.android.project3.data.TowerType;
import com.android.project3.data.User;
import com.android.project3.gameComponents.Enemy;
import com.android.project3.gameComponents.Projectile;
import com.android.project3.gameComponents.managers.ProjectileManager;
import com.android.project3.gameComponents.TowerBar;
import com.android.project3.gameComponents.TowerUpgradeUI;
import com.android.project3.gameComponents.managers.WaveManager;
import com.android.project3.gameComponents.towerTypes.DemoTower;
import com.android.project3.gameComponents.towerTypes.FireSpreader;
import com.android.project3.gameComponents.towerTypes.Tank;
import com.android.project3.gameComponents.towerTypes.Turret;
import com.android.project3.utils.GameValues;

/**
 * a class that includes all the fields of a tower
 * all methods and logic a tower needs
 */

public abstract class Tower extends BitmapObject {

    protected TowerBar towerBar;
    protected WaveManager waveManager;
    protected ProjectileManager projectileManager;
    protected TowerType towerType;
    protected Rect collider;
    protected boolean isActive;
    protected boolean isSelected;

    protected int range;
    protected Projectile.ProjectileType projectileType;
    protected Paint rangeCirclePaint;
    protected Paint rangeBorderPaint;
    protected int cooldown;
    protected int currentTick;

    protected Bitmap originalBitmap;
    protected int icon;

    protected TowerUpgradeUI towerUpgradeUI;
    protected TowerType.TowerUpgradePath[] towerUpgradePaths;
    protected int[] pathLevels;
    protected int moneySpent;

    protected boolean isDoubleShot;
    protected boolean isCamoDetecting;

    public Tower(double x, double y, Rect collider, TowerType towerType, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x-towerType.size.width/2, y-towerType.size.height/2, towerType.bitmap, towerType.size, context);
        this.towerBar = towerBar;
        this.waveManager = waveManager;
        this.projectileManager = projectileManager;
        this.towerType = towerType;
        this.collider = collider;
        this.isActive = true;
        this.projectileType = towerType.projectileType;
        this.range = towerType.range;
        this.cooldown = towerType.cooldown;
        this.rangeCirclePaint = new Paint();
        this.rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.range_circle));
        this.rangeBorderPaint = new Paint();
        this.rangeBorderPaint.setColor(ContextCompat.getColor(context, R.color.white));
        this.rangeBorderPaint.setStyle(Paint.Style.STROKE);
        this.rangeBorderPaint.setStrokeWidth(2);
        this.currentTick = cooldown;
        this.originalBitmap = bitmap;
        this.icon = towerType.icon;
        this.isSelected = false;
        this.towerUpgradePaths = new TowerType.TowerUpgradePath[] {
                towerType.towerUpgradePathOne,
                towerType.towerUpgradePathTwo
        };
        this.pathLevels = new int[] {0, 0};
        this.moneySpent = towerType.value;
        this.towerUpgradeUI = new TowerUpgradeUI(this, context);
        this.isDoubleShot = false;
        this.isCamoDetecting = false;
        GameValues.colliderArrayList.add(collider);
    }

    public abstract boolean upgrade(int upgradePathIndex);

    public abstract void loadUpgrades(int upgradePathIndex, int level);

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

    public Rect getCollider() {
        return collider;
    }

    public TowerType.TowerUpgradePath[] getTowerUpgradePaths() {
        return towerUpgradePaths;
    }

    public int[] getPathLevels() {
        return pathLevels;
    }

    public int getMoneySpent() {
        return moneySpent;
    }

    public boolean getIsDoubleShot() {
        return isDoubleShot;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public int getXP() {
        return User.getInstance().getTowerXP(towerType);
    }

    public void setXP(int xp) {
        User.getInstance().setTowerXP(towerType, xp);
        towerUpgradeUI.getUpgradeButtonPathOne().handleState();
        towerUpgradeUI.getUpgradeButtonPathTwo().handleState();
    }

    public double getHypoDistance(double towerX, double towerY, double enemyX, double enemyY) {
        return Math.hypot(Math.abs(towerX-enemyX), Math.abs(towerY-enemyY));
    }

    public void lookForEnemies() {
        waveManager.getAliveList().forEach(this::attack);
    }

    public void attack(Enemy enemy) {
        if (currentTick >= cooldown) {
            if (getHypoDistance(centerPosition.x, centerPosition.y, enemy.getCenterPosition().x, enemy.getCenterPosition().y) < range) {
                if (!enemy.getIsCamo() || isCamoDetecting) {
                    float angle = projectileManager.createNewProjectile(this, enemy);
                    handleTowerRotation(angle);
                    currentTick = 0;
                }
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
        towerUpgradeUI.draw(canvas);
    }

    public void onTowerUpgradeTouchEvent(MotionEvent motionEvent) {
        towerUpgradeUI.onTouchEvent(motionEvent);
    }

    public void handleTowerRotation(float angle) {
        bitmap = rotateBitmap(originalBitmap, angle);
    }

    public boolean isPressed(MotionEvent motionEvent) {
        return collider.contains((int)motionEvent.getX(), (int)motionEvent.getY());
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

        lookForEnemies();
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

    public static class TowerFactory {

        private Context context;
        private TowerBar towerBar;
        private WaveManager waveManager;
        private ProjectileManager projectileManager;

        public TowerFactory(TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
            this.context = context;
            this.towerBar = towerBar;
            this.waveManager = waveManager;
            this.projectileManager = projectileManager;
        }

        public Tower createTower(TowerType towerType, double x, double y) {
            Rect collider = new Rect(
                    (int)(x-towerType.size.width/2),
                    (int)(y-towerType.size.height/2),
                    (int)(x+towerType.size.width/2),
                    (int)(y+towerType.size.height/2)
            );
            switch (towerType) {
                case DEMO_TOWER:
                    return new DemoTower(x, y, collider, towerBar, waveManager, projectileManager, context);
                case TURRET:
                    collider = new Rect(
                            (int)(x-towerType.size.width/2)+35,
                            (int)(y-towerType.size.height/2)+30,
                            (int)(x+towerType.size.width/2)-25,
                            (int)(y+towerType.size.height/2)-30
                    );
                    return new Turret(x, y, collider, towerBar, waveManager, projectileManager, context);
                case FIRE_SPREADER:
                    return new FireSpreader(x, y, collider, towerBar, waveManager, projectileManager, context);
                case TANK:
                    return new Tank(x, y, collider, towerBar, waveManager, projectileManager, context);
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
