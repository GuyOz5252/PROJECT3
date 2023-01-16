package com.example.project2_rev2.gameStructure.sceneManagement;

import static com.example.project2_rev2.utils.GameValues.display;
import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.SaveData;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameComponents.CoinCounter;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.HealthCounter;
import com.example.project2_rev2.gameComponents.LoadingOverlay;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.buttons.PauseButton;
import com.example.project2_rev2.gameComponents.managers.DeathManager;
import com.example.project2_rev2.gameComponents.managers.ProjectileManager;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.utils.GameValues;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Scene {

    protected Context context;

    // scene components
    protected Rect[] coverRect;
    protected Paint coverPaint;

    // game components
    protected TowerBar towerBar;
    protected EnemyPath enemyPath;
    protected WaveManager waveManager;
    protected ProjectileManager projectileManager;
    protected TowerManager towerManager;
    protected PauseButton pauseButton;
    protected CoinCounter coinCounter;
    protected HealthCounter healthCounter;
    protected DeathManager deathManager;

    private final AtomicBoolean finishedLoading;
    private final LoadingOverlay loadingOverlay;

    public Scene(Action[] actionsArray, boolean loadSave, Context context) {

        this.context = context;

        this.coverRect = new Rect[] {
                new Rect(
                        (int)xCoordinate(gameDisplay.size.width),
                        0,
                        (int)display.size.width,
                        (int)yCoordinate(display.size.height)
                ),
                new Rect(
                        0,
                        0,
                        (int)xCoordinate(0),
                        (int)display.size.height
                ),
                new Rect(
                        (int)xCoordinate(0),
                        0,
                        (int)xCoordinate(gameDisplay.size.width),
                        (int)yCoordinate(0)
                ),
                new Rect(
                        (int)xCoordinate(0),
                        (int)yCoordinate(gameDisplay.size.height),
                        (int)xCoordinate(gameDisplay.size.width),
                        (int)display.size.height
                )
        };
        this.coverPaint = new Paint();
        this.coverPaint.setColor(ContextCompat.getColor(context, R.color.black));

        this.enemyPath = new EnemyPath();
        this.waveManager = new WaveManager(actionsArray[1], context);
        this.projectileManager = new ProjectileManager(waveManager, context);
        this.towerBar = new TowerBar(waveManager, context);
        this.towerManager = new TowerManager(towerBar, waveManager, projectileManager, context);
        this.towerBar.setTowerManager(towerManager);
        this.pauseButton = new PauseButton(actionsArray[0], context);
        this.coinCounter = new CoinCounter(context);
        this.healthCounter = new HealthCounter(context);
        this.deathManager = new DeathManager(actionsArray[2], context);

        this.finishedLoading = new AtomicBoolean(false);
        this.loadingOverlay = new LoadingOverlay(context);

        if (loadSave) {
            loadGame();
        } else {
            finishedLoading.set(true);
        }
    }

    public void saveGame() {
        User.getInstance().setSaveData(new SaveData(
                0,
                (waveManager.getAliveList().isEmpty() || waveManager.getIsSpawning()) ? waveManager.getCurrentWave() : waveManager.getCurrentWave()-1,
                GameValues.getPlayerCoins(),
                GameValues.getPlayerHealth(),
                towerManager.getTowerArrayList(),
                GameValues.isFinished,
                true
        ));
    }

    public void loadGame() {
        Thread thread = new Thread(() -> {
            SaveData saveData = User.getInstance().getSaveData();
            waveManager.setCurrentWave(saveData.getCurrentWave());
            GameValues.setPlayerCoins(saveData.getMoney());
            GameValues.setPlayerHealth(saveData.getHealth());
            towerManager.setTowerArrayList(saveData.getTowerArrayList());
            GameValues.isFinished = saveData.getIsFinished();
            finishedLoading.set(true);
        });
        thread.start();
    }

    public void draw(Canvas canvas) {
        //enemyPath.draw(canvas);
        waveManager.draw(canvas);
        projectileManager.draw(canvas);
        towerManager.draw(canvas);
        towerBar.draw(canvas);
        towerManager.drawTowerUpgradeUI(canvas);
        pauseButton.draw(canvas);
        coinCounter.draw(canvas);
        healthCounter.draw(canvas);
        waveManager.drawWaveCounter(canvas);

        if (!finishedLoading.get()) {
            loadingOverlay.draw(canvas);
        }

        for (Rect rect : coverRect) {
            canvas.drawRect(rect, coverPaint);
        }
    }

    public void update() {
        towerBar.update();
        waveManager.update();
        projectileManager.update();
        towerManager.update();
        deathManager.update();
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        if (finishedLoading.get()) {
            towerManager.onTouchEvent(motionEvent);
            towerBar.onTouchEvent(motionEvent);
            towerManager.onTowerUpgradeTouchEvent(motionEvent);
            pauseButton.onTouchEvent(motionEvent);
        }
    }

    public enum Levels {
        DEMO_1("DEMO 1", R.drawable.demo_one);

        public String name;
        public int background;

        Levels(String name, @DrawableRes int background) {
            this.name = name;
            this.background = background;
        }
    }
}
