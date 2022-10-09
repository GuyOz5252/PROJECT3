package com.example.project2_rev2.gameComponents.managers;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.WaveCounter;
import com.example.project2_rev2.gameComponents.button.StartWaveButton;

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

    public WaveManager(Context context) {
        this.waveCounter = new WaveCounter(context);
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.isSpawning = false;
        this.currentWaveIndex = 0;
        this.updatesToNextSpawn = UPDATES_BETWEEN_SPAWNS;
    }

    public ArrayList<Enemy> getAliveList() {
        return aliveList;
    }

    public void setStartWaveButton(StartWaveButton startWaveButton) {
        this.startWaveButton = startWaveButton;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
        waveCounter.changeText("WAVE: 0/" + waveArrayList.size());
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

        waveCounter.draw(canvas);
    }

    public void update() {
        if (isSpawning) {
            if (updatesToNextSpawn >= UPDATES_BETWEEN_SPAWNS) {
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

        aliveList.forEach(enemy -> {
            enemy.update();
            if (!enemy.getIsAlive()) {
                aliveList.remove(enemy);
            }
        });
    }

    /**********************************************************************************************/

    public static class Wave {

        private ArrayList<Enemy> enemyArrayList;

        public Wave(Enemy.EnemyType[] enemyArray, EnemyPath enemyPath, Context context) {
            this.enemyArrayList = new ArrayList<>();
            convertCodeToUnit(enemyArray, enemyPath, context);
        }

        private void convertCodeToUnit(Enemy.EnemyType[] enemyArray, EnemyPath enemyPath, Context context) {
            for (Enemy.EnemyType enemyType : enemyArray) {
                enemyArrayList.add(new Enemy(enemyType, enemyPath, context));
            }
        }
    }
}
