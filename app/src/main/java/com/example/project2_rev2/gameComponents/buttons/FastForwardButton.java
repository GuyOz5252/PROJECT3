package com.example.project2_rev2.gameComponents.buttons;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Size;

/**
 * a class that represents the fast forward button in the tower bar
 * on press, switch between fast forwarded and regular speed
 */

public class FastForwardButton extends Button {

    private BitmapObject fastForwardIcon;
    private BitmapObject originalFastForwardIcon;
    private BitmapObject pressedFastForwardIcon;

    public FastForwardButton(Context context) {
        super(18, gameDisplay.size.height-180, R.drawable.fast_forward_button_background, new Size(150, 150), context);
        this.originalFastForwardIcon = new BitmapObject(
                centerPosition.x-60,
                centerPosition.y-60,
                R.drawable.ic_fast_forward_off,
                new Size(120, 120),
                context
        ) {};
        this.pressedFastForwardIcon = new BitmapObject(
                centerPosition.x-52.5,
                centerPosition.y-52.5,
                R.drawable.ic_fast_forward_off,
                new Size(105, 105),
                context
        ) {};
        fastForwardIcon = originalFastForwardIcon;
    }

    @Override
    public void setPressEffect(boolean b) {
        if (b) {
            bitmap = pressedBitmap;
            position = pressedPosition;
            fastForwardIcon = pressedFastForwardIcon;
        } else {
            bitmap = originalBitmap;
            position = originalPosition;
            fastForwardIcon = originalFastForwardIcon;
        }
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
                setPressEffect(false);
                if (GameValues.isFastForwarded) {
                    fastForwardIcon.changeBitmap(R.drawable.ic_fast_forward_on);
                } else {
                    fastForwardIcon.changeBitmap(R.drawable.ic_fast_forward_off);
                }
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setPressEffect(true);
                if (GameValues.isFastForwarded) {
                    fastForwardIcon.changeBitmap(R.drawable.ic_fast_forward_on);
                } else {
                    fastForwardIcon.changeBitmap(R.drawable.ic_fast_forward_off);
                }
            }
        } else {
            setPressEffect(false);
        }
        return true;
    }
}
