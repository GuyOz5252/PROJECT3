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

    public ArrayList<Tower> getTowerArrayList() {
        return towerArrayList;
    }

    public boolean getIsAnyTowerSelected() {
        return isAnyTowerSelected;
    }

    public void setTowerArrayList(ArrayList<TowerSaveData> towerSaveDataArrayList) {
        towerSaveDataArrayList.forEach(towerSaveData -> {
            switch (towerSaveData.getType()) {
                case "DEMO_TOWER":
                    towerArrayList.add(new DemoTower(xCoordinate(towerSaveData.getPosition().x), yCoordinate(towerSaveData.getPosition().y), towerSaveData.getCollider(), towerBar, waveManager, projectileManager, context));
                    break;
                case "TURRET":
                    towerArrayList.add(new Turret(xCoordinate(towerSaveData.getPosition().x), yCoordinate(towerSaveData.getPosition().y), towerSaveData.getCollider(), towerBar, waveManager, projectileManager, context));
                    break;
                case "FIRE_SPREADER":
                    towerArrayList.add(new FireSpreader(xCoordinate(towerSaveData.getPosition().x), yCoordinate(towerSaveData.getPosition().y), towerSaveData.getCollider(), towerBar, waveManager, projectileManager, context));
                    break;
            }
            towerArrayList.get(towerArrayList.size()-1).loadUpgrades(0, towerSaveData.getPathOneLevel());
            towerArrayList.get(towerArrayList.size()-1).loadUpgrades(1, towerSaveData.getPathTwoLevel());

            GameValues.colliderArrayList.add(getGameCoordinatesRect(towerSaveData.getCollider()));
        });
    }

    public void addTower(TowerType towerType, double x, double y) {
        Rect collider = null;
        switch (towerType) {
            case DEMO_TOWER:
                collider = new Rect(
                        (int)(x-towerType.size.width/2),
                        (int)(y-towerType.size.height/2),
                        (int)(x+towerType.size.width/2),
                        (int)(y+towerType.size.height/2)
                );
                towerArrayList.add(new DemoTower(x, y, collider, towerBar, waveManager, projectileManager, context));
                break;
            case TURRET:
                collider = new Rect(
                        (int)(x-towerType.size.width/2)+35,
                        (int)(y-towerType.size.height/2)+30,
                        (int)(x+towerType.size.width/2)-25,
                        (int)(y+towerType.size.height/2)-30
                );
                towerArrayList.add(new Turret(x, y, collider, towerBar, waveManager, projectileManager, context));
                break;
            case FIRE_SPREADER:
                collider = new Rect(
                        (int)(x-towerType.size.width/2),
                        (int)(y-towerType.size.height/2),
                        (int)(x+towerType.size.width/2),
                        (int)(y+towerType.size.height/2)
                );
                towerArrayList.add(new FireSpreader(x, y, collider, towerBar, waveManager, projectileManager, context));
                break;
        }
        GameValues.colliderArrayList.add(collider);
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
