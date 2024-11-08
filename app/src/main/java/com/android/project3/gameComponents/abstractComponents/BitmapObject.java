package com.android.project3.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import static com.android.project3.utils.HelperMethods.getBitmapFromVectorDrawable;

import androidx.annotation.DrawableRes;

import com.android.project3.utils.Position;
import com.android.project3.utils.Size;

public abstract class BitmapObject extends GameObject {

    protected Context context;
    protected Size size;
    protected Bitmap bitmap;
    protected Paint paint;
    protected Position centerPosition;

    public BitmapObject(double x, double y, @DrawableRes int resourceId, Size size, Context context) {
        super(x, y);
        this.context = context;
        this.size = size;
        bitmap = getBitmapFromVectorDrawable(context, resourceId);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)size.width, (int)size.height, false);
        this.paint = new Paint();
        this.centerPosition = new Position(position.x+bitmap.getWidth()/2, position.y+bitmap.getHeight()/2);
    }

    public Position getCenterPosition() {
        return centerPosition;
    }

    public Paint getPaint() {
        return paint;
    }

    public Size getSize() {
        return size;
    }

    public void changeBitmap(@DrawableRes int resourceId) {
        bitmap = getBitmapFromVectorDrawable(context, resourceId);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int)size.width, (int)size.height, false);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(
                bitmap,
                (int)position.x,
                (int)position.y,
                paint
        );
    }

    @Override
    public void update() {
        centerPosition.x = position.x+bitmap.getWidth()/2;
        centerPosition.y = position.y+bitmap.getHeight()/2;
    }
}
