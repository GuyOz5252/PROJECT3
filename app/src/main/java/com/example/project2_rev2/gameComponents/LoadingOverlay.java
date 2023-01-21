package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GameValues.gameDisplay;

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
                gameDisplay.size.width/2,
                gameDisplay.size.height/2,
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
        canvas.drawColor(ContextCompat.getColor(context, R.color.transparent_white));
        loadingText.draw(canvas);
    }
}
