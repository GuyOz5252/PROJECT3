package com.example.project2_rev2.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.abstractComponents.EnemyUnit;
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
    private Tower tower;

    public DemoOne(Display display, Context context) {
        this.context = context;

        this.towerBar = new TowerBar(display, context);

        this.enemyPath = new EnemyPath();
        this.enemyPath.add(new Position(330, display.size.height/2));
        this.enemyPath.add(new Position(900, display.size.height/2));
        this.enemyPath.add(new Position(900, 300));
        this.enemyPath.add(new Position(1250, 300));
        this.enemyPath.add(new Position(1250, display.size.height/2+100));
        this.enemyPath.add(new Position(2000, display.size.height/2+100));

        this.waveManager = new WaveManager();
        this.waveManager.addWave(new WaveManager.Wave(new EnemyUnit.EnemyTypes[] {
                EnemyUnit.EnemyTypes.DEMO_ENEMY,
                EnemyUnit.EnemyTypes.DEMO_ENEMY,
                EnemyUnit.EnemyTypes.DEMO_ENEMY,
                EnemyUnit.EnemyTypes.DEMO_ENEMY
        },
                enemyPath,
                context
        ));
        this.waveManager.spawnEnemies();

        this.projectileManager = new ProjectileManager(waveManager, context);

        this.tower = new DemoTower(1350, 200, waveManager, projectileManager, context);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.background));

        enemyPath.draw(canvas);
        waveManager.draw(canvas);
        tower.draw(canvas);
        projectileManager.draw(canvas);
        towerBar.draw(canvas);
    }

    @Override
    public void update() {
        waveManager.update();
        projectileManager.update();
        tower.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }
}
