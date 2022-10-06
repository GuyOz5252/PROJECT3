package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.annotation.DrawableRes;

import com.example.project2_rev2.utils.Size;

public abstract class Button extends BitmapObject {

    protected Rect buttonRect;
    protected boolean isActive;

    public Button(double x, double y, @DrawableRes int resourceId, Size size, Context context) {
        super(x, y, resourceId, size, context);
        this.buttonRect = new Rect(
                (int)x,
                (int)y,
                (int)(x+size.width),
                (int)(y+size.height)
        );
        this.isActive = true;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isPressed(MotionEvent motionEvent) {
        return buttonRect.contains((int)(motionEvent.getX()), (int)(motionEvent.getY()));
    }

    public abstract void onTouchEvent(MotionEvent motionEvent);
}
