package com.example.project2_rev2.gameComponents.abstractComponents;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Button extends RectObject {

    public Button(double posX, double posY, double width, double height, int color) {
        super(posX, posY, width, height, color);
    }

    public boolean isPressed(MotionEvent motionEvent) {
        double leftCircleToTouchDistance = Math.sqrt(
                Math.pow(position.x - motionEvent.getX(), 2) +
                        Math.pow((position.y+size.height/2) - motionEvent.getY(), 2)
        );
        double rightCircleToTouchDistance = Math.sqrt(
                Math.pow((position.x+size.width/2) - motionEvent.getX(), 2) +
                        Math.pow((position.y+size.height/2) - motionEvent.getY(), 2)
        );
        boolean isRectContainingTouch = rect.contains((int)motionEvent.getX(), (int)motionEvent.getY());
        return isRectContainingTouch && leftCircleToTouchDistance < size.height/2 && rightCircleToTouchDistance < size.height/2;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawCircle((float)position.x, (float)(position.y+size.height/2), (float)size.height/2, paint);
        canvas.drawCircle((float)(position.x+size.width), (float)(position.y+size.height/2), (float)size.height/2, paint);
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            System.out.println("pressed");
        }
    }
}
