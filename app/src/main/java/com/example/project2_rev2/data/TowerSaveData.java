package com.example.project2_rev2.data;

import android.graphics.Rect;

import com.example.project2_rev2.utils.Position;

public class TowerSaveData {

    private String type;
    private Position position;
    private int pathOneLevel;
    private int pathTwoLevel;
    private Rect collider;

    public TowerSaveData() {} // firestore object mapper

    public TowerSaveData(String type, Position position, int pathOneLevel, int pathTwoLevel, Rect collider) {
        this.type = type;
        this.position = position;
        this.pathOneLevel = pathOneLevel;
        this.pathTwoLevel = pathTwoLevel;
        this.collider = collider;
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

    public Rect getCollider() {
        return new Rect(
                collider.left,
                collider.top,
                collider.right,
                collider.bottom
        );
    }
}
