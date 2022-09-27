package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.abstractComponents.Enemy;
import com.example.project2_rev2.gameComponents.enemyTypes.DemoEnemy;

import java.util.ArrayList;

public class WaveManager {

    private int currentWaveIndex;
    private ArrayList<Enemy> enemyArrayList;
    private int enemyIndexInWave;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<Enemy> aliveList;

    private int updatesToNextSpawn;
    private final int UPDATES_BETWEEN_SPAWNS = 60;
    private boolean isWaveSpawning;

    public WaveManager() {
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.updatesToNextSpawn = 0;
        this.isWaveSpawning = false;
    }

    public ArrayList<Enemy> getAliveList() {
        return aliveList;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
    }

    public void spawnEnemies() {
        isWaveSpawning = true;
        enemyArrayList = waveArrayList.get(currentWaveIndex).enemyArrayList;
    }

    public void draw(Canvas canvas) {
        for (Enemy enemy : aliveList) {
            enemy.draw(canvas);
        }
    }

    public void update() {
        updatesToNextSpawn++;

        if (updatesToNextSpawn >= UPDATES_BETWEEN_SPAWNS && isWaveSpawning) {
            if (enemyIndexInWave < enemyArrayList.size()) {
                aliveList.add(enemyArrayList.get(enemyIndexInWave));
                System.out.println(enemyIndexInWave);
                enemyIndexInWave++;
                updatesToNextSpawn = 0;
            } else {
                isWaveSpawning = false;
                currentWaveIndex++;
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
