package com.example.project2_rev2.data;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class PlayerStats {

    private double gamesPlayed;
    private double gamesWon;
    private double gamesLost;
    private double towersPlaced;
    private double enemiesKilled;
    private double moneyEarned;
    private double xpEarned;

    public PlayerStats() {} // firestore object mapper

    @Exclude
    public ArrayList<Object[]> getAllStats() {
        ArrayList<Object[]> playerStatsArrayList = new ArrayList<>();
        playerStatsArrayList.add(new Object[]{"Games Played", gamesPlayed});
        playerStatsArrayList.add(new Object[]{"Games Won", gamesWon});
        playerStatsArrayList.add(new Object[]{"Games Lost", gamesLost});
        playerStatsArrayList.add(new Object[]{"Towers Placed", towersPlaced});
        playerStatsArrayList.add(new Object[]{"Enemies Killed", enemiesKilled});
        playerStatsArrayList.add(new Object[]{"Money Earned", moneyEarned});
        playerStatsArrayList.add(new Object[]{"XP Earned", xpEarned});
        return playerStatsArrayList;
    }

    public double getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(double gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public double getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(double gamesWon) {
        this.gamesWon = gamesWon;
    }

    public double getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(double gamesLost) {
        this.gamesLost = gamesLost;
    }

    public double getTowersPlaced() {
        return towersPlaced;
    }

    public void setTowersPlaced(double towersPlaced) {
        this.towersPlaced = towersPlaced;
    }

    public double getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(double enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public double getMoneyEarned() {
        return moneyEarned;
    }

    public void setMoneyEarned(double moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public double getXpEarned() {
        return xpEarned;
    }

    public void setXpEarned(double xpEarned) {
        this.xpEarned = xpEarned;
    }
}
