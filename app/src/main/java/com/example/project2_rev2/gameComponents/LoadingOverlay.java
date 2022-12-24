package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;
import static com.example.project2_rev2.utils.GameValues.xCoordinate;
import static com.example.project2_rev2.utils.GameValues.yCoordinate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.project2_rev2.R;

public class LoadingOverlay {

    private Context context;
    private TextUI loadingText;

    public LoadingOverlay(Context context) {
        this.context = context;
        this.loadingText = new TextUI(
                xCoordinate(gameDisplay.size.width/2),
                yCoordinate(gameDisplay.size.height/2),
                "Loading Towers",
                R.color.black,
                80,
                Paint.Align.CENTER,
                context
        );
        this.loadingText.setBold();
        this.loadingText.setExtraBold();
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(ContextCompat.getColor(context, R.color.transparentWhite));
        loadingText.draw(canvas);
    }
}