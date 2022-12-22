package com.example.project2_rev2.utils;

import androidx.annotation.Nullable;

public class Position {

    public double x;
    public double y;

    public Position() {}

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Position position = (Position)obj;
        return this.x == position.x && this.y == position.y;
    }

    public Position toDisplayCoordinates() {
        this.x -= GameValues.xOffset;
        this.y -= GameValues.yOffset;
        return this;
    }
}
