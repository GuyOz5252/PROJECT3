package com.android.project3.data;

import com.android.project3.utils.Position;

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
