package com.example.project2_rev2.scenes;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromPicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Pair;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.EnemyType;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.utils.Position;

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
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 5));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 3));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 5));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                30,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 55));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                30,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 70));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 30));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                12,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 150));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                28,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 70));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                10,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 70));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 30));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 50));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 50));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                28,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 5));
        waveMap.add(new Pair<>(EnemyType.DEMO_ENEMY, 15));
        waveMap.add(new Pair<>(EnemyType.CAMO_DEMO_ENEMY, 20));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                30,
                context
        ));
        waveMap.clear();
        waveMap.add(new Pair<>(EnemyType.DEMO_BOSS, 1));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                0,
                context
        ));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, 350f, 0f, null);
        super.draw(canvas);
    }
}
