package com.example.project2_rev2.gameComponents.button;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.DragAndDropUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Size;

public class NextTowerPageButton extends Button {

    private DragAndDropUI dragAndDropUI;

    public NextTowerPageButton(DragAndDropUI dragAndDropUI, Context context) {
        super(xCoordinate(185), yCoordinate(770), R.drawable.ic_launcher_background, new Size(140, 80), context);
        this.dragAndDropUI = dragAndDropUI;
    }

    @Override
    public void setPressEffect(boolean b) {
        if (b) {
            bitmap = pressedBitmap;
            position = pressedPosition;
        } else {
            bitmap = originalBitmap;
            position = originalPosition;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isPressed(motionEvent)) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (dragAndDropUI.getStartTowerPageIndex()+3 < dragAndDropUI.getTowerDragButtonArrayList().size()) {
                    dragAndDropUI.setStartTowerPageIndex(dragAndDropUI.getStartTowerPageIndex()+3);
                }
                setPressEffect(false);
                return true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                setPressEffect(true);
            }
        } else {
            setPressEffect(false);
        }
        return false;
    }
}
