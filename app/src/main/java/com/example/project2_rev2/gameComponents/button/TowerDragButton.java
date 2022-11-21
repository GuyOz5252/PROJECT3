package com.example.project2_rev2.gameComponents.button;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.gameComponents.managers.TowerManager;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

import java.util.concurrent.atomic.AtomicBoolean;

public class TowerDragButton extends Button {

    private TowerManager towerManager;
    private Tower.TowerType towerType;
    private BitmapObject towerIcon;
    private BitmapObject draggedTower;
    private boolean isDragged;
    private Paint rangeCirclePaint;
    private Paint rangeBorderPaint;

    public TowerDragButton(int resourceId, TowerManager towerManager, Tower.TowerType towerType, Size size, Context context) {
        super(80, 0, resourceId, size, context);
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
    }

    public void setY(int y) {
        this.position.y = y;
        this.buttonRect.set(80, y, (int)(80+size.width), (int)(y+size.height));
        centerPosition.y = position.y+bitmap.getHeight()/2;
        towerIcon.setPosition(centerPosition.x - (size.width-10)/2, centerPosition.y - (size.height-10)/2);
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
            } else if (action == MotionEvent.ACTION_UP) {

                // collider check
                boolean b = true;
                for (Rect rect : GameValues.colliderArrayList) {
                    b = !rect.contains((int) motionEvent.getX(), (int) motionEvent.getY());
                    if (!b) {
                        break;
                    }
                }
                if (b) {
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
