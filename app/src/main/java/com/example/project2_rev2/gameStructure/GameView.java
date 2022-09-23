package com.example.project2_rev2.gameStructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameStructure.sceneManagement.SceneManager;
import com.example.project2_rev2.utils.Display;

public class GameView extends AppCompatActivity implements View.OnTouchListener {

    private MainThread mainThread;
    private SceneManager sceneManager;

    private Display display;

    // ui xml elements
    ImageButton btnPause;

    // pause menu dialog elements
    Dialog pauseMenu;
    Button btnResume, btnSettings, btnExit;
    TextView txtLevelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // hide the action bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // hide nav bar
        setContentView(R.layout.activity_game_view);

        DisplayMetrics displayMetrics = new DisplayMetrics(); // TODO fix display obj
        ((Activity)this).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.display = new Display(displayMetrics);

        SurfaceView surfaceView = findViewById(R.id.gameSurface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                mainThread = new MainThread(surfaceHolder, GameView.this);
                mainThread.startThread();
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });

        Bundle bundle = getIntent().getExtras();
        sceneManager = new SceneManager(bundle.getInt("sceneIndex", 0), display, this); // receive the index of the requested scene and init a new sceneManager with that scene

        btnPause = findViewById(R.id.btnPause);
        btnPause.setOnTouchListener(this);
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

    public void pause() {
        MainThread.isPaused = true;
    }

    public void resume() {
        MainThread.isPaused = false;
    }

    //==========pause menu dialog============//
    public void createPauseMenuDialog() {
        pause();
        pauseMenu = new Dialog(this);
        pauseMenu.setContentView(R.layout.dialog_pause_menu);
        pauseMenu.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // hide bottom bar
        pauseMenu.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        pauseMenu.setTitle("Pause Menu");

        btnResume = pauseMenu.findViewById(R.id.btnResume_pauseMenuDialog);
        btnSettings = pauseMenu.findViewById(R.id.btnSettings_pauseMenuDialog);
        btnExit = pauseMenu.findViewById(R.id.btnExit_pauseMenuDialog);
        txtLevelName = pauseMenu.findViewById(R.id.txtLevelName_pauseMenuDialog);

        btnResume.setOnTouchListener(this);
        btnSettings.setOnTouchListener(this);
        btnExit.setOnTouchListener(this);

        txtLevelName.setText(sceneManager.getLevelName());

        pauseMenu.show();

        pauseMenu.setOnDismissListener(dialogInterface -> resume());
    }

    public void createPauseMenuDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            createPauseMenuDialog();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }
    //=======================================//

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnPause:
                createPauseMenuDialog(view, motionEvent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onPause() {
        mainThread.stopThread();
        super.onPause();
    }
}