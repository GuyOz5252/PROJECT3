package com.example.project2_rev2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameView extends AppCompatActivity {

    TextView txt1, target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        txt1 = findViewById(R.id.text1);
        target = findViewById(R.id.target);

        txt1.setOnLongClickListener(longClickListener);
        target.setOnLongClickListener(longClickListener);
    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {

            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

            view.startDragAndDrop(data, shadowBuilder, view, 0);
            return true;
        }
    };

}