package com.example.project2_rev2.gameComponents;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WaveController {

    private int waveCount;
    private int currentWaveIndex;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<EnemyUnit> aliveList;

    public WaveController() {
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.currentWaveIndex = 0;
        this.waveCount = 0;
    }

    public int getWaveCount() {
        return waveCount;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
        waveCount++;
    }

    public void spawnEnemies() {
        EnemyUnit[] enemyUnitArray = waveArrayList.get(currentWaveIndex).enemyUnitArray;
        int enemyIndexInWave = 0;
        aliveList.add(enemyUnitArray[enemyIndexInWave]);
        enemyIndexInWave++;
    }

    public void draw(Canvas canvas) {
        for (EnemyUnit enemyUnit : aliveList) {
            enemyUnit.draw(canvas);
        }
    }

    public void update() {
        for (EnemyUnit enemyUnit : aliveList) {
            enemyUnit.update();
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {

    }

    /**********************************************************************************************/

    public static class Wave {

        private EnemyUnit[] enemyUnitArray;

        public Wave() {

        }
    }
}
