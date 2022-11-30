package com.example.project2_rev2.data;

import android.graphics.Rect;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.google.firebase.firestore.core.UserData;

import java.util.ArrayList;

public class SaveData {

    private SaveData saveData;

    private int sceneIndex;
    private int currentWave;
    private int money;
    private int health;
    private ArrayList<Tower> towerArrayList;
    private ArrayList<Rect> colliderArrayList;

    private SaveData() {}

    public void setSaveData(int sceneIndex, int currentWave, int money, int health, ArrayList<Tower> towerArrayList, ArrayList<Rect> colliderArrayList) {
        this.sceneIndex = sceneIndex;
        this.currentWave = currentWave;
        this.money = money;
        this.health = health;
        this.towerArrayList = towerArrayList;
        this.colliderArrayList = colliderArrayList;
    }

    public void setSaveData(SaveData saveData) {
        this.saveData = saveData;
    }

    public SaveData getInstance() {
        if (saveData == null) {
            return new SaveData();
        }
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

    public ArrayList<Tower> getTowerArrayList() {
        return towerArrayList;
    }

    public ArrayList<Rect> getColliderArrayList() {
        return colliderArrayList;
    }
}
