package com.example.project2_rev2.gameComponents.enemyTypes;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.EnemyType;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

public class ArmorDemoEnemy extends Enemy {

    private boolean isArmored;

    public ArmorDemoEnemy(EnemyType enemyType, EnemyPath enemyPath, Context context) {
        super(enemyType, enemyPath, context);
        this.isArmored = true;
    }

    public void removeArmor() {
        isArmored = false;
        changeBitmap(R.drawable.ic_bug);
        originalBitmap = bitmap;
        needRotation = true;
    }

    @Override
    public void receiveDamage(int damage, Tower originTower) {
        if (originTower.getProjectileType().isArmorPenetrating && isArmored) {
            removeArmor();
        } else if (!isArmored) {
            super.receiveDamage(damage, originTower);
        }
    }

    @Override
    public void receiveDamageOverTime(int damage, int duration, int interval, Tower originTower) {
        if (!isArmored) {
            super.receiveDamageOverTime(damage, duration, interval, originTower);
        }
    }
}
