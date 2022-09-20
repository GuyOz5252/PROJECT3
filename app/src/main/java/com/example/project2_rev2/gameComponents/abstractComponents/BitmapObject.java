package com.example.project2_rev2.gameComponents.abstractComponents;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromVectorDrawable;

import com.example.project2_rev2.utils.Size;

public abstract class BitmapObject extends GameObject {

    private Bitmap bitmap;

    public BitmapObject(double x, double y, int resourceId, Size size, Context context) {
        super(x, y);
        bitmap = getBitmapFromVectorDrawable(context, resourceId);
        if (size != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, (int)size.width, (int)size.height, false);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap,
                (int)this.position.x,
                (int)this.position.y,
                null
        );
    }
}
