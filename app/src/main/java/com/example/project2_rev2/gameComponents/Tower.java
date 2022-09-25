package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.utils.Size;

public class Tower extends BitmapObject {

    private WaveController waveController;
    private ProjectileManager projectileManager;

    private int range;
    private Paint rangeCirclePaint;
    private int cooldown;
    private int currentTick;

    public Tower(double x, double y, int resourceId, int range, int cooldown, WaveController waveController, ProjectileManager projectileManager, Size size, Context context) {
        super(x, y, resourceId, size, context);
        this.waveController = waveController;
        this.projectileManager = projectileManager;
        this.range = range;
        this.rangeCirclePaint = new Paint();
        this.rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.rangeCircle));
        this.cooldown = cooldown;
        this.currentTick = 0;
    }

    public void attack(EnemyUnit enemyUnit) {
        if (currentTick >= cooldown) {
            if (getHypoDistance(centerPosition.x, centerPosition.y, enemyUnit.getCenterPosition().x, enemyUnit.getCenterPosition().y) < range) {
                System.out.println("shoot");
                projectileManager.createNewProjectile(this, enemyUnit);
                currentTick = 0;
            }
        }
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
        currentTick++;

        super.update();
        for (EnemyUnit enemyUnit : waveController.getAliveList()) {
            attack(enemyUnit);
        }
    }
}
