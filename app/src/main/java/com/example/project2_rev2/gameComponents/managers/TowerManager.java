package com.example.project2_rev2.gameComponents.managers;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;
import static com.example.project2_rev2.utils.HelperMethods.getGameCoordinatesRect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.project2_rev2.data.TowerSaveData;
import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameComponents.TowerBar;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.towerTypes.DemoTower;
import com.example.project2_rev2.gameComponents.towerTypes.FireSpreader;
import com.example.project2_rev2.gameComponents.towerTypes.Turret;
import com.example.project2_rev2.utils.GameValues;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class TowerManager {

    private ArrayList<Tower> towerArrayList;
    //private AtomicReference<ArrayList<Tower>> atomicTowerArrayList;
    private boolean isAnyTowerSelected;
    private Tower selectedTower;
    private Tower.TowerFactory towerFactory;

    public TowerManager(TowerBar towerBar, WaveManager waveManager, ProjectileManager projectileManager, Context context) {
        this.towerArrayList = new ArrayList<>();
        this.isAnyTowerSelected = false;
        this.towerFactory = new Tower.TowerFactory(towerBar, waveManager, projectileManager, context);
    }

    public ArrayList<Tower> getTowerArrayList() {
        return towerArrayList;
    }

    public boolean getIsAnyTowerSelected() {
        return isAnyTowerSelected;
    }

    public void setTowerArrayList(ArrayList<TowerSaveData> towerSaveDataArrayList) {
        AtomicReference<ArrayList<Tower>> atomicTowerArrayList = new AtomicReference<>(towerArrayList);
        towerSaveDataArrayList.forEach(towerSaveData -> {
            Rect collider = getGameCoordinatesRect(towerSaveData.getCollider());
            atomicTowerArrayList.get().add(towerFactory.createTower(TowerType.valueOf(towerSaveData.getType()), xCoordinate(towerSaveData.getPosition().x), yCoordinate(towerSaveData.getPosition().y), collider));
            atomicTowerArrayList.get().get(towerArrayList.size()-1).loadUpgrades(0, towerSaveData.getPathOneLevel());
            atomicTowerArrayList.get().get(towerArrayList.size()-1).loadUpgrades(1, towerSaveData.getPathTwoLevel());
            GameValues.colliderArrayList.add(collider);
        });
    }

    public void addTower(TowerType towerType, double x, double y) {
        towerArrayList.add(towerFactory.createTower(towerType, x, y));
        User.getInstance().getPlayerStats().setTowersPlaced(User.getInstance().getPlayerStats().getTowersPlaced() + 1);
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
        towerArrayList.removeIf(tower -> !tower.getIsActive());
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        towerArrayList.forEach(tower -> tower.onTouchEvent(motionEvent));
    }
}
