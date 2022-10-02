package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.example.project2_rev2.utils.Size;
import static com.example.project2_rev2.utils.GaveValues.canvasDisplay;
import static com.example.project2_rev2.utils.GaveValues.display;

public abstract class Button extends BitmapObject {

    protected Rect buttonRect;
    protected boolean isActive;

    public Button(double x, double y, int resourceId, Size size, Context context) {
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
        return buttonRect.contains((int)(motionEvent.getX()), //- (display.size.width/2-canvasDisplay.size.width/2)),
                (int)(motionEvent.getY())); //- (display.size.height/2-canvasDisplay.size.height/2)));
    }

    public abstract void onTouchEvent(MotionEvent motionEvent);
}
