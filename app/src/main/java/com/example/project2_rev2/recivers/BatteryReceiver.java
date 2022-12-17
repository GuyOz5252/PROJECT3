package com.example.project2_rev2.recivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.project2_rev2.gameStructure.GameView;

public class BatteryReceiver extends BroadcastReceiver {

    private GameView gameView;

    public BatteryReceiver(GameView gameView) {
        this.gameView = gameView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        gameView.createDeathDialog();
    }
}
