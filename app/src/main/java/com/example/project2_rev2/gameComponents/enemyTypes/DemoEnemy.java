package com.example.project2_rev2.gameComponents.enemyTypes;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.abstractComponents.Enemy;
import com.example.project2_rev2.utils.Size;

public class DemoEnemy extends Enemy {

    public DemoEnemy(EnemyPath enemyPath, Context context) {
        super(R.drawable.ic_launcher_background, 3, new Size(100, 100), enemyPath, 1, context);
    }
}
