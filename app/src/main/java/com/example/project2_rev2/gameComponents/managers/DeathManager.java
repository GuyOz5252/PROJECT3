package com.example.project2_rev2.gameComponents.managers;

import android.app.Activity;
import android.content.Context;

import com.example.project2_rev2.Action;
import com.example.project2_rev2.listeners.OnHealthChangeListener;
import com.example.project2_rev2.utils.GameValues;

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
            ((Activity)context).runOnUiThread(() -> death.action());
        }
    }
}
