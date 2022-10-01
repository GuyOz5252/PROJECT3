package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.abstractComponents.Enemy;
import com.example.project2_rev2.gameComponents.button.StartWaveButton;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;

import java.util.ArrayList;

public class WaveManager {

    private WaveCounter waveCounter;
    private StartWaveButton startWaveButton;

    private int currentWaveIndex;
    private ArrayList<Enemy> enemyArrayList;
    private int enemyIndexInWave;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<Enemy> aliveList;

    private boolean isSpawning;
    private int updatesToNextSpawn;
    private final int UPDATES_BETWEEN_SPAWNS = 60;

    public WaveManager(WaveCounter waveCounter) {
        this.waveCounter = waveCounter;
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.isSpawning = false;
        this.currentWaveIndex = 0;
        this.updatesToNextSpawn = UPDATES_BETWEEN_SPAWNS;
    }

    public ArrayList<Enemy> getAliveList() {
        return aliveList;
    }

    public int getWaveCount() {
        return waveArrayList.size();
    }

    public void setWaveCounter(WaveCounter waveCounter) {
        this.waveCounter = waveCounter;
    }

    public void setStartWaveButton(StartWaveButton startWaveButton) {
        this.startWaveButton = startWaveButton;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
    }

    public void startWave() {
        enemyArrayList = waveArrayList.get(currentWaveIndex).enemyArrayList;
        isSpawning = true;
        startWaveButton.setIsActive(false);
    }

    public void spawnEnemy() {
        aliveList.add(enemyArrayList.get(enemyIndexInWave));
        enemyIndexInWave++;
        waveCounter.changeText("WAVE: " + (currentWaveIndex+1) + "/" + waveArrayList.size());
    }

    public void draw(Canvas canvas) {
        for (Enemy enemy : aliveList) {
            enemy.draw(canvas);
        }
    }

    public void update() {
        if (isSpawning) {
            if (updatesToNextSpawn >= UPDATES_BETWEEN_SPAWNS / Scene.speedMultiplier) {
                if (enemyIndexInWave < enemyArrayList.size()) {
                    spawnEnemy();
                    updatesToNextSpawn = 0;
                } else {
                    isSpawning = false;
                    enemyIndexInWave = 0;
                    currentWaveIndex++;
                }
            } else {
                updatesToNextSpawn++;
            }
        } else {
            if (aliveList.isEmpty()) {
                startWaveButton.setIsActive(true);
            }
        }

        for (Enemy enemy : aliveList) {
            enemy.update();
            if (!enemy.getIsAlive()) {
                aliveList.remove(enemy);
            }
        }
    }

    /**********************************************************************************************/

    public static class Wave {

        private ArrayList<Enemy> enemyArrayList;

        public Wave(Enemy.EnemyTypes[] enemyArray, EnemyPath enemyPath, Context context) {
            this.enemyArrayList = new ArrayList<>();
            convertCodeToUnit(enemyArray, enemyPath, context);
        }

        private void convertCodeToUnit(Enemy.EnemyTypes[] enemyArray, EnemyPath enemyPath, Context context) {
            for (Enemy.EnemyTypes enemyType : enemyArray) {
                enemyArrayList.add(new Enemy(enemyType, enemyPath, context));
            }
        }
    }
}
