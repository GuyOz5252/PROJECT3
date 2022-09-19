package com.example.project2_rev2.gameStructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameStructure.sceneManagement.SceneManager;
import com.example.project2_rev2.utils.Display;

public class GameView extends AppCompatActivity implements View.OnTouchListener {

    private MainThread mainThread;
    private SceneManager sceneManager;

    private Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)this).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.display = new Display(displayMetrics);

        SurfaceView surfaceView = findViewById(R.id.gameSurface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        sceneManager = new SceneManager(bundle.getInt("idx", 0), display, this); // receive the index of the requested scene and init a new sceneManager with that scene
    }

    public void update() {
        sceneManager.update();
    }

    public void draw(Canvas canvas) {
        // reset the canvas
        canvas.drawColor(Color.BLACK);
        // draw the scene
        sceneManager.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        sceneManager.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}