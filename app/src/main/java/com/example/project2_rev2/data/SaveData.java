package com.example.project2_rev2.data;

import android.graphics.Rect;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;
import java.util.List;

public class SaveData {

    private int sceneIndex;
    private int currentWave;
    private int money;
    private int health;
    private ArrayList<Rect> colliderArrayList;
    private ArrayList<TowerSaveData> towerArrayList;

    public SaveData() {} // firestore object mapper

    public SaveData(int sceneIndex, int currentWave, int money, int health, ArrayList<Rect> colliderList, ArrayList<Tower> towerList) {
        this.sceneIndex = sceneIndex;
        this.currentWave = currentWave;
        this.money = money;
        this.health = health;
        this.colliderArrayList = colliderList;
        this.towerArrayList = new ArrayList<>();
        for (Tower tower : towerList) {
            this.towerArrayList.add(new TowerSaveData(
                    tower.getTowerType().name(),
                    tower.getCenterPosition(),
                    tower.getPathLevels()[0],
                    tower.getPathLevels()[1],
                    tower.getCollider()
            ));
        }
    }

    public int getSceneIndex() {
        return sceneIndex;
    }

    public int getCurrentWave() {
        return currentWave;
    }

    public int getMoney() {
        return money;
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<Rect> getColliderArrayList() {
        return colliderArrayList;
    }

    public ArrayList<TowerSaveData> getTowerArrayList() {
        return towerArrayList;
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "sceneIndex=" + sceneIndex +
                ", currentWave=" + currentWave +
                ", money=" + money +
                ", health=" + health +
                ", towerArrayList=" + towerArrayList +
                ", colliderArrayList=" + colliderArrayList +
                '}';
    }
}
