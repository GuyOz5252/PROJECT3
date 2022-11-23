package com.example.project2_rev2.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.project2_rev2.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);

        AtomicBoolean isRun = new AtomicBoolean(false);

        RelativeLayout relativeLayout = findViewById(R.id.touchDetector);
        relativeLayout.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                isRun.set(true);
                startActivity(new Intent(MainActivity.this, Login.class));
                this.finish();
            }
            return true;
        });

        new Handler().postDelayed(() -> {
            if (!isRun.get()) {
                startActivity(new Intent(MainActivity.this, Login.class));
                this.finish();
            }
        }, 1500);
    }

    @Override
    public void onBackPressed() {}
}