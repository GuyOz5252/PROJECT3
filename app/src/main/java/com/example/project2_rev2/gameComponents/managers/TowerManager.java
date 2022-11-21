package com.example.project2_rev2.gameComponents.managers;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.towerTypes.DemoTower;
import com.example.project2_rev2.gameComponents.towerTypes.FireSpreader;
import com.example.project2_rev2.gameComponents.towerTypes.Turret;

import java.util.ArrayList;

public class TowerManager {

    private Context context;
    private TowerBar towerBar;
    private WaveManager waveManager;
    private ProjectileManager projectileManager;
    private ArrayList<Tower> towerArrayList;
    private boolean isAnyTowerSelected;
    private Tower selectedTower;

    public TowerManager(TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        this.context = context;
        this.towerBar = towerBar;
        this.waveManager = waveManager;
        this.projectileManager = projectileManager;
        this.towerArrayList = new ArrayList<>();
        this.isAnyTowerSelected = false;
    }

    public void removeTower(Tower tower) {
        towerArrayList.remove(tower);
    }

    public boolean getIsAnyTowerSelected() {
        return isAnyTowerSelected;
    }

    public void addTower(Tower.TowerType towerType, double x, double y) {
        switch (towerType) {
            case DEMO_TOWER:
                towerArrayList.add(new DemoTower(x, y, towerBar, waveManager, projectileManager, this, context));
                break;
            case TURRET:
                towerArrayList.add(new Turret(x, y, towerBar, waveManager, projectileManager, this, context));
                break;
            case FIRE_SPREADER:
                towerArrayList.add(new FireSpreader(x, y, towerBar, waveManager, projectileManager, this, context));
                break;
        }
    }

    public void drawTowerUpgradeUI(Canvas canvas) {
        if (selectedTower != null) {
            selectedTower.drawTowerUpgradeUI(canvas);
        }
    }

    public void onTowerUpgradeTouchEvent(MotionEvent motionEvent) {
        if (selectedTower != null) {
            selectedTower.onTowerUpgradeTouchEvent(motionEvent);
        }
    }

    public void draw(Canvas canvas) {
        towerArrayList.forEach(tower -> tower.draw(canvas));
    }

    public void update() {
        towerArrayList.forEach(tower -> {
            tower.update();
            if (tower.getIsSelected()) {
                isAnyTowerSelected = true;
                selectedTower = tower;
            }
        });
        if (selectedTower != null) {
            if (!selectedTower.getIsSelected()) {
                isAnyTowerSelected = false;
                selectedTower = null;
            }
        }
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        towerArrayList.forEach(tower -> tower.onTouchEvent(motionEvent));
    }
}
