package com.android.project3.gameComponents;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.project3.data.EnemyType;
import com.android.project3.data.User;
import com.android.project3.gameComponents.abstractComponents.BitmapObject;
import com.android.project3.gameComponents.abstractComponents.Tower;
import com.android.project3.gameComponents.enemyTypes.ArmorEnemy;
import com.android.project3.gameComponents.enemyTypes.CamoEnemy;
import com.android.project3.data.GameValues;
import com.android.project3.utils.Position;

import static com.android.project3.utils.HelperMethods.rotateBitmap;

public class Enemy extends BitmapObject {

    protected final EnemyPath enemyPath;
    protected int nextPathDestinationIndex;
    protected Position nextPathDestination;

    protected boolean isMoving;
    protected MovementDirection movementDirection;

    protected final int SPEED;

    protected boolean isAlive;
    protected boolean needRotation;

    protected Bitmap originalBitmap;

    protected int health;
    protected int damage;
    protected int value;

    protected boolean isOnFire;
    protected int damageOverTimeDurationTick;
    protected int damageOverTimeTick;
    protected int damageOverTimeDamage;
    protected int damageOverTimeDuration;
    protected int damageOverTimeInterval;
    protected Tower damageOverTimeOriginTower;

    protected Enemy(EnemyType enemyType, EnemyPath enemyPath, Context context) {
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

    public boolean getIsCamo() {
        return false;
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
        if (centerPosition.x < nextPathDestination.x-4) {
            movementDirection = MovementDirection.RIGHT;
            return true;
        }
        if (centerPosition.x > nextPathDestination.x+4) {
            movementDirection = MovementDirection.LEFT;
            return true;
        }
        if (centerPosition.y > nextPathDestination.y+4) {
            movementDirection = MovementDirection.UP;
            return true;
        }
        if (centerPosition.y < nextPathDestination.y-4) {
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
                    //bitmap = originalBitmap;
                    bitmap = rotateBitmap(originalBitmap, 90);
                    break;
                case LEFT:
                    //bitmap = rotateBitmap(originalBitmap, 180);
                    bitmap = rotateBitmap(originalBitmap, 270);
                    break;
                case UP:
                    //bitmap = rotateBitmap(originalBitmap, -90);
                    bitmap = originalBitmap;
                    break;
                case DOWN:
                    //bitmap = rotateBitmap(originalBitmap, 90);
                    bitmap = rotateBitmap(originalBitmap, 180);
                    break;
            }
            needRotation = false;
        }
    }

    public void die(Tower tower) {
        isAlive = false;
        tower.setXP(tower.getXP() + value);
        User.getInstance().getPlayerStats().setEnemiesKilled(User.getInstance().getPlayerStats().getEnemiesKilled() + 1);
        User.getInstance().addUserXP(value/3);
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
    public void update() {
        super.update();
        if (isAlive) {
            handlePathMovement();
        }

        if (isOnFire) {
            receiveDamageOverTime(damageOverTimeDamage, damageOverTimeDuration, damageOverTimeInterval, damageOverTimeOriginTower);
        }
    }

    private enum MovementDirection {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }

    public static class EnemyFactory {

        private Context context;
        private EnemyPath enemyPath;

        public EnemyFactory(EnemyPath enemyPath, Context context) {
            this.context = context;
            this.enemyPath = enemyPath;
        }

        public Enemy createEnemy(EnemyType enemyType) {
            switch (enemyType) {
                case CAMO_ENEMY:
                    return new CamoEnemy(enemyType, enemyPath, context);
                case ARMOR_ENEMY:
                    return new ArmorEnemy(enemyType, enemyPath, context);
                default:
                    return new Enemy(enemyType, enemyPath, context);
            }
        }
    }
}
