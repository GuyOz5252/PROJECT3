package com.example.project2_rev2.gameComponents;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.listeners.OnHealthChangeListener;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

import static com.example.project2_rev2.utils.HelperMethods.rotateBitmap;

public class Enemy extends BitmapObject implements OnHealthChangeListener {

    private final EnemyPath enemyPath;
    private int nextPathDestinationIndex;
    private Position nextPathDestination;

    private boolean isMoving;
    private MovementDirection movementDirection;

    private final int SPEED;

    private boolean isAlive;
    private boolean needRotation;

    private final Bitmap originalBitmap;

    private int health;
    private int damage;
    private int value;

    private boolean isOnFire;
    private int damageOverTimeDurationTick;
    private int damageOverTimeTick;
    private int damageOverTimeDamage;
    private int damageOverTimeDuration;
    private int damageOverTimeInterval;
    private Tower damageOverTimeOriginTower;

    public Enemy(EnemyType enemyType, EnemyPath enemyPath, Context context) {
        super(
                enemyPath.getPositionArrayList().get(0).x-enemyType.size.width/2,
                enemyPath.getPositionArrayList().get(0).y-enemyType.size.height/2,
                enemyType.resourceId,
                enemyType.size,
                context
        );
        this.isMoving = false;
        this.SPEED = enemyType.speed;
        this.enemyPath = enemyPath;
        this.nextPathDestinationIndex = 0;
        this.nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
        this.isAlive = true;
        this.needRotation = false;
        this.originalBitmap = bitmap;
        this.health = enemyType.health;
        this.damage = enemyType.damage;
        this.value = enemyType.value;
        this.isOnFire = false;
        this.damageOverTimeDurationTick = 0;
        this.damageOverTimeTick = 0;
        this.damageOverTimeDamage = 0;
        this.damageOverTimeDuration = 0;
        this.damageOverTimeInterval = 0;
        this.damageOverTimeOriginTower = null;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public boolean getIsOnFire() {
        return isOnFire;
    }

    public void handlePathMovement() {
        isMoving = checkPosition();
        if (isMoving) {
            moveInDirection();
            handleEnemyRotation();
        } else {
            nextPathDestinationIndex++;
            if (nextPathDestinationIndex < enemyPath.getPositionArrayList().size()) {
                nextPathDestination = enemyPath.getPositionArrayList().get(nextPathDestinationIndex);
                needRotation = true;
            } else {
                isAlive = false;
                GameValues.setPlayerHealth(GameValues.getPlayerHealth() - this.damage);
            }
        }
    }

    private boolean checkPosition() { // after testing, decided to put the offsets in the if statements
        if (centerPosition.x < nextPathDestination.x-1) {
            movementDirection = MovementDirection.RIGHT;
            return true;
        }
        if (centerPosition.x > nextPathDestination.x+1) {
            movementDirection = MovementDirection.LEFT;
            return true;
        }
        if (centerPosition.y > nextPathDestination.y+2) {
            movementDirection = MovementDirection.UP;
            return true;
        }
        if (centerPosition.y < nextPathDestination.y-1) {
            movementDirection = MovementDirection.DOWN;
            return true;
        }
        return false;
    }

    public void moveInDirection() {
        switch (movementDirection) {
            case RIGHT:
                position.x += SPEED;
                break;
            case LEFT:
                position.x -= SPEED;
                break;
            case UP:
                position.y -= SPEED;
                break;
            case DOWN:
                position.y += SPEED;
                break;
        }
    }

    public void handleEnemyRotation() {
        if (needRotation) {
            switch (movementDirection) {
                case RIGHT:
                    bitmap = originalBitmap;
                    break;
                case LEFT:
                    bitmap = rotateBitmap(originalBitmap, 180);
                    break;
                case UP:
                    bitmap = rotateBitmap(originalBitmap, -90);
                    break;
                case DOWN:
                    bitmap = rotateBitmap(originalBitmap, 90);
                    break;
            }
            needRotation = false;
        }
    }

    public void die(Tower tower) {
        isAlive = false;
        //tower.setXP(tower.getXP() + );
        GameValues.setPlayerCoins(GameValues.getPlayerCoins() + value);
    }

    public void receiveDamage(int damage, Tower originTower) {
        health -= damage;
        if (health <= 0) {
            die(originTower);
        }
    }

    public void receiveDamageOverTime(int damage, int duration, int interval, Tower originTower) {
        if (damageOverTimeDurationTick <= duration+1) { // to get into the if for the last time in the duration, adding one
            if (damageOverTimeTick > duration/interval) {
                health -= damage;
                damageOverTimeTick = 2;
            } else {
                damageOverTimeTick++;
            }
            if (health <= 0) {
                die(originTower);
            }
            damageOverTimeDurationTick++;
        } else {
            isOnFire = false;
            damageOverTimeDurationTick = 0;
            damageOverTimeTick = 0;
            damageOverTimeDamage = 0;
            damageOverTimeDuration = 0;
            damageOverTimeInterval = 0;
            damageOverTimeOriginTower = null;
        }
    }

    public void setOnFire(int damage, int duration, int interval, Tower originTower) {
        damageOverTimeDurationTick = 0;
        damageOverTimeTick = 0;
        damageOverTimeDamage = damage;
        damageOverTimeDuration = duration;
        damageOverTimeInterval = interval;
        damageOverTimeOriginTower = originTower;
        isOnFire = true;
    }

    @Override
    public void onHealthChange() {

    }

    @Override
    public void update() {
        super.update();
        if (isAlive) {
            handlePathMovement();
        }

        if (isOnFire) {
            receiveDamageOverTime(damageOverTimeDamage, damageOverTimeDuration, damageOverTimeInterval, damageOverTimeOriginTower);
        }

        System.out.println(health);
    }

    private enum MovementDirection {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }

    public enum EnemyType {
        DEMO_ENEMY(R.drawable.ic_launcher_background, 3, new Size(85, 85), 30, 10, 7);

        public int resourceId;
        public int speed;
        public Size size;
        public int health;
        public int damage;
        public int value;

        EnemyType(int resourceId, int speed, Size size, int health, int damage, int value) {
            this.resourceId = resourceId;
            this.speed = speed;
            this.size = size;
            this.health = health;
            this.damage = damage;
            this.value = value;
        }
    }
}
