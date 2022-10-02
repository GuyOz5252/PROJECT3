package com.example.project2_rev2.utils;

public class GaveValues {

    public final static Display gameDisplay = new Display(1916, 1076);

    public static Display display = new Display(0, 0); // value given in GameView

    public static double xOffset = 0; // value given in GameView
    public static double yOffset = 0; // value given in GameView

    public static double xCoordinate(double value) {
        return value + xOffset;
    }

    public static double yCoordinate(double value) {
        return value + yOffset;
    }
}
