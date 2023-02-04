package com.android.project3.gameComponents.managers;

import android.app.Activity;
import android.content.Context;

import com.android.project3.utils.Action;
import com.android.project3.listeners.OnHealthChangeListener;
import com.android.project3.data.GameValues;

public class DeathManager implements OnHealthChangeListener {

    private Context context;
    private Action death;
    private boolean isDead;

    public DeathManager(Action death, Context context) {
        GameValues.healthChangeListenerArrayList.add(this);
        this.context = context;
        this.death = death;
        this.isDead = false;
    }

    @Override
    public void onHealthChange() {
        if (GameValues.getPlayerHealth() <= 0) {
            isDead = true;
        }
    }

    public void update() {
        if (isDead && !((Activity)context).isFinishing()) {
            isDead = false;
            GameValues.isFinished = true;
            ((Activity)context).runOnUiThread(() -> death.action());
        }
    }
}
