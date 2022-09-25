package com.example.project2_rev2.gameComponents.enemyTypes;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.abstractComponents.EnemyUnit;
import com.example.project2_rev2.utils.Size;

public class DemoEnemy extends EnemyUnit {

    public DemoEnemy(EnemyPath enemyPath, Context context) {
        super(R.drawable.ic_launcher_background, 5, new Size(100, 100), enemyPath, 1, context);
    }
}
