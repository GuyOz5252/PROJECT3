package com.android.project3.data;

import android.util.Pair;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class PlayerStats {

    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int towersPlaced;
    private int enemiesKilled;
    private int moneyEarned;
    private int xpEarned;

    public PlayerStats() {} // firestore object mapper

    @Exclude
    public ArrayList<Pair<String, Integer>> getAllStats() {
        ArrayList<Pair<String, Integer>> playerStatsArrayList = new ArrayList<>();
        playerStatsArrayList.add(new Pair<>("Games Played", gamesPlayed));
        playerStatsArrayList.add(new Pair<>("Games Won", gamesWon));
        playerStatsArrayList.add(new Pair<>("Games Lost", gamesLost));
        playerStatsArrayList.add(new Pair<>("Towers Placed", towersPlaced));
        playerStatsArrayList.add(new Pair<>("Enemies Killed", enemiesKilled));
        playerStatsArrayList.add(new Pair<>("Money Earned", moneyEarned));
        playerStatsArrayList.add(new Pair<>("XP Earned", xpEarned));
        return playerStatsArrayList;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getTowersPlaced() {
        return towersPlaced;
    }

    public void setTowersPlaced(int towersPlaced) {
        this.towersPlaced = towersPlaced;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public int getMoneyEarned() {
        return moneyEarned;
    }

    public void setMoneyEarned(int moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public int getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(int xpEarned) {
        this.xpEarned = xpEarned;
    }
}
