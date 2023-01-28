package com.android.project3.data;

import com.android.project3.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;

public class SaveData {

    private int sceneIndex;
    private int currentWave;
    private int money;
    private int health;
    private ArrayList<TowerSaveData> towerArrayList;
    private boolean isFinished;
    private boolean isActive;

    public SaveData() {} // firestore object mapper

    public SaveData(int sceneIndex, int currentWave, int money, int health, ArrayList<Tower> towerList, boolean isFinished, boolean isActive) {
        this.sceneIndex = sceneIndex;
        this.currentWave = currentWave;
        this.money = money;
        this.health = health;
        this.towerArrayList = new ArrayList<>();
        for (Tower tower : towerList) {
            this.towerArrayList.add(new TowerSaveData(
                    tower.getTowerType().name(),
                    tower.getCenterPosition(),
                    tower.getPathLevels()[0],
                    tower.getPathLevels()[1]
            ));
        }
        this.isFinished = isFinished;
        this.isActive = isActive;
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

    public ArrayList<TowerSaveData> getTowerArrayList() {
        return towerArrayList;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean b) {
        isActive = b;
    }
}
