package com.example.project2_rev2.gameComponents;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.utils.Size;

public class EnemyUnit extends BitmapObject {

    public EnemyUnit(double posX, double posY, int resourceId, Size size, Context context) {
        super(posX, posY, R.drawable.ic_launcher_background, size, context);
    }

    @Override
    public void update() {

    }
}
