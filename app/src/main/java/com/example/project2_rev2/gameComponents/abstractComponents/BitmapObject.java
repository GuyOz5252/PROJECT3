package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.speech.SpeechRecognizer;

import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromVectorDrawable;

import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public abstract class BitmapObject extends GameObject {

    protected Bitmap bitmap;
    protected Position pivotPosition;

    public BitmapObject(double x, double y, int resourceId, Size size, Context context) {
        super(x, y);
        bitmap = getBitmapFromVectorDrawable(context, resourceId);
        if (size != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)size.width, (int)size.height, false);
        }
        this.pivotPosition = new Position(position.x+bitmap.getWidth()/2, position.y+bitmap.getHeight()/2);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,
                (int)this.position.x,
                (int)this.position.y,
                null
        );
    }

    @Override
    public void update() {
        pivotPosition.x = position.x+bitmap.getWidth()/2;
        pivotPosition.y = position.y+bitmap.getHeight()/2;
    }
}
