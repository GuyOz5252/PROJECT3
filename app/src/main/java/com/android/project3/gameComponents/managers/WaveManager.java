package com.android.project3.gameComponents.managers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.Pair;

import com.android.project3.data.EnemyType;
import com.android.project3.utils.Action;
import com.android.project3.gameComponents.Enemy;
import com.android.project3.gameComponents.EnemyPath;
import com.android.project3.gameComponents.WaveCounter;
import com.android.project3.gameComponents.buttons.StartWaveButton;
import com.android.project3.data.GameValues;

import java.util.ArrayList;

public class WaveManager {

    private Context context;

    private Enemy.EnemyFactory enemyFactory;

    private WaveCounter waveCounter;
    private StartWaveButton startWaveButton;

    private Action victory;

    private int currentWaveIndex;
    private Wave currentWave;
    private int enemyIndexInWave;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<Enemy> aliveList;

    private boolean isSpawning;
    private int updatesToNextSpawn;
    private int updatesBetweenSpawn;

    public WaveManager(Action victory, EnemyPath enemyPath, Context context) {
        this.enemyFactory = new Enemy.EnemyFactory(enemyPath, context);
        this.waveCounter = new WaveCounter(context);
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.isSpawning = false;
        this.currentWaveIndex = 0;

        this.victory = victory;
        this.context = context;
    }

    public int getCurrentWave() {
        return currentWaveIndex;
    }

    public void setCurrentWave(int currentWaveIndex) {
        this.currentWaveIndex = currentWaveIndex;
        waveCounter.changeText("WAVE: " + currentWaveIndex + "/" + waveArrayList.size());
    }

    public boolean getIsSpawning() {
        return isSpawning;
    }

    public ArrayList<Enemy> getAliveList() {
        return new ArrayList<>(aliveList);
    }

    public void setStartWaveButton(StartWaveButton startWaveButton) {
        this.startWaveButton = startWaveButton;
    }

    public void addWave(Wave wave) {
        waveArrayList.add(wave);
        waveCounter.changeText("WAVE: " + currentWaveIndex + "/" + waveArrayList.size());
    }

    public void startWave() {
        currentWave = waveArrayList.get(currentWaveIndex);
        isSpawning = true;
        startWaveButton.setIsActive(false);
        updatesBetweenSpawn = waveArrayList.get(currentWaveIndex).updatesBetweenSpawn;
        updatesToNextSpawn = updatesBetweenSpawn;
        waveCounter.changeText("WAVE: " + (currentWaveIndex+1) + "/" + waveArrayList.size());
    }

    public void spawnEnemy() {
        Pair<EnemyType, Integer> enemyMap = currentWave.enemyMap.get(enemyIndexInWave);
        if (enemyMap.second > 0) {
            new Thread(() -> aliveList.add(enemyFactory.createEnemy(currentWave.enemyMap.get(enemyIndexInWave).first))).start();
            currentWave.enemyMap.set(enemyIndexInWave, new Pair<>(enemyMap.first, enemyMap.second - 1));
        } else {
            enemyIndexInWave++;
        }
    }

    public void drawWaveCounter(Canvas canvas) {
        waveCounter.draw(canvas);
    }

    public void draw(Canvas canvas) {
        // to avoid ConcurrentModificationException, make a copy of a list that doesn't change
        ArrayList<Enemy> protectiveAliveList = new ArrayList<>(aliveList);
        protectiveAliveList.forEach(enemy -> enemy.draw(canvas));
    }

    public void update() {
        if (isSpawning) {
            if (updatesToNextSpawn >= updatesBetweenSpawn) {
                if (enemyIndexInWave < currentWave.enemyMap.size()) {
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
                if (!startWaveButton.getIsActive()) {
                    startWaveButton.setIsActive(true);
                }

                if (currentWaveIndex == waveArrayList.size() && !GameValues.isFinished) {
                    GameValues.isFinished = true;
                    ((Activity)context).runOnUiThread(() -> victory.action());
                }
            }
        }

        aliveList.forEach(Enemy::update);
        aliveList.removeIf(enemy -> !enemy.getIsAlive());
    }

    public static class Wave {

        private ArrayList<Pair<EnemyType, Integer>> enemyMap;
        private int updatesBetweenSpawn;

        public Wave(ArrayList<Pair<EnemyType, Integer>> enemyMap, int updatesBetweenSpawn) {
            this.enemyMap = new ArrayList<>(enemyMap);
            this.updatesBetweenSpawn = updatesBetweenSpawn;
        }

//        public void convertCodeToUnit(ArrayList<Pair<EnemyType, Integer>> enemyMap) {
//            enemyMap.forEach(enemyTypeIntegerPair -> {
//                for (int i = 0; i < enemyTypeIntegerPair.second; i++) {
//                    enemyArrayList.add(enemyFactory.createEnemy(enemyTypeIntegerPair.first));
//                }
//            });
//        }
    }
}
