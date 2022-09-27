package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.abstractComponents.Enemy;
import com.example.project2_rev2.gameComponents.enemyTypes.DemoEnemy;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WaveManager {

    private int currentWaveIndex;
    private ArrayList<Enemy> enemyArrayList;
    private int enemyIndexInWave;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<Enemy> aliveList;

    private boolean isSpawning;
    private int updatesToNextSpawn;
    private final int UPDATES_BETWEEN_SPAWNS = 60;
    private int updatesToNextWave;
    private final int UPDATES_BETWEEN_WAVES = 800;

    public WaveManager() {
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.isSpawning = false;
        this.currentWaveIndex = 0;
        this.updatesToNextSpawn = 0;
        this.updatesToNextWave = 0;
    }

    public ArrayList<Enemy> getAliveList() {
        return aliveList;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
    }

    public void spawnEnemies() {
        enemyArrayList = waveArrayList.get(currentWaveIndex).enemyArrayList;
        isSpawning = true;
    }

    public void draw(Canvas canvas) {
        for (Enemy enemy : aliveList) {
            enemy.draw(canvas);
        }
    }

    public void update() {
        if (updatesToNextSpawn < UPDATES_BETWEEN_SPAWNS) {
            updatesToNextSpawn++;
        }

        if (updatesToNextSpawn >= UPDATES_BETWEEN_SPAWNS && isSpawning) {
            if (enemyIndexInWave < enemyArrayList.size()) {
                aliveList.add(enemyArrayList.get(enemyIndexInWave));
                System.out.println(enemyIndexInWave);
                enemyIndexInWave++;
                updatesToNextSpawn = 0;
            } else {
                isSpawning = false;
                currentWaveIndex++;
                enemyIndexInWave = 0;
            }
        }

        if (updatesToNextWave < UPDATES_BETWEEN_WAVES && !isSpawning) {
            updatesToNextWave++;
        }

        if (updatesToNextWave >= UPDATES_BETWEEN_WAVES) {
            updatesToNextWave = 0;
            spawnEnemies();
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

        public Wave(Enemy.EnemyTypes[] enemyCodeArray, EnemyPath enemyPath, Context context) {
            this.enemyArrayList = new ArrayList<>();
            convertCodeToUnit(enemyCodeArray, enemyPath, context);
        }

        private void convertCodeToUnit(Enemy.EnemyTypes[] enemyCodeArray, EnemyPath enemyPath, Context context) {
            for (int i = 0; i < enemyCodeArray.length; i++) {
                switch (enemyCodeArray[i]) {
                    case DEMO_ENEMY:
                        enemyArrayList.add(new DemoEnemy(enemyPath, context));
                        break;
                }
            }
        }
    }
}
