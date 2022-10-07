package com.example.project2_rev2.scenes;

import static com.example.project2_rev2.utils.GameValues.display;
import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.ProjectileManager;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.WaveManager;
import com.example.project2_rev2.gameComponents.towerTypes.DemoTower;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Position;

public class DemoOne extends Scene {

    // game components
    private Rect backgroundRect;
    private Paint backgroundPaint;
    private Rect coverRect;
    private Paint coverPaint;
    private TowerBar towerBar;
    private EnemyPath enemyPath;
    private WaveManager waveManager;
    private ProjectileManager projectileManager;
    private Tower tower;

    public DemoOne(Context context) {

        this.backgroundRect = new Rect(
                (int)xCoordinate(0),
                (int)yCoordinate(0),
                (int)xCoordinate(gameDisplay.size.width),
                (int)yCoordinate(gameDisplay.size.height)
        );
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setColor(ContextCompat.getColor(context, R.color.background));
        this.coverRect = new Rect(
                (int)xCoordinate(gameDisplay.size.width),
                (int)yCoordinate(0),
                (int)xCoordinate(display.size.width),
                (int)yCoordinate(display.size.height)
        );
        this.coverPaint = new Paint();
        this.coverPaint.setColor(ContextCompat.getColor(context, R.color.black));

        this.enemyPath = new EnemyPath();
        this.enemyPath.add(new Position(xCoordinate(330), yCoordinate(gameDisplay.size.height/2)));
        this.enemyPath.add(new Position(xCoordinate(900), yCoordinate(gameDisplay.size.height/2)));
        this.enemyPath.add(new Position(xCoordinate(900), yCoordinate(300)));
        this.enemyPath.add(new Position(xCoordinate(1250), yCoordinate(300)));
        this.enemyPath.add(new Position(xCoordinate(1250), yCoordinate(gameDisplay.size.height/2+100)));
        this.enemyPath.add(new Position(xCoordinate(2000), yCoordinate(gameDisplay.size.height/2+100)));

        this.waveManager = new WaveManager(context);
        this.waveManager.addWave(new WaveManager.Wave(new Enemy.EnemyType[] {
                Enemy.EnemyType.DEMO_ENEMY,
                Enemy.EnemyType.DEMO_ENEMY,
                Enemy.EnemyType.DEMO_ENEMY,
                Enemy.EnemyType.DEMO_ENEMY
        },
                enemyPath,
                context
        ));
        this.waveManager.addWave(new WaveManager.Wave(new Enemy.EnemyType[] {
                Enemy.EnemyType.DEMO_ENEMY,
                Enemy.EnemyType.DEMO_ENEMY
        },
                enemyPath,
                context
        ));

        this.projectileManager = new ProjectileManager(waveManager, context);
        this.towerBar = new TowerBar(waveManager, context);

        this.tower = new DemoTower(xCoordinate(1400), yCoordinate(250), towerBar, waveManager, projectileManager, context); // TODO temp
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(backgroundRect, backgroundPaint);

        enemyPath.draw(canvas);
        waveManager.draw(canvas);
        projectileManager.draw(canvas);
        towerBar.draw(canvas);

        tower.draw(canvas); // TODO temp

        canvas.drawRect(coverRect, coverPaint);
    }

    @Override
    public void update() {
        waveManager.update();
        projectileManager.update();

        tower.update(); // TODO temp
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        towerBar.onTouchEvent(motionEvent);
        tower.onTouchEvent(motionEvent);
    }
}
