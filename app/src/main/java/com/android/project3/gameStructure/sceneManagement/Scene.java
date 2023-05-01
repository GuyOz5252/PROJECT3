package com.android.project3.gameStructure.sceneManagement;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import androidx.annotation.DrawableRes;

import com.android.project3.R;
import com.android.project3.data.SaveData;
import com.android.project3.data.User;
import com.android.project3.gameComponents.CoinCounter;
import com.android.project3.gameComponents.EnemyPath;
import com.android.project3.gameComponents.HealthCounter;
import com.android.project3.gameComponents.LoadingOverlay;
import com.android.project3.gameComponents.TowerBar;
import com.android.project3.gameComponents.buttons.PauseButton;
import com.android.project3.gameComponents.managers.DeathManager;
import com.android.project3.gameComponents.managers.ProjectileManager;
import com.android.project3.gameComponents.managers.TowerManager;
import com.android.project3.gameComponents.managers.WaveManager;
import com.android.project3.utils.Action;
import com.android.project3.data.GameValues;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Scene {

    protected Context context;

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

        this.enemyPath = new EnemyPath();
        this.waveManager = new WaveManager(actionsArray[1], enemyPath, context);
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

    public enum Scenes {
        DEMO_1("DEMO 1", R.drawable.demo_one, 0),
        PCB("PCB", R.drawable.pcb, 1);

        public String name;
        public int background;
        public int levelReq;

        Scenes(String name, @DrawableRes int background, int levelReq) {
            this.name = name;
            this.background = background;
            this.levelReq = levelReq;
        }
    }
}
