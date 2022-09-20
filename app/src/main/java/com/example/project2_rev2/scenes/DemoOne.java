package com.example.project2_rev2.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.EnemyUnit;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

import java.util.ArrayList;

public class DemoOne extends Scene {

    private Context context;

    // game components
    private TowerBar towerBar;
    private EnemyPath enemyPath;
    private EnemyUnit testEnemy;

    public DemoOne(Display display, Context context) {
        this.context = context;

        towerBar = new TowerBar(display, context);
        enemyPath = new EnemyPath();
        enemyPath.add(new Position(345, display.size.height/2));
        enemyPath.add(new Position(900, display.size.height/2));
        enemyPath.add(new Position(900, 300));
        enemyPath.add(new Position(1250, 300));
        enemyPath.add(new Position(1250, display.size.height/2+100));
        enemyPath.add(new Position(2000, display.size.height/2+100));

        testEnemy = new EnemyUnit(100, 100, 0, new Size(120, 120), context);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.background));

        enemyPath.draw(canvas);
        towerBar.draw(canvas);
        testEnemy.draw(canvas);
    }

    @Override
    public void update() {
        towerBar.update();
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {

    }
}
