package com.example.project2_rev2.gameComponents.buttons;

import android.content.Context;
import android.view.MotionEvent;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.DragAndDropUI;
import com.example.project2_rev2.gameComponents.abstractComponents.Button;
import com.example.project2_rev2.utils.Size;

public class NextTowerPageButton extends Button {

    private DragAndDropUI dragAndDropUI;

    public NextTowerPageButton(DragAndDropUI dragAndDropUI, Context context) {
        super(180, 750, R.drawable.ic_tower_page_right_arrow, new Size(120, 120), context);
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
                    dragAndDropUI.getTowerDragButtonArrayList().forEach(TowerDragButton::onCoinsChange);
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
