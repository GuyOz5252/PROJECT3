package com.example.project2_rev2.utils;

public class GameValues {

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

    public static boolean isPaused = false;

    public static boolean isFastForwarded = false;

    public final static int START_COINS = 800;

    public static int playerCoins = START_COINS;

}
