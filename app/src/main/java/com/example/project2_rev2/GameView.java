package com.example.project2_rev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.MessageFormat;

public class GameView extends AppCompatActivity {

    View tst1, tst2, game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_view);

        tst1 = findViewById(R.id.tst1);
        tst2 = findViewById(R.id.tst2);
        game = findViewById(R.id.game);

        tst1.setOnLongClickListener(longClickListener);
        tst2.setOnLongClickListener(longClickListener);

        game.setOnDragListener(dragListener);
        game.setOnTouchListener(touchListener);

    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                double towerX, towerY, targetX, targetY;
                towerX = tst1.getPivotX();
                towerY = tst1.getPivotY();
                targetX = motionEvent.getX();
                targetY = motionEvent.getY();
                double m = (targetY-towerY)/(targetX-towerX);
                double angle = Math.abs(Math.toDegrees(Math.atan(m)));
                System.out.println(MessageFormat.format("tower: ({0},{1}) | point: ({2},{3})", towerX, towerY, targetX, targetY));
                System.out.println((float)angle);
                if (towerX > targetX) {
                    tst1.setRotation((float)angle);
                } else {
                    tst1.setRotation(180-(float)angle);
                }
            }
            return true;
        }
    };

    View.OnLongClickListener longClickListener = view -> {
        ClipData.Item item = new ClipData.Item("");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData data = new ClipData("", mimeTypes, item);
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
        view.startDragAndDrop(data, dragShadowBuilder, view, 0);
        view.setVisibility(View.INVISIBLE);
        return true;
    };

    View.OnDragListener dragListener = (view, dragEvent) -> {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return dragEvent.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
            case DragEvent.ACTION_DRAG_ENTERED:
            case DragEvent.ACTION_DRAG_EXITED:
            case DragEvent.ACTION_DRAG_ENDED:
                view.invalidate();
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                return true;
            case DragEvent.ACTION_DROP:
                ClipData.Item item = dragEvent.getClipData().getItemAt(0);
                CharSequence dragData = item.getText();
                System.out.println(dragData);
                view.invalidate();
                View v = (View)(dragEvent.getLocalState());
                ((ViewGroup)(v.getParent())).removeView(v);
                ((ViewGroup)view).addView(v);
                v.setVisibility(View.VISIBLE);
                return true;
        }
        return true;
    };

}