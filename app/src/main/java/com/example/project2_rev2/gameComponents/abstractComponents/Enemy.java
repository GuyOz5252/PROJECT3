package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public abstract class Enemy extends BitmapObject {

    private final EnemyPath enemyPath;
    private int nextPathDestinationIndex;
    private Position nextPathDestination;

    private final int SPEED;
    private int velocityX;
    private int velocityY;

    private boolean isAlive;
    private boolean needRotation;

    private final Bitmap originalBitmap;

    private int health;

    public Enemy(int resourceId, int speed, Size size, EnemyPath enemyPath, int health, Context context) {
        super(enemyPath.getPositionArrayList().get(0).x-size.width/2, enemyPath.getPositionArrayList().get(0).y-size.height/2, resourceId, size, context);
        this.SPEED = speed;
        this.enemyPath = enemyPath;
        this.nextPathDestinationIndex = 1;
        this.nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
        this.isAlive = true;
        this.needRotation = false;
        this.originalBitmap = bitmap;
        this.health = health;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public boolean moveToPosition(Position position) {
        if (centerPosition.x < position.x) {
            velocityX = SPEED;
            velocityY = 0;
            handleEnemyRotation();
            return false;
        }
        if (centerPosition.y != position.y) {
            if (centerPosition.y > position.y) {
                velocityX = 0;
                velocityY = -SPEED;
            }
            if (centerPosition.y < position.y) {
                velocityX = 0;
                velocityY = SPEED;
                handleEnemyRotation();
                return Math.abs(position.y - centerPosition.y) < SPEED;
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

    public void receiveDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }

    @Override
    public void update() {
        super.update();
        if (isAlive) {
            followPath();
            movement();
        }
    }

    public enum EnemyTypes {
        DEMO_ENEMY
    }
}
