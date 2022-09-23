package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public class EnemyUnit extends BitmapObject {

    private final EnemyPath enemyPath;
    private int nextPathDestinationIndex;
    private Position nextPathDestination;

    private int SPEED;
    private int velocityX;
    private int velocityY;

    private boolean isAlive;
    private boolean needRotation;

    private Bitmap originalBitmap;
    //private Bitmap rotatedBitmap;

    public EnemyUnit(int resourceId, int speed, Size size, EnemyPath enemyPath, Context context) {
        super(enemyPath.getPositionArrayList().get(0).x-size.width/2, enemyPath.getPositionArrayList().get(0).y-size.height/2, resourceId, size, context);
        this.SPEED = speed;
        this.enemyPath = enemyPath;
        this.nextPathDestinationIndex = 1;
        this.nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
        this.isAlive = true;
        this.needRotation = false;
        this.originalBitmap = bitmap;
        //this.rotatedBitmap = bitmap;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public boolean moveToPosition(Position position) {
        if (pivotPosition.x < position.x) {
            velocityX = SPEED;
            velocityY = 0;
            handleEnemyRotation();
            return false;
        }
        if (pivotPosition.y != position.y) {
            if (pivotPosition.y > position.y) {
                velocityX = 0;
                velocityY = -SPEED;
            }
            if (pivotPosition.y < position.y) {
                velocityX = 0;
                velocityY = SPEED;
                handleEnemyRotation();
                return Math.abs(position.y - pivotPosition.y) < SPEED;
            }
            handleEnemyRotation();
            return false;
        }
        return true;
    }

    public void followPath() {
        boolean advancePath = true;
        while (advancePath) {
            advancePath = moveToPosition(nextPathDestination);
            if (advancePath) {
                velocityX = 0;
                velocityY = 0;
                nextPathDestinationIndex++;
                if (nextPathDestinationIndex < enemyPath.getPositionArrayList().size()) {
                    nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
                    needRotation = true;
                } else {
                    isAlive = false;
                    advancePath = false;
                }
            }
        }
    }

    public void movement() {
        position.x += velocityX;
        position.y += velocityY;
    }

    public void handleEnemyRotation() {
        if (needRotation) {
            if (velocityX > 0) {
                rotateEnemy(0);
            }
            if (velocityY > 1) {
                rotateEnemy(90);
            }
            if (velocityY < -1) {
                rotateEnemy(-90);
            }
            needRotation = false;
        }
    }

    public void rotateEnemy(int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        bitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
    }

    @Override
    public void update() {
        super.update();
        if (isAlive) {
            followPath();
            movement();
        }
    }
}