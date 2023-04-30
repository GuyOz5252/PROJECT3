package com.android.project3.scenes;

import static com.android.project3.data.GameValues.gameDisplay;
import static com.android.project3.utils.HelperMethods.getBitmapFromPicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Pair;

import com.android.project3.R;
import com.android.project3.data.EnemyType;
import com.android.project3.gameComponents.managers.WaveManager;
import com.android.project3.gameStructure.sceneManagement.Scene;
import com.android.project3.utils.Action;
import com.android.project3.utils.Position;

import java.util.ArrayList;

public class DemoTwo extends Scene {

    private Bitmap background;

    public DemoTwo(Action[] actionsArray, boolean loadSave, Context context) {
        super(actionsArray, loadSave, context);

        this.background = Bitmap.createScaledBitmap(getBitmapFromPicture(context, R.drawable.demo_one), 1566, 1080, false);

        this.enemyPath.add(new Position(330, 195));
        this.enemyPath.add(new Position(gameDisplay.size.width-300, 195));
        this.enemyPath.add(new Position(gameDisplay.size.width-300, gameDisplay.size.height-290));
        this.enemyPath.add(new Position(600, gameDisplay.size.height-290));
        this.enemyPath.add(new Position(600, 500));
        this.enemyPath.add(new Position(1230, 500));
        this.enemyPath.add(new Position(1230, gameDisplay.size.height+50));
        this.enemyPath.calculateColliders();

        ArrayList<Pair<EnemyType, Integer>> waveMap = new ArrayList<>();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 10));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 28));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 25));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 10));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 20));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 25));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 25));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 18));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 40));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 15));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 5));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 15));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 55));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 30));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 15));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 10));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 20));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 70));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 15));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 30));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 70));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 15));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 80));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 130));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 15));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 20));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 150));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 100));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 15));
        waveMap.add(new Pair<>(EnemyType.ARMOR_ENEMY, 50));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 20));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 15));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 20));
        waveMap.add(new Pair<>(EnemyType.ARMOR_ENEMY, 100));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 100));
        waveMap.add(new Pair<>(EnemyType.ARMOR_ENEMY, 20));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 130));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 10));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY, 200));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 150));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 50));
        waveMap.add(new Pair<>(EnemyType.ARMOR_ENEMY, 150));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 120));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 50));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 7));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 25));
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.ENEMY, 25));
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        this.waveManager.addWave(new WaveManager.Wave(waveMap,20));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.BOSS, 5));
        this.waveManager.addWave(new WaveManager.Wave(waveMap,140));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 2));
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 2));
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 2));
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.CAMO_ENEMY, 2));
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 2));
        this.waveManager.addWave(new WaveManager.Wave(waveMap,80));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 1));
        waveMap.add(new Pair<>(EnemyType.BOSS, 10));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 25));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 70));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.POWER_ENEMY, 50));
        waveMap.add(new Pair<>(EnemyType.ENEMY_V2, 20));
        this.waveManager.addWave(new WaveManager.Wave(waveMap, 50));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.POWER_ENEMY, 10));
        waveMap.add(new Pair<>(EnemyType.BOSS, 10));
        waveMap.add(new Pair<>(EnemyType.POWER_ENEMY, 10));
        waveMap.add(new Pair<>(EnemyType.BOSS, 1));
        this.waveManager.addWave(new WaveManager.Wave(waveMap,70));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, 350f, 0f, null);
        super.draw(canvas);
    }
}
