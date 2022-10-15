package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.xOffset;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.ColorRes;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.BitmapObject;
import com.example.project2_rev2.listeners.OnHealthChangeListener;
import com.example.project2_rev2.utils.GameValues;
import com.example.project2_rev2.utils.Position;
import com.example.project2_rev2.utils.Size;

public class HealthCounter extends TextUI implements OnHealthChangeListener {

    private Context context;
    private BitmapObject heartBitmap;
    private float size;

    public HealthCounter(Context context) {
        super(
                xCoordinate(GameValues.gameDisplay.size.width),
                yCoordinate(55),
                String.valueOf(GameValues.getPlayerHealth()),
                R.color.upgradeNotReady,
                55,
                Paint.Align.RIGHT,
                context
        );
        this.context = context;
        this.heartBitmap = new BitmapObject(
                position.x+5,
                position.y,
                R.drawable.coin_icon,
                new Size(55, 55),
                context
        ) {};
        this.size = 55;
    }

    @Override
    public void onHealthChange() {
        changeText(String.valueOf(GameValues.getPlayerHealth()));
    }

    @Override
    public void changeText(String string) {
        super.changeText(string);
        //heartBitmap.setPosition(position.x+5, position.y-size+8);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //heartBitmap.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle((float) position.x, (float) position.y, 5, paint);
    }
}
