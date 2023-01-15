package com.example.project2_rev2.scenes;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;
import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromPicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Pair;

import androidx.annotation.RequiresApi;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.EnemyType;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.utils.Position;

import java.util.ArrayList;
import java.util.HashMap;

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

        this.enemyPath.add(new Position(xCoordinate(330), yCoordinate(195)));
        this.enemyPath.add(new Position(xCoordinate(gameDisplay.size.width-300), yCoordinate(195)));
        this.enemyPath.add(new Position(xCoordinate(gameDisplay.size.width-300), yCoordinate(gameDisplay.size.height-290)));
        this.enemyPath.add(new Position(xCoordinate(600), yCoordinate(gameDisplay.size.height-290)));
        this.enemyPath.add(new Position(xCoordinate(600), yCoordinate(500)));
        this.enemyPath.add(new Position(xCoordinate(1230), yCoordinate(500)));
        this.enemyPath.add(new Position(xCoordinate(1230), yCoordinate(gameDisplay.size.height+50)));
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
                30,
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
        waveMap.add(new Pair<>(EnemyType.ARMOR_DEMO_ENEMY, 30));
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                20,
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
        canvas.drawBitmap(background, (float)xCoordinate(350), (float)yCoordinate(0), null);
        super.draw(canvas);
    }
}
