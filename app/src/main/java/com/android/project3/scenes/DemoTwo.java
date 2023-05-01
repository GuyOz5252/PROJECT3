package com.android.project3.scenes;

import static com.android.project3.data.GameValues.gameDisplay;
import static com.android.project3.utils.HelperMethods.getBitmapFromPicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Pair;

import com.android.project3.R;
import com.android.project3.data.EnemyType;
import com.android.project3.data.GameValues;
import com.android.project3.gameComponents.managers.WaveManager;
import com.android.project3.gameStructure.GameView;
import com.android.project3.gameStructure.sceneManagement.Scene;
import com.android.project3.utils.Action;
import com.android.project3.utils.Position;

import java.util.ArrayList;

public class DemoTwo extends Scene {

    private Bitmap background;
    private Rect chip1;
    private Rect chip2;

    public DemoTwo(Action[] actionsArray, boolean loadSave, Context context) {
        super(actionsArray, loadSave, context);

        this.background = Bitmap.createScaledBitmap(getBitmapFromPicture(context, R.drawable.pcbv2), 1566, 1080, false);

        this.enemyPath.add(new Position(gameDisplay.size.width-180, 340));
        this.enemyPath.add(new Position(gameDisplay.size.width-180, gameDisplay.size.height-140));
        this.enemyPath.add(new Position(gameDisplay.size.width-420, gameDisplay.size.height-140));
        this.enemyPath.add(new Position(gameDisplay.size.width-420, gameDisplay.size.height-420));
        this.enemyPath.add(new Position(gameDisplay.size.width-735, gameDisplay.size.height-420));
        this.enemyPath.add(new Position(gameDisplay.size.width-735, gameDisplay.size.height-140));
        this.enemyPath.add(new Position(1010, gameDisplay.size.height-140));
        this.enemyPath.add(new Position(1010, gameDisplay.size.height-425));
        this.enemyPath.add(new Position(725, gameDisplay.size.height-425));
        this.enemyPath.add(new Position(725, gameDisplay.size.height-145));
        this.enemyPath.add(new Position(490, gameDisplay.size.height-145));
        this.enemyPath.add(new Position(490, 100));
        this.enemyPath.add(new Position(gameDisplay.size.width-795, 100));
        this.enemyPath.add(new Position(gameDisplay.size.width-795, 215));
        this.enemyPath.add(new Position(gameDisplay.size.width-300, 215));
        this.enemyPath.calculateColliders();

        this.chip1 = new Rect(
                690,
                277,
                1005,
                452
        );
        this.chip2 = new Rect(
                1630,
                68,
                1870,
                300
        );
        GameValues.colliderArrayList.add(chip1);
        GameValues.colliderArrayList.add(chip2);

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
