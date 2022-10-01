package com.example.project2_rev2.gameComponents.abstractComponents;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;
import static com.example.project2_rev2.utils.HelperMethods.rotateBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.Projectile;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.WaveManager;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public abstract class Tower extends BitmapObject {

    private WaveManager waveManager;
    private ProjectileManager projectileManager;

    private int range;
    private Projectile.ProjectileType projectileType;
    private Paint rangeCirclePaint;
    private int cooldown;
    private int currentTick;

    private final Bitmap originalBitmap;
    private final Position centerPosition;

    public Tower(double x, double y, int resourceId, int range, int cooldown, Size size, Projectile.ProjectileType projectileType, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        super(x+size.width/2, y+size.height/2, resourceId, size, context);
        this.waveManager = waveManager;
        this.projectileManager = projectileManager;
        this.range = range;
        this.projectileType = projectileType;
        this.rangeCirclePaint = new Paint();
        this.rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.rangeCircle));
        this.cooldown = cooldown;
        this.currentTick = 0;
        this.originalBitmap = bitmap;
        this.centerPosition = new Position(x, y);
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

    public void handleTowerRotation(float angle) {
        bitmap = rotateBitmap(originalBitmap, angle);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(
                (float)centerPosition.x,
                (float)centerPosition.y,
                range,
                rangeCirclePaint
        );
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

    public enum TowerTypes {
        DEMO_TOWER
    }
}
