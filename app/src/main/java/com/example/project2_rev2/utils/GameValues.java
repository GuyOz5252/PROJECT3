package com.example.project2_rev2.utils;

import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.listeners.OnHealthChangeListener;

import java.util.ArrayList;

public class GameValues {

    public final static Display gameDisplay = new Display(1916, 1076);

    public static Display display = new Display(0, 0);

    public static double xOffset = 0;
    public static double yOffset = 0;

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

    public final static int START_HEALTH = 150;

    private static int playerHealth = START_HEALTH;

    /**player coins**/
    public static ArrayList<OnCoinsChangeListener> coinsChangeListenerArrayList = new ArrayList<>();

    public static int getPlayerCoins() {
        return playerCoins;
    }

    public static void setPlayerCoins(int playerCoins) {
        GameValues.playerCoins = playerCoins;
        coinsChangeListenerArrayList.forEach(OnCoinsChangeListener::onCoinsChange);
    }

    /**player health**/
    public static ArrayList<OnHealthChangeListener> healthChangeListenerArrayList = new ArrayList<>();

    public static int getPlayerHealth() {
        return playerHealth;
    }

    public static void setPlayerHealth(int playerHealth) {
        GameValues.playerHealth = playerHealth;
        healthChangeListenerArrayList.forEach(OnHealthChangeListener::onHealthChange);
    }

    public static void init(Display display) {
        GameValues.display.size.width = display.size.width;
        GameValues.display.size.height = display.size.height;
        if (display.size.width > gameDisplay.size.width) {
            xOffset = (display.size.width - gameDisplay.size.width)/2;
            yOffset = (display.size.height - gameDisplay.size.height)/2;
        }
        isPaused = false;
        isFastForwarded = false;
        playerCoins = START_COINS;
        playerHealth = START_HEALTH;
    }
}
