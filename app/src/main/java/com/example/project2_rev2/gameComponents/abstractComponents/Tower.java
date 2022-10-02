package com.example.project2_rev2.gameComponents.abstractComponents;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;
import static com.example.project2_rev2.utils.HelperMethods.rotateBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.WaveManager;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public abstract class Tower extends BitmapObject {

    private TowerBar towerBar;
    private WaveManager waveManager;
    private ProjectileManager projectileManager;

    private int range;
    private Projectile.ProjectileType projectileType;
    private Paint rangeCirclePaint;
    private int cooldown;
    private int currentTick;

    private final Bitmap originalBitmap;

    private Rect towerRect;
    private boolean isSelected;

    public Tower(double x, double y, int resourceId, int range, int cooldown, Size size, Projectile.ProjectileType projectileType, TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x-size.width/2, y-size.height/2, resourceId, size, context);
        this.towerBar = towerBar;
        this.waveManager = waveManager;
        this.projectileManager = projectileManager;
        this.range = range;
        this.projectileType = projectileType;
        this.rangeCirclePaint = new Paint();
        this.rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.rangeCircle));
        this.cooldown = cooldown;
        this.currentTick = 0;
        this.originalBitmap = bitmap;
        this.towerRect = new Rect(
                (int)(x-size.width/2),
                (int)(y-size.height/2),
                (int)(x+size.width/2),
                (int)(y+size.height/2)
        );
        this.isSelected = false;
    }

    public Projectile.ProjectileType getProjectileType() {
        return projectileType;
    }

    public void attack(Enemy enemy) {
        if (currentTick >= cooldown / Scene.speedMultiplier) {
            if (getHypoDistance(centerPosition.x, centerPosition.y, enemy.getCenterPosition().x, enemy.getCenterPosition().y) < range) {
                float angle = projectileManager.createNewProjectile(this, enemy);
                handleTowerRotation(angle);
                currentTick = 0;
            }
        }
    }

    public void drawRange(Canvas canvas) {
        canvas.drawCircle(
                (float)centerPosition.x,
                (float)centerPosition.y,
                range,
                rangeCirclePaint
        );
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

        super.update();
        for (Enemy enemy : waveManager.getAliveList()) {
            attack(enemy);
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            if (!isSelected ) {
                if (isPressed(motionEvent)) {
                    System.out.println("select");
                    isSelected = true;
                }
            } else {
                if (!towerBar.getTowerBarRect().contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
                    System.out.println("deselect");
                    isSelected = false;
                }
            }
        }
    }

    public enum TowerTypes {
        DEMO_TOWER
    }
}
