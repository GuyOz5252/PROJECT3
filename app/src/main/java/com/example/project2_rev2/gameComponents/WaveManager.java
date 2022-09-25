package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.abstractComponents.EnemyUnit;
import com.example.project2_rev2.gameComponents.enemyTypes.DemoEnemy;

import java.util.ArrayList;

public class WaveManager {

    private int currentWaveIndex;
    private ArrayList<EnemyUnit> enemyUnitArrayList;
    private int enemyIndexInWave;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<EnemyUnit> aliveList;

    private int updatesToNextSpawn;
    private final int UPDATES_BETWEEN_SPAWNS = 30;
    private boolean isWaveSpawning;

    public WaveManager() {
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.updatesToNextSpawn = 0;
        this.isWaveSpawning = false;
    }

    public ArrayList<EnemyUnit> getAliveList() {
        return aliveList;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
    }

    public void spawnEnemies() {
        isWaveSpawning = true;
        enemyUnitArrayList = waveArrayList.get(currentWaveIndex).enemyUnitArrayList;
    }

    public void draw(Canvas canvas) {
        for (EnemyUnit enemyUnit : aliveList) {
            enemyUnit.draw(canvas);
        }
    }

    public void update() {
        updatesToNextSpawn++;

        if (updatesToNextSpawn >= UPDATES_BETWEEN_SPAWNS && isWaveSpawning) {
            if (enemyIndexInWave < enemyUnitArrayList.size()) {
                aliveList.add(enemyUnitArrayList.get(enemyIndexInWave));
                System.out.println(enemyIndexInWave);
                enemyIndexInWave++;
                updatesToNextSpawn = 0;
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

    /**********************************************************************************************/

    public static class Wave {

        private ArrayList<EnemyUnit> enemyUnitArrayList;

        public Wave(EnemyUnit.EnemyTypes[] enemyCodeArray, EnemyPath enemyPath, Context context) {
            this.enemyUnitArrayList = new ArrayList<>();
            convertCodeToUnit(enemyCodeArray, enemyPath, context);
        }

        private void convertCodeToUnit(EnemyUnit.EnemyTypes[] enemyCodeArray, EnemyPath enemyPath, Context context) {
            for (int i = 0; i < enemyCodeArray.length; i++) {
                switch (enemyCodeArray[i]) {
                    case DEMO_ENEMY:
                        enemyUnitArrayList.add(new DemoEnemy(enemyPath, context));
                        break;
                }
            }
        }
    }
}
