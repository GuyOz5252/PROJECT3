package com.example.project2_rev2.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.EnemyUnit;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.Tower;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.WaveController;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public class DemoOne extends Scene {

    private Context context;

    // game components
    private TowerBar towerBar;
    private EnemyPath enemyPath;
    private WaveController waveController;
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

        this.waveController = new WaveController();
        this.waveController.addWave(new WaveController.Wave(new int[] {0, 0, 0, 0}, enemyPath, context));
        this.waveController.spawnEnemies();

        this.projectileManager = new ProjectileManager(context);

        this.tower = new Tower(1350, 200, R.drawable.ic_launcher_background, 200, 60, waveController, projectileManager, new Size(120, 120), context);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.background));

        enemyPath.draw(canvas);
        waveController.draw(canvas);
        tower.draw(canvas);
        projectileManager.draw(canvas);
        towerBar.draw(canvas);
    }

    @Override
    public void update() {
        waveController.update();
        projectileManager.update();
        tower.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }
}
