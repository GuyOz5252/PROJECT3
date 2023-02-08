package com.android.project3.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

import com.android.project3.listeners.OnCoinsChangeListener;
import com.android.project3.listeners.OnHealthChangeListener;
import com.android.project3.utils.Display;

import java.util.ArrayList;

public class GameValues {

    public final static Display gameDisplay = new Display(1916, 1080);

    public static Display display = new Display(0, 0);

    public static float scaleFactor = 1;

    public static ArrayList<Rect> colliderArrayList = new ArrayList<>();

    public static boolean isPaused = false;

    public static boolean isFastForwarded = false;

    public static boolean isFinished = false;

    private static int playerCoins = Constants.START_COINS;

    private static int playerHealth = Constants.START_HEALTH;

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
        GameValues.display.size.height = display.size.height;
        if (getNavbarMode(activity) == 2) {
            GameValues.display.size.height += activity.getResources().getDimensionPixelSize(activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android"));
        } else {
            GameValues.display.size.width += activity.getResources().getDimensionPixelSize(activity.getResources().getIdentifier("navigation_bar_width", "dimen", "android"));
        }
        scaleFactor = (float)((GameValues.display.size.height)/GameValues.gameDisplay.size.height);
        isPaused = false;
        isFastForwarded = false;
        isFinished = false;
        playerCoins = Constants.START_COINS;
        playerHealth = Constants.START_HEALTH;
        colliderArrayList.clear();
        coinsChangeListenerArrayList.clear();
        healthChangeListenerArrayList.clear();
    }

    public static int getNavbarMode(Context context) {
        int resourceId = context.getResources().getIdentifier("config_navBarInteractionMode", "integer", "android");
        return resourceId > 0 ? context.getResources().getInteger(resourceId) : 0;
    }
}
