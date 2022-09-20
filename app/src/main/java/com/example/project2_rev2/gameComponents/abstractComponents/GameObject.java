package com.example.project2_rev2.gameComponents.abstractComponents;

import android.graphics.Canvas;

import com.example.project2_rev2.utils.Position;

public abstract class GameObject {

    protected Position position;

    public GameObject(double x, double y) {
        this.position = new Position(x, y);
    }

    public abstract void update();
    public abstract void draw(Canvas canvas);

    public void setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Position getPosition() {
        return position;
    }
}
