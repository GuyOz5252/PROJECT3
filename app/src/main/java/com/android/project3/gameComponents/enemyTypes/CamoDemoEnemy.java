package com.android.project3.gameComponents.enemyTypes;

import android.content.Context;

import com.android.project3.data.EnemyType;
import com.android.project3.gameComponents.Enemy;
import com.android.project3.gameComponents.EnemyPath;

public class CamoDemoEnemy extends Enemy {

    public CamoDemoEnemy(EnemyType enemyType, EnemyPath enemyPath, Context context) {
        super(enemyType, enemyPath, context);
    }

    @Override
    public boolean getIsCamo() {
        return true;
    }
}
