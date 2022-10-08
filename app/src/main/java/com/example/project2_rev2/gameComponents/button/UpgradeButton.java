package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.utils.Size;

public class UpgradeButton extends Button {

    private int upgradePathIndex;
    private Tower tower;

    public UpgradeButton(double y, int upgradeIndex, Tower tower, Context context) {
        super(xCoordinate(30), y, R.drawable.ic_launcher_background, new Size(290, 150), context);
        this.upgradePathIndex = upgradeIndex;
        this.tower = tower;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent) && motionEvent.getAction() == MotionEvent.ACTION_UP) {
            return tower.upgrade(upgradePathIndex);
        }
        return false;
    }
}
