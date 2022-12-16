package com.example.project2_rev2.scenes;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.EnemyType;
import com.example.project2_rev2.gameComponents.managers.WaveManager;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.utils.Position;

import java.util.HashMap;

/**
 * a class that includes all the components of a scene
 * a draw method and an update method to draw and updates all the components
 * needs: setting the background, setting the path, setting waves
 */

public class DemoOne extends Scene {

    private Rect backgroundRect;
    private Paint backgroundPaint;

    public DemoOne(Action[] actionsArray, boolean loadSave, Context context) {
        super(actionsArray, loadSave, context);

        this.backgroundRect = new Rect(
                (int)xCoordinate(0),
                (int)yCoordinate(0),
                (int)xCoordinate(gameDisplay.size.width),
                (int)yCoordinate(gameDisplay.size.height)
        );
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setColor(ContextCompat.getColor(context, R.color.background));

        this.enemyPath.add(new Position(xCoordinate(330), yCoordinate(200)));
        this.enemyPath.add(new Position(xCoordinate(gameDisplay.size.width-300), yCoordinate(200)));
        this.enemyPath.add(new Position(xCoordinate(gameDisplay.size.width-300), yCoordinate(gameDisplay.size.height-300)));
        this.enemyPath.add(new Position(xCoordinate(600), yCoordinate(gameDisplay.size.height-300)));
        this.enemyPath.add(new Position(xCoordinate(600), yCoordinate(500)));
        this.enemyPath.add(new Position(xCoordinate(1200), yCoordinate(500)));
        this.enemyPath.add(new Position(xCoordinate(1200), yCoordinate(gameDisplay.size.height+50)));
        this.enemyPath.calculateColliders();

        HashMap<EnemyType, Integer> waveMap = new HashMap<>();
        waveMap.put(EnemyType.DEMO_ENEMY, 5);
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                40,
                context
        ));
        waveMap.clear();
        waveMap.put(EnemyType.DEMO_ENEMY, 15);
        this.waveManager.addWave(new WaveManager.Wave(
                waveMap,
                enemyPath,
                30,
                context
        ));
        //waveMap.clear();
        //waveMap.put(EnemyType.DEMO_ENEMY, 55);
        //this.waveManager.addWave(new WaveManager.Wave(
        //        waveMap,
        //        enemyPath,
        //        30,
        //        context
        //));
        //waveMap.clear();
        //waveMap.put(EnemyType.DEMO_ENEMY, 100);
        //this.waveManager.addWave(new WaveManager.Wave(
        //        waveMap,
        //        enemyPath,
        //        30,
        //        context
        //));
        //waveMap.clear();
        //waveMap.put(EnemyType.DEMO_ENEMY, 150);
        //this.waveManager.addWave(new WaveManager.Wave(
        //        waveMap,
        //        enemyPath,
        //        28,
        //        context
        //));
        //waveMap.clear();
        //waveMap.put(EnemyType.DEMO_ENEMY, 200);
        //this.waveManager.addWave(new WaveManager.Wave(
        //        waveMap,
        //        enemyPath,
        //        28,
        //        context
        //));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(backgroundRect, backgroundPaint);
        super.draw(canvas);
    }
}
