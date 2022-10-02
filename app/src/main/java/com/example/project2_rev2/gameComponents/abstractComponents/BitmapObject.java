package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromVectorDrawable;

import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

import java.net.CookieHandler;

public abstract class BitmapObject extends GameObject {

    protected Bitmap bitmap;
    protected Position centerPosition;

    public BitmapObject(double x, double y, int resourceId, Size size, Context context) {
        super(x, y);
        bitmap = getBitmapFromVectorDrawable(context, resourceId);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)size.width, (int)size.height, false);
        this.centerPosition = new Position(position.x+bitmap.getWidth()/2, position.y+bitmap.getHeight()/2);
    }

    public Position getCenterPosition() {
        return centerPosition;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(
                bitmap,
                (int)position.x,
                (int)position.y,
                null
        );
    }

    @Override
    public void update() {
        centerPosition.x = position.x+bitmap.getWidth()/2;
        centerPosition.y = position.y+bitmap.getHeight()/2;
    }
}
