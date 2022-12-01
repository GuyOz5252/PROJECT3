package com.example.project2_rev2.data;

import android.graphics.Rect;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;
import java.util.List;

public class SaveData {

    private static SaveData saveData = new SaveData();

    private int sceneIndex;
    private int currentWave;
    private int money;
    private int health;
    private ArrayList<Tower> towerList;
    private ArrayList<Rect> colliderList;

    private SaveData() {}

    public void setSaveData(int sceneIndex, int currentWave, int money, int health, ArrayList<Tower> towerList, ArrayList<Rect> colliderList) {
        this.sceneIndex = sceneIndex;
        this.currentWave = currentWave;
        this.money = money;
        this.health = health;
        this.towerList = towerList;
        this.colliderList = colliderList;
    }

    public void setSaveData(SaveData saveData) {
        SaveData.saveData = saveData;
    }

    public static SaveData getInstance() {
        return saveData;
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

    public List<Tower> getTowerList() {
        return towerList;
    }

    public List<Rect> getColliderList() {
        return colliderList;
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "sceneIndex=" + sceneIndex +
                ", currentWave=" + currentWave +
                ", money=" + money +
                ", health=" + health +
                ", towerArrayList=" + towerList +
                ", colliderArrayList=" + colliderList +
                '}';
    }
}
