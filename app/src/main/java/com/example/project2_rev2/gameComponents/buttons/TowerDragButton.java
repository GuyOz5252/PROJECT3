package com.example.project2_rev2.gameComponents.buttons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.gameComponents.CoinTextUI;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.listeners.OnCoinsChangeListener;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

/**
 * a class that represents the tower buttons in the tower bar
 * include all logic for tower drag and drop and adding towers to tower manager
 */

public class TowerDragButton extends Button implements OnCoinsChangeListener {

    private TowerManager towerManager;
    private TowerType towerType;
    private BitmapObject towerIcon;
    private BitmapObject draggedTower;
    private boolean isDragged;
    private Paint rangeCirclePaint;
    private Paint rangeBorderPaint;
    private CoinTextUI towerPriceTextUI;
    private Paint priceTextBackgroundPaint;

    public TowerDragButton(int resourceId, TowerManager towerManager, TowerType towerType, Size size, Context context) {
        super(80, -500, resourceId, size, context);
        GameValues.coinsChangeListenerArrayList.add(this);
        this.towerManager = towerManager;
        this.towerType = towerType;
        this.towerIcon = new BitmapObject(
                centerPosition.x - (size.width-10)/2,
                centerPosition.y - (size.height-10)/2,
                towerType.icon,
                new Size(size.width-10, size.height-10),
                context
        ) {};
        this.rangeCirclePaint = new Paint();
        this.rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.rangeCircle));
        this.rangeBorderPaint = new Paint();
        this.rangeBorderPaint.setColor(ContextCompat.getColor(context, R.color.white));
        this.rangeBorderPaint.setStyle(Paint.Style.STROKE);
        this.rangeBorderPaint.setStrokeWidth(2);
        this.priceTextBackgroundPaint = new Paint();
        this.priceTextBackgroundPaint.setColor(ContextCompat.getColor(context, R.color.textBackground));
        this.draggedTower = new BitmapObject(
                0,
                0,
                towerType.icon,
                new Size(towerType.size.width, towerType.size.height),
                context
        ) {
            @Override
            public void draw(Canvas canvas) {
                canvas.drawCircle(
                        (float)centerPosition.x,
                        (float)centerPosition.y,
                        towerType.range,
                        rangeCirclePaint
                );
                canvas.drawCircle(
                        (float)centerPosition.x,
                        (float)centerPosition.y,
                        towerType.range,
                        rangeBorderPaint
                );
                super.draw(canvas);
            }

            @Override
            public void update() {
                super.update();
            }
        };
        this.isDragged = false;
        setY(-500);
    }

    public void setY(int y) {
        this.position.y = y;
        this.buttonRect.set(80, y, (int)(80+size.width), (int)(y+size.height));
        centerPosition.y = position.y+bitmap.getHeight()/2;
        pressedPosition.y = position.y+size.height/40;
        towerIcon.setPosition(centerPosition.x - (size.width-10)/2, centerPosition.y - (size.height-10)/2);
        int textXOffset = 50;
        if (String.valueOf(towerType.value).length() <= 2) {
            textXOffset = 40;
        }
        this.towerPriceTextUI = new CoinTextUI(
                centerPosition.x-textXOffset,
                centerPosition.y+size.height/2+8,
                String.valueOf(towerType.value),
                (GameValues.getPlayerCoins() > towerType.value) ? R.color.white : R.color.red,
                35f,
                context
        );
        this.towerPriceTextUI.setBold();
        this.towerPriceTextUI.setShadow();
    }

    public boolean isNotColliding(Rect rect) {
        for (Rect collider : GameValues.colliderArrayList) {
            if (rect.intersect(collider)) return false;
        }
        return true;
    }

    @Override
    public void onCoinsChange() {
        if (GameValues.getPlayerCoins() < towerType.value) {
            towerPriceTextUI.changeColor(R.color.red);
        } else {
            towerPriceTextUI.changeColor(R.color.white);
        }
    }

    @Override
    public void setPressEffect(boolean b) {
        if (b) {
            bitmap = pressedBitmap;
            position = pressedPosition;
        } else {
            bitmap = originalBitmap;
            position = originalPosition;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isDragged) {
            draggedTower.draw(canvas);
        } else {
            towerIcon.draw(canvas);
            canvas.drawRect(new Rect(
                    (int)(centerPosition.x-60),
                    (int)(centerPosition.y+size.height/2-25),
                    (int)(centerPosition.x+60),
                    (int)(centerPosition.y+size.height/2+15)
            ),
                priceTextBackgroundPaint
            );
            towerPriceTextUI.draw(canvas);
        }
    }

    @Override
    public void update() {
        if (isDragged) {
            draggedTower.update();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isDragged) {
            if (isPressed(motionEvent) && towerType.value <= GameValues.getPlayerCoins()) { // money check
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    setPressEffect(true);
                    draggedTower.setPosition(motionEvent.getX()-draggedTower.getSize().width/2, motionEvent.getY()-draggedTower.getSize().height/2);
                    isDragged = true;
                }
            } else {
                setPressEffect(false);
            }
        } else {
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_MOVE) {
                draggedTower.setPosition(motionEvent.getX() - draggedTower.getSize().width / 2, motionEvent.getY() - draggedTower.getSize().height / 2);

                Rect towerRect = new Rect(
                        (int)(motionEvent.getX()-towerType.size.width/2),
                        (int)(motionEvent.getY()-towerType.size.height/2),
                        (int)(motionEvent.getX()+towerType.size.width/2),
                        (int)(motionEvent.getY()+towerType.size.height/2)
                );
                if (towerType.equals(TowerType.TURRET)) towerRect = new Rect(
                        (int)(motionEvent.getX()-towerType.size.width/2)+35,
                        (int)(motionEvent.getY()-towerType.size.height/2)+30,
                        (int)(motionEvent.getX()+towerType.size.width/2)-25,
                        (int)(motionEvent.getY()+towerType.size.height/2)-30
                );
                if (isNotColliding(towerRect)) {
                    rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.rangeCircle));
                } else {
                    rangeCirclePaint.setColor(ContextCompat.getColor(context, R.color.invalidRangeCircle));
                }
            } else if (action == MotionEvent.ACTION_UP) {

                // collider check
                Rect towerRect = new Rect(
                        (int)(motionEvent.getX()-towerType.size.width/2),
                        (int)(motionEvent.getY()-towerType.size.height/2),
                        (int)(motionEvent.getX()+towerType.size.width/2),
                        (int)(motionEvent.getY()+towerType.size.height/2)
                );
                if (towerType.equals(TowerType.TURRET)) towerRect = new Rect(
                        (int)(motionEvent.getX()-towerType.size.width/2)+35,
                        (int)(motionEvent.getY()-towerType.size.height/2)+30,
                        (int)(motionEvent.getX()+towerType.size.width/2)-25,
                        (int)(motionEvent.getY()+towerType.size.height/2)-30
                );
                if (isNotColliding(towerRect)) {
                    towerManager.addTower(towerType, motionEvent.getX(), motionEvent.getY());
                    GameValues.setPlayerCoins(GameValues.getPlayerCoins() - towerType.value);
                }

                isDragged = false;
                setPressEffect(false);
            }
        }
        return true;
    }
}
