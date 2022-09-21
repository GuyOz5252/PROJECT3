package com.example.project2_rev2.gameComponents;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

import java.util.Timer;
import java.util.TimerTask;

public class EnemyUnit extends BitmapObject {

    private EnemyPath enemyPath;
    private int nextPathDestinationIndex;
    private Position nextPathDestination;

    private final int MAX_SPEED = 3;
    private int velocityX;
    private int velocityY;

    public EnemyUnit(double posX, double posY, int resourceId, Size size, EnemyPath enemyPath, Context context) {
        super(enemyPath.getPositionArrayList().get(0).x-size.width/2, enemyPath.getPositionArrayList().get(0).y-size.height/2, resourceId, size, context);
        this.enemyPath = enemyPath;
        this.nextPathDestinationIndex = 1;
        this.nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
    }

    public boolean moveToPosition(Position position) {
        if (pivotPosition.x < position.x) {
            velocityX = MAX_SPEED;
            velocityY = 0;
            return false;
        }
        if (pivotPosition.y != position.y) {
            if (pivotPosition.y > position.y) {
                velocityX = 0;
                velocityY = -MAX_SPEED;
            }
            if (pivotPosition.y < position.y) {
                velocityX = 0;
                velocityY = MAX_SPEED;
            }
            return false;
        }
        velocityX = 0;
        velocityY = 0;
        return true;
    }

    public void followPath() {
        boolean advancePath = true;
        while (advancePath) {
            advancePath = moveToPosition(nextPathDestination);
            if (advancePath) {
                nextPathDestinationIndex++;
                if (nextPathDestinationIndex < enemyPath.getPositionArrayList().size()) {
                    nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
                }
            }
        }
    }

    public void movement() {
        position.x += velocityX;
        position.y += velocityY;
    }

    @Override
    public void update() {
        super.update();
        followPath();
        movement();
    }
}
