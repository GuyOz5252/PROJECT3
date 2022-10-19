package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.xOffset;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import androidx.annotation.ColorRes;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.listeners.OnHealthChangeListener;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public class HealthCounter extends TextUI implements OnHealthChangeListener {

    private BitmapObject heartBitmap;
    private float size;

    public HealthCounter(Context context) {
        super(
                xCoordinate(GameValues.gameDisplay.size.width-70),
                yCoordinate(55),
                String.valueOf(GameValues.getPlayerHealth()),
                R.color.upgradeNotReady,
                55,
                Paint.Align.RIGHT,
                context
        );
        GameValues.healthChangeListenerArrayList.add(this);
        setBold();
        setShadow();
        this.size = 55;
        this.heartBitmap = new BitmapObject(
                position.x+10,
                position.y-size+8,
                R.drawable.ic_heart,
                new Size(size, size),
                context
        ) {};
    }

    @Override
    public void onHealthChange() {
        changeText(String.valueOf(GameValues.getPlayerHealth()));
    }

    @Override
    public void changeText(String string) {
        if (Integer.parseInt(string) >= 0) {
            super.changeText(string);
        } else {
            super.changeText("0");
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        heartBitmap.draw(canvas);
    }
}
