package com.example.project2_rev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ContentResolver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends AppCompatActivity {

    TextView txt1;
    LinearLayout ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        txt1 = findViewById(R.id.text1);
        ln = findViewById(R.id.ln);

        txt1.setOnLongClickListener(longClickListener);
        ln.setOnDragListener(dragListener);
    }

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