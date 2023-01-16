package com.example.project2_rev2.data;

import android.graphics.Rect;

import com.example.project2_rev2.utils.Position;

public class TowerSaveData {

    private String type;
    private Position position;
    private int pathOneLevel;
    private int pathTwoLevel;

    public TowerSaveData() {} // firestore object mapper

    public TowerSaveData(String type, Position position, int pathOneLevel, int pathTwoLevel) {
        this.type = type;
        this.position = position;
        this.pathOneLevel = pathOneLevel;
        this.pathTwoLevel = pathTwoLevel;
    }

    public String getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public int getPathOneLevel() {
        return pathOneLevel;
    }

    public int getPathTwoLevel() {
        return pathTwoLevel;
    }
}
