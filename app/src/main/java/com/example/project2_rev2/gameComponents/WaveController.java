package com.example.project2_rev2.gameComponents;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class WaveController {

    private int currentWaveIndex;
    private EnemyUnit[] enemyUnitArray;
    private int enemyIndexInWave;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<EnemyUnit> aliveList;

    private int updatesToNextSpawn;
    private final int UPDATES_BETWEEN_SPAWNS = 30;
    private boolean isWaveSpawning;

    public WaveController() {
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.updatesToNextSpawn = UPDATES_BETWEEN_SPAWNS;
        this.isWaveSpawning = false;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
    }

    public void spawnEnemies() {
        isWaveSpawning = true;
        enemyUnitArray = waveArrayList.get(currentWaveIndex).enemyUnitArray;
    }

    public void draw(Canvas canvas) {
        for (EnemyUnit enemyUnit : aliveList) {
            enemyUnit.draw(canvas);
        }
    }

    public void update() {
        updatesToNextSpawn--;

        if (updatesToNextSpawn <= 0 && isWaveSpawning) {
            if (enemyIndexInWave < enemyUnitArray.length) {
                aliveList.add(enemyUnitArray[enemyIndexInWave]);
                System.out.println(enemyIndexInWave);
                enemyIndexInWave++;
                updatesToNextSpawn = UPDATES_BETWEEN_SPAWNS;
            } else {
                isWaveSpawning = false;
                currentWaveIndex++;
            }
        }

        for (EnemyUnit enemyUnit : aliveList) {
            enemyUnit.update();
            if (!enemyUnit.getIsAlive()) {
                aliveList.remove(enemyUnit);
            }
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {

    }

    /**********************************************************************************************/

    public static class Wave {

        private EnemyUnit[] enemyUnitArray;

        public Wave(EnemyUnit[] enemyUnitArray) {
            this.enemyUnitArray = enemyUnitArray;
        }
    }
}
