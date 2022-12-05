package com.example.project2_rev2.data;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;

public class SaveData {

    private int sceneIndex;
    private int currentWave;
    private int money;
    private int health;
    private ArrayList<TowerSaveData> towerArrayList;
    private boolean isActive;

    public SaveData() {} // firestore object mapper

    public SaveData(int sceneIndex, int currentWave, int money, int health, ArrayList<Tower> towerList, boolean isActive) {
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
                    tower.getPathLevels()[1],
                    tower.getCollider()
            ));
        }
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean b) {
        isActive = b;
    }

    @Override
    public String toString() {
        return "SaveData{" +
                "sceneIndex=" + sceneIndex +
                ", currentWave=" + currentWave +
                ", money=" + money +
                ", health=" + health +
                ", towerArrayList=" + towerArrayList +
                '}';
    }
}
