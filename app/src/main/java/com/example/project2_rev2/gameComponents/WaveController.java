package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.utils.Size;

import java.util.ArrayList;

public class WaveController {

    private int currentWaveIndex;
    private ArrayList<EnemyUnit> enemyUnitArrayList;
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
        updatesToNextSpawn--;

        if (updatesToNextSpawn <= 0 && isWaveSpawning) {
            if (enemyIndexInWave < enemyUnitArrayList.size()) {
                aliveList.add(enemyUnitArrayList.get(enemyIndexInWave));
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

    /**********************************************************************************************/

    public static class Wave {

        private ArrayList<EnemyUnit> enemyUnitArrayList;

        public Wave(int[] enemyCodeArray, EnemyPath enemyPath, Context context) {
            this.enemyUnitArrayList = new ArrayList<>();
            convertCodeToUnit(enemyCodeArray, enemyPath, context);
        }

        private void convertCodeToUnit(int[] enemyCodeArray, EnemyPath enemyPath, Context context) {
            for (int i = 0; i < enemyCodeArray.length; i++) {
                switch (enemyCodeArray[i]) {
                    case 0:
                        enemyUnitArrayList.add(new EnemyUnit(R.drawable.ic_launcher_background, 6, new Size(100, 100), enemyPath, context));
                        break;
                }
            }
        }
    }
}
