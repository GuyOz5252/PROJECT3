package com.example.project2_rev2.gameComponents.enemyTypes;

import android.content.Context;

import com.example.project2_rev2.data.EnemyType;
import com.example.project2_rev2.gameComponents.Enemy;
import com.example.project2_rev2.gameComponents.EnemyPath;

public class CamoDemoEnemy extends Enemy {

    public CamoDemoEnemy(EnemyType enemyType, EnemyPath enemyPath, Context context) {
        super(enemyType, enemyPath, context);
    }

    @Override
    public boolean getIsCamo() {
        return true;
    }
}
