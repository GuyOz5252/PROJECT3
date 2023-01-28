package com.android.project3.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.MotionEvent;

import androidx.annotation.DrawableRes;

import com.android.project3.utils.Position;
import com.android.project3.utils.Size;

public abstract class Button extends BitmapObject {

    protected Rect buttonRect;
    protected boolean isActive;
    protected Bitmap originalBitmap;
    protected Position originalPosition;
    protected Bitmap pressedBitmap;
    protected Position pressedPosition;

    public Button(double x, double y, @DrawableRes int resourceId, Size size, Context context) {
        super(x, y, resourceId, size, context);
        this.buttonRect = new Rect(
                (int)x,
                (int)y,
                (int)(x+size.width),
                (int)(y+size.height)
        );
        this.isActive = true;
        this.originalBitmap = bitmap;
        this.originalPosition = position;
        this.pressedBitmap = Bitmap.createScaledBitmap(originalBitmap, (int)(size.width-size.width/20), (int)(size.height-size.height/20), false);
        this.pressedPosition = new Position(position.x+size.width/40, position.y+size.height/40);
    }

    public abstract void setPressEffect(boolean b);

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isPressed(MotionEvent motionEvent) {
        return buttonRect.contains((int)(motionEvent.getX()), (int)(motionEvent.getY()));
    }

    public abstract boolean onTouchEvent(MotionEvent motionEvent);
}
