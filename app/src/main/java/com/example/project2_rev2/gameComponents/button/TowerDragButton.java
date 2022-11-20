package com.example.project2_rev2.gameComponents.button;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.HelperMethods;
import com.example.project2_rev2.utils.Size;

public class TowerDragButton extends Button {

    private Bitmap towerIcon;

    public TowerDragButton(double x, int resourceId, Tower.TowerType towerType, Size size, Context context) {
        super(x, 0, resourceId, size, context);
        this.towerIcon = Bitmap.createScaledBitmap(
                HelperMethods.getBitmapFromVectorDrawable(context, towerType.icon),
                (int)size.width-10,
                (int)size.height-10,
                false
        );
    }

    public void setY(int y) {
        this.position.y = y;
    }

    @Override
    public void setPressEffect(boolean b) {
        if (b) {
            bitmap = pressedBitmap;
        } else {
            bitmap = originalBitmap;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(towerIcon, (float)position.x+5, (float)position.y+5, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setPressEffect(false);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setPressEffect(true);
            }
        } else {
            setPressEffect(false);
        }
        return true;
    }
}
