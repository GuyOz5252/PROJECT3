package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.HelperMethods.getHypoDistance;

import android.content.Context;
import android.graphics.Canvas;

import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.utils.Size;

public class Tower extends BitmapObject {

    private int range;

    public Tower(double x, double y, int resourceId, Size size, Context context) {
        super(x, y, resourceId, size, context);
    }

    public void attack(EnemyUnit enemyUnit) {
        if (getHypoDistance(centerPosition.x, centerPosition.y, enemyUnit.getCenterPosition().x, enemyUnit.getCenterPosition().y) < range) {
            System.out.println("shoot");
        }
    }

    @Override
    public void update() {
        super.update();
    }
}
