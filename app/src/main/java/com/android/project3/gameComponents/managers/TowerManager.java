package com.android.project3.gameComponents.managers;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.android.project3.data.TowerSaveData;
import com.android.project3.data.TowerType;
import com.android.project3.data.User;
import com.android.project3.gameComponents.TowerBar;
import com.android.project3.gameComponents.abstractComponents.Tower;
import com.android.project3.listeners.OnCoinsChangeListener;
import com.android.project3.utils.GameValues;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class TowerManager {

    private ArrayList<Tower> towerArrayList;
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
        towerSaveDataArrayList.forEach(towerSaveData -> {
            towerArrayList.add(towerFactory.createTower(TowerType.valueOf(towerSaveData.getType()), towerSaveData.getPosition().x, towerSaveData.getPosition().y));
            towerArrayList.get(towerArrayList.size()-1).loadUpgrades(0, towerSaveData.getPathOneLevel());
            towerArrayList.get(towerArrayList.size()-1).loadUpgrades(1, towerSaveData.getPathTwoLevel());
        });
        GameValues.coinsChangeListenerArrayList.forEach(OnCoinsChangeListener::onCoinsChange);
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
        ArrayList<Tower> protectiveTowerArrayList = new ArrayList<>(towerArrayList); // to avoid ConcurrentModificationException, make a copy of a list that doesn't change
        protectiveTowerArrayList.forEach(tower -> tower.draw(canvas));
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
