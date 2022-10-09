package com.example.project2_rev2.utils;

import com.example.project2_rev2.listeners.OnCoinsChangeListener;

import java.util.ArrayList;

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

    private static int playerCoins = START_COINS;

    /**player coins**/
    public static ArrayList<OnCoinsChangeListener> coinsChangeListenerArrayList = new ArrayList<>();

    public static int getPlayerCoins() {
        return playerCoins;
    }

    public static void setPlayerCoins(int playerCoins) {
        GameValues.playerCoins = playerCoins;
        coinsChangeListenerArrayList.forEach(OnCoinsChangeListener::onCoinsChange);
    }
}
