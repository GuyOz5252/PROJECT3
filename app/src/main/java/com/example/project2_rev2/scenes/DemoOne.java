package com.example.project2_rev2.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.WaveCounter;
import com.example.project2_rev2.gameComponents.abstractComponents.Enemy;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.WaveManager;
import com.example.project2_rev2.gameComponents.towerTypes.DemoTower;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.Position;

public class DemoOne extends Scene {

    private Context context;

    // game components
    private TowerBar towerBar;
    private EnemyPath enemyPath;
    private WaveManager waveManager;
    private ProjectileManager projectileManager;
    private WaveCounter waveCounter;
    private Tower tower;

    public DemoOne(Display display, Context context) {
        this.context = context;

        this.speedMultiplier = 1;

        this.enemyPath = new EnemyPath();
        this.enemyPath.add(new Position(330, display.size.height/2));
        this.enemyPath.add(new Position(900, display.size.height/2));
        this.enemyPath.add(new Position(900, 300));
        this.enemyPath.add(new Position(1250, 300));
        this.enemyPath.add(new Position(1250, display.size.height/2+100));
        this.enemyPath.add(new Position(2000, display.size.height/2+100));

        this.waveManager = new WaveManager(waveCounter);
        this.waveManager.addWave(new WaveManager.Wave(new Enemy.EnemyTypes[] {
                Enemy.EnemyTypes.DEMO_ENEMY,
                Enemy.EnemyTypes.DEMO_ENEMY,
                Enemy.EnemyTypes.DEMO_ENEMY,
                Enemy.EnemyTypes.DEMO_ENEMY
        },
                enemyPath,
                context
        ));
        this.waveManager.addWave(new WaveManager.Wave(new Enemy.EnemyTypes[] {
                Enemy.EnemyTypes.DEMO_ENEMY,
                Enemy.EnemyTypes.DEMO_ENEMY
        },
                enemyPath,
                context
        ));

        this.waveCounter = new WaveCounter(waveManager, context);
        this.waveManager.setWaveCounter(waveCounter);
        this.projectileManager = new ProjectileManager(waveManager, context);
        this.tower = new DemoTower(1400, 250, waveManager, projectileManager, context);

        this.towerBar = new TowerBar(this, display, waveManager, context);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.background));

        enemyPath.draw(canvas);
        waveCounter.draw(canvas);
        waveManager.draw(canvas);
        tower.draw(canvas);
        projectileManager.draw(canvas);
        towerBar.draw(canvas);

        Paint paint = new Paint(); // TODO debug
        paint.setColor(Color.RED); // TODO debug
        canvas.drawCircle(1400+60, 250+60, 10, paint); // TODO debug
    }

    @Override
    public void update() {
        waveCounter.update();
        waveManager.update();
        projectileManager.update();
        tower.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        towerBar.onTouchEvent(motionEvent);
        tower.onTouchEvent(motionEvent);
    }
}
