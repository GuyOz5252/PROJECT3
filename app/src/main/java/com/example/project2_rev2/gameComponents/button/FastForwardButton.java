package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;
import static com.example.project2_rev2.utils.HelperMethods.getBitmapFromVectorDrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public class FastForwardButton extends Button {

    private BitmapObject fastForwardIcon;

    public FastForwardButton(Context context) {
        super(xCoordinate(18), yCoordinate(gameDisplay.size.height-180), R.drawable.fast_forward_button_background, new Size(150, 150), context);
        this.fastForwardIcon = new BitmapObject(
                centerPosition.x-60,
                centerPosition.y-60,
                R.drawable.ic_fast_forward_off,
                new Size(120, 120),
                context
        ) {};
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        fastForwardIcon.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                GameValues.isFastForwarded = !GameValues.isFastForwarded;
                if (GameValues.isFastForwarded) {
                    fastForwardIcon.changeBitmap(R.drawable.ic_fast_forward_on);
                } else {
                    fastForwardIcon.changeBitmap(R.drawable.ic_fast_forward_off);
                }
                paint.setAlpha(255);
                fastForwardIcon.getPaint().setAlpha(255);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                paint.setAlpha(100);
                fastForwardIcon.getPaint().setAlpha(100);
            }
        }
        return true;
    }
}
