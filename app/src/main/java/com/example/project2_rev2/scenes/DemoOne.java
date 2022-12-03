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

import com.example.project2_rev2.data.EnemyType;
import com.example.project2_rev2.data.SaveData;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.CoinCounter;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.HealthCounter;
import com.example.project2_rev2.gameComponents.button.PauseButton;
import com.example.project2_rev2.gameComponents.managers.DeathManager;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Position;

import java.util.HashMap;

/**
 * a class that includes all the components of a scene
 * a draw method and an update method to draw and updates all the components
 */

public class DemoOne extends Scene {

    // scene components
    private Rect backgroundRect;
    private Paint backgroundPaint;
    private Rect[] coverRect;
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
    private DeathManager deathManager;

    public DemoOne(Action[] actionsArray, boolean loadSave, Context context) {

        this.backgroundRect = new Rect(
                (int)xCoordinate(0),
                (int)yCoordinate(0),
                (int)xCoordinate(gameDisplay.size.width),
                (int)yCoordinate(gameDisplay.size.height)
        );
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setColor(ContextCompat.getColor(context, R.color.background));

        this.coverRect = new Rect[] {
                new Rect(
                        (int)xCoordinate(gameDisplay.size.width),
                        (int)yCoordinate(0),
                        (int)display.size.width,
                        (int)yCoordinate(display.size.height)
                ),
                new Rect(
                        0,
                        0,
                        (int)xCoordinate(0),
                        (int)display.size.height
                )
        };
        this.coverPaint = new Paint();
        this.coverPaint.setColor(ContextCompat.getColor(context, R.color.black));

        this.enemyPath = new EnemyPath();
        this.enemyPath.add(new Position(xCoordinate(330), yCoordinate(200)));
        this.enemyPath.add(new Position(xCoordinate(gameDisplay.size.width-300), yCoordinate(200)));
        this.enemyPath.add(new Position(xCoordinate(gameDisplay.size.width-300), yCoordinate(gameDisplay.size.height-300)));
        this.enemyPath.add(new Position(xCoordinate(600), yCoordinate(gameDisplay.size.height-300)));
        this.enemyPath.add(new Position(xCoordinate(600), yCoordinate(500)));
        this.enemyPath.add(new Position(xCoordinate(1200), yCoordinate(500)));
        this.enemyPath.add(new Position(xCoordinate(1200), yCoordinate(gameDisplay.size.height+50)));
        this.enemyPath.calculateColliders();

        this.waveManager = new WaveManager(actionsArray[1], context);
        HashMap<EnemyType, Integer> waveMap = new HashMap<>();
        waveMap.put(EnemyType.DEMO_ENEMY, 5);
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                40,
                context
        ));
        waveMap.clear();
        waveMap.put(EnemyType.DEMO_ENEMY, 15);
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                30,
                context
        ));
        waveMap.clear();
        waveMap.put(EnemyType.DEMO_ENEMY, 55);
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                30,
                context
        ));
        waveMap.clear();
        waveMap.put(EnemyType.DEMO_ENEMY, 500);
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                28,
                context
        ));

        this.projectileManager = new ProjectileManager(waveManager, context);
        this.towerBar = new TowerBar(waveManager, context);
        this.towerManager = new TowerManager(towerBar, waveManager, projectileManager, context);
        this.towerBar.setTowerManager(towerManager);
        this.pauseButton = new PauseButton(actionsArray[0], context);
        this.coinCounter = new CoinCounter(context);
        this.healthCounter = new HealthCounter(context);
        this.deathManager = new DeathManager(actionsArray[2], context);

        if (loadSave) {
            SaveData saveData = User.getInstance().getSaveData();
            waveManager.setCurrentWave(saveData.getCurrentWave());
            GameValues.setPlayerCoins(saveData.getMoney());
            GameValues.setPlayerHealth(saveData.getHealth());
            GameValues.colliderArrayList = saveData.getColliderArrayList();
            towerManager.setTowerArrayList(saveData.getTowerArrayList());
        }
    }

    @Override
    public void saveGame() {
        User.getInstance().setSaveData(new SaveData(
                0,
                waveManager.getCurrentWave(),
                GameValues.getPlayerCoins(),
                GameValues.getPlayerHealth(),
                GameValues.colliderArrayList,
                towerManager.getTowerArrayList()
        ));
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
        waveManager.drawWaveCounter(canvas);

        for (Rect rect : coverRect) {
            canvas.drawRect(rect, coverPaint);
        }
    }

    @Override
    public void update() {
        towerBar.update();
        waveManager.update();
        projectileManager.update();
        towerManager.update();
        deathManager.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        towerManager.onTouchEvent(motionEvent);
        towerBar.onTouchEvent(motionEvent);
        towerManager.onTowerUpgradeTouchEvent(motionEvent);
        pauseButton.onTouchEvent(motionEvent);
    }
}
