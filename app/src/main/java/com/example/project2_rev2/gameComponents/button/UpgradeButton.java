package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GaveValues.xCoordinate;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.utils.Size;

public class UpgradeButton extends Button {

    private int upgradeIndex;
    private Tower tower;

    public UpgradeButton(double y, int upgradeIndex, Context context) {
        super(xCoordinate(30), y, R.drawable.ic_launcher_background, new Size(290, 150), context);
        this.upgradeIndex = upgradeIndex;
    }

    public void setTower(Tower tower) {
        this.tower = tower;
    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            int i = tower.upgrade(upgradeIndex);
            switch (i) {
                case 1:
                    changeBitmap(R.drawable.google);
                    break;
                case 2:
                    changeBitmap(R.drawable.ic_email_unfocused);
                    break;
                case 3:
                    changeBitmap(R.drawable.ic_lock_focused);
                    break;
            }
        }
    }
}
