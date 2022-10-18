package com.example.project2_rev2.scenes;

import static com.example.project2_rev2.utils.GameValues.display;
import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;
import static com.example.project2_rev2.utils.GameValues.yOffset;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.Action;
import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.CoinCounter;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.HealthCounter;
import com.example.project2_rev2.gameComponents.button.PauseButton;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Position;

public class DemoOne extends Scene {

    // scene components
    private Rect backgroundRect;
    private Paint backgroundPaint;
    private Rect coverRect;
    private Paint coverPaint;

    // game components
    private TowerBar towerBar;
    private EnemyPath enemyPath;
    private WaveManager waveManager;
    private ProjectileManager projectileManager;
    private TowerManager towerManager;
    private PauseButton pauseButton;
    private CoinCounter coinCounter;
    private HealthCounter healthCounter;

    public DemoOne(Action[] actionsArray, Context context) {
        GameValues.healthChangeListenerArrayList.add(this);

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
                (int)display.size.width,
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

        this.waveManager = new WaveManager(actionsArray[1], context);
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
        this.towerManager = new TowerManager(towerBar, waveManager, projectileManager, context);
        this.towerBar.setTowerManager(towerManager);
        this.pauseButton = new PauseButton(actionsArray[0], context);
        this.coinCounter = new CoinCounter(context);
        this.healthCounter = new HealthCounter(context);
    }

    @Override
    public void onHealthChange() {
        if (GameValues.getPlayerHealth() <= 0) {
            GameValues.isPaused = true;
            // TODO finish game with dialog
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(backgroundRect, backgroundPaint);

        enemyPath.draw(canvas);
        waveManager.draw(canvas);
        projectileManager.draw(canvas);
        towerManager.draw(canvas);
        towerBar.draw(canvas);
        towerManager.drawTowerUpgradeUI(canvas);
        pauseButton.draw(canvas);
        coinCounter.draw(canvas);
        healthCounter.draw(canvas);

        canvas.drawRect(coverRect, coverPaint);
    }

    @Override
    public void update() {
        waveManager.update();
        projectileManager.update();
        towerManager.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        towerManager.onTouchEvent(motionEvent);
        towerBar.onTouchEvent(motionEvent);
        towerManager.onTowerUpgradeTouchEvent(motionEvent);
        pauseButton.onTouchEvent(motionEvent);
    }
}
