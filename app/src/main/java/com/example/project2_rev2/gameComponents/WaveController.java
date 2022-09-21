package com.example.project2_rev2.gameComponents;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

public class WaveController {

    private int currentWaveIndex;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<EnemyUnit> aliveList;

    public WaveController() {
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.currentWaveIndex = 0;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
    }

    public void spawnEnemies() {
        EnemyUnit[] enemyUnitArray = waveArrayList.get(currentWaveIndex).enemyUnitArray;
        int enemyIndexInWave = 0;
        while (enemyIndexInWave < enemyUnitArray.length) {
            aliveList.add(enemyUnitArray[enemyIndexInWave]);
            System.out.println(enemyIndexInWave);
            enemyIndexInWave++;
        }
    }

    public void draw(Canvas canvas) {
        for (EnemyUnit enemyUnit : aliveList) {
            enemyUnit.draw(canvas);
        }
    }

    public void update() {
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
