package com.example.project2_rev2.gameComponents.managers;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.WaveCounter;
import com.example.project2_rev2.gameComponents.button.StartWaveButton;
import com.example.project2_rev2.utils.GameValues;

import java.util.ArrayList;

public class WaveManager {

    private Context context;

    private WaveCounter waveCounter;
    private StartWaveButton startWaveButton;

    private Action victory;

    private int currentWaveIndex;
    private ArrayList<Enemy> enemyArrayList;
    private int enemyIndexInWave;

    private ArrayList<Wave> waveArrayList;
    private ArrayList<Enemy> aliveList;

    private boolean isSpawning;
    private int updatesToNextSpawn;
    private int updatesBetweenSpawn;

    public WaveManager(Action victory, Context context) {
        this.waveCounter = new WaveCounter(context);
        this.waveArrayList = new ArrayList<>();
        this.aliveList = new ArrayList<>();
        this.isSpawning = false;
        this.currentWaveIndex = 0;

        this.victory = victory;
        this.context = context;
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
        updatesBetweenSpawn = waveArrayList.get(currentWaveIndex).updatesBetweenSpawn;
        updatesToNextSpawn = updatesBetweenSpawn;
        waveCounter.changeText("WAVE: " + (currentWaveIndex+1) + "/" + waveArrayList.size());
    }

    public void spawnEnemy() {
        aliveList.add(enemyArrayList.get(enemyIndexInWave));
        enemyIndexInWave++;
    }

    public void draw(Canvas canvas) {
        for (Enemy enemy : aliveList) {
            enemy.draw(canvas);
        }

        waveCounter.draw(canvas);
    }

    public void update() {
        if (isSpawning) {
            if (updatesToNextSpawn >= updatesBetweenSpawn) {
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
                if (!startWaveButton.getIsActive()) {
                    startWaveButton.setIsActive(true);
                }


                if (currentWaveIndex == waveArrayList.size() && !GameValues.isFinished) {
                    GameValues.isFinished = true;
                    ((Activity)context).runOnUiThread(() -> victory.action());
                }
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
        private int updatesBetweenSpawn;

        public Wave(Enemy.EnemyType[] enemyArray, EnemyPath enemyPath, int updatesBetweenSpawn, Context context) {
            this.enemyArrayList = new ArrayList<>();
            this.updatesBetweenSpawn = updatesBetweenSpawn;
            convertCodeToUnit(enemyArray, enemyPath, context);
        }

        private void convertCodeToUnit(Enemy.EnemyType[] enemyArray, EnemyPath enemyPath, Context context) {
            for (Enemy.EnemyType enemyType : enemyArray) {
                enemyArrayList.add(new Enemy(enemyType, enemyPath, context));
            }
        }
    }
}
