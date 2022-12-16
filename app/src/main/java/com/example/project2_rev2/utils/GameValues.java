package com.example.project2_rev2.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

import com.example.project2_rev2.data.User;
import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.listeners.OnHealthChangeListener;

import java.util.ArrayList;

public class GameValues {

    public final static Display gameDisplay = new Display(1916, 1080);

    public static Display display = new Display(0, 0);

    public static double xOffset = 0;

    public static double yOffset = 0;

    public static double xCoordinate(double value) {
        return value + xOffset;
    }

    public static double yCoordinate(double value) {
        return value + yOffset;
    }

    public static ArrayList<Rect> colliderArrayList = new ArrayList<>();

    public static boolean isPaused = false;

    public static boolean isFastForwarded = false;

    public static boolean isFinished = false;

    public final static int START_COINS = 650;

    private static int playerCoins = START_COINS;

    public final static int START_HEALTH = 150;

    private static int playerHealth = START_HEALTH;

    /**player coins**/
    public static ArrayList<OnCoinsChangeListener> coinsChangeListenerArrayList = new ArrayList<>();

    public static int getPlayerCoins() {
        return playerCoins;
    }

    public static void setPlayerCoins(int playerCoins) {
        if (playerCoins > GameValues.playerCoins) {
            User.getInstance().getPlayerStats().setMoneyEarned(User.getInstance().getPlayerStats().getMoneyEarned() + playerCoins-GameValues.getPlayerCoins());
        }
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

    public static void init(Activity activity, Display display) {
        GameValues.display.size.width = display.size.width;
        GameValues.display.size.width += activity.getResources().getDimensionPixelSize(activity.getResources().getIdentifier("navigation_bar_width", "dimen", "android"));
        GameValues.display.size.height = display.size.height;
        xOffset = (GameValues.display.size.width - gameDisplay.size.width)/2;
        yOffset = (GameValues.display.size.height - gameDisplay.size.height)/2;
        isPaused = false;
        isFastForwarded = false;
        isFinished = false;
        playerCoins = START_COINS;
        playerHealth = START_HEALTH;
        colliderArrayList.clear();
        coinsChangeListenerArrayList.clear();
        healthChangeListenerArrayList.clear();
    }
}
