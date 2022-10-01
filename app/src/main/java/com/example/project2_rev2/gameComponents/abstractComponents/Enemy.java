package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.EnemyPath;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

import static com.example.project2_rev2.utils.HelperMethods.rotateBitmap;

public class Enemy extends BitmapObject {

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

    public Enemy(EnemyTypes enemyType, EnemyPath enemyPath, Context context) {
        super(
                enemyPath.getPositionArrayList().get(0).x-enemyType.size.width/2,
                enemyPath.getPositionArrayList().get(0).y-enemyType.size.height/2,
                enemyType.resourceId,
                enemyType.size,
                context
        );
        this.SPEED = enemyType.speed;
        this.enemyPath = enemyPath;
        this.nextPathDestinationIndex = 1;
        this.nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
        this.isAlive = true;
        this.needRotation = false;
        this.originalBitmap = bitmap;
        this.health = enemyType.health;
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
                return Math.abs(position.y - centerPosition.y) < SPEED * Scene.speedMultiplier;
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
        position.x += velocityX * Scene.speedMultiplier;
        position.y += velocityY * Scene.speedMultiplier;
    }

    public void handleEnemyRotation() {
        if (needRotation) {
            if (velocityX > 0) {
                bitmap = originalBitmap;
            }
            if (velocityY > 1) {
                bitmap = rotateBitmap(originalBitmap, 90);
            }
            if (velocityY < -1) {
                bitmap = rotateBitmap(originalBitmap, -90);
            }
            needRotation = false;
        }
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
        DEMO_ENEMY(R.drawable.ic_launcher_background, 3, new Size(100, 100), 1, 1);

        public int resourceId;
        public int speed;
        public Size size;
        public int health;
        public int damage;

        EnemyTypes(int resourceId, int speed, Size size, int health, int damage) {
            this.resourceId = resourceId;
            this.speed = speed;
            this.size = size;
            this.health = health;
            this.damage = damage;
        }
    }
}
