package com.example.project2_rev2.gameComponents.abstractComponents;

import android.graphics.Canvas;

public class Button extends RectObject {

    public Button(double posX, double posY, double width, double height, int color) {
        super(posX, posY, width, height, color);
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
}
