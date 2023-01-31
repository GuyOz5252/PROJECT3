package com.android.project3.scenes;

import static com.android.project3.utils.GameValues.gameDisplay;
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

/**
 * a class that includes all the components of a scene
 * a draw method and an update method to draw and updates all the components
 * needs: setting the background, setting the path, setting waves
 */

public class DemoOne extends Scene {

    private Bitmap background;

    public DemoOne(Action[] actionsArray, boolean loadSave, Context context) {
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
        //waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 5));
        //this.waveManager.addWave(new WaveManager.Wave(
        //        waveMap,
        //        enemyPath,
        //        40,
        //        context
        //));
        //waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 10));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                28,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 25));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                20,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 55));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 30));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 10));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                20,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 70));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 30));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 70));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 80));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 130));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                15,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 20));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 150));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 100));
        waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 50));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 20));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                15,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 20));
        waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 100));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 100));
        waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 20));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 130));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                10,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 200));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 150));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 50));
        waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 150));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 120));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 50));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                7,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 25));
        waveMap.add(new Pair<>(EnemyType.DEMO_BOSS, 1));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 25));
        waveMap.add(new Pair<>(EnemyType.DEMO_BOSS, 1));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                20,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_BOSS, 5));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                30,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_BOSS, 5));
        waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 20));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                140,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_BOSS, 10));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                140,
                context
        ));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, 350f, 0f, null);
        super.draw(canvas);
    }
}
