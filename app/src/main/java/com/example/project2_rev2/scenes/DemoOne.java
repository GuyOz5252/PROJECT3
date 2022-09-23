package com.example.project2_rev2.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.EnemyUnit;
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

    public DemoOne(Display display, Context context) {
        this.context = context;

        towerBar = new TowerBar(display, context);
        enemyPath = new EnemyPath();
        enemyPath.add(new Position(330, display.size.height/2));
        enemyPath.add(new Position(900, display.size.height/2));
        enemyPath.add(new Position(900, 300));
        enemyPath.add(new Position(1250, 300));
        enemyPath.add(new Position(1250, display.size.height/2+100));
        enemyPath.add(new Position(2000, display.size.height/2+100));

        waveController = new WaveController();
        waveController.addWave(new WaveController.Wave(new EnemyUnit[] {
                new EnemyUnit(R.drawable.ic_launcher_background, 6, new Size(100, 100), enemyPath, context),
                new EnemyUnit(R.drawable.ic_launcher_background, 6, new Size(100, 100), enemyPath, context),
                new EnemyUnit(R.drawable.ic_launcher_background, 6, new Size(100, 100), enemyPath, context),
                new EnemyUnit(R.drawable.ic_launcher_background, 6, new Size(100, 100), enemyPath, context)
        }));
        waveController.spawnEnemies();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.background));

        enemyPath.draw(canvas);
        waveController.draw(canvas);
        towerBar.draw(canvas);
    }

    @Override
    public void update() {
        waveController.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }
}
