package com.example.project2_rev2.gameStructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project2_rev2.menus.TowerFragment;
import com.example.project2_rev2.menus.TowerUpgradeInfo;
import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.R;
import com.example.project2_rev2.gameStructure.sceneManagement.SceneManager;
import com.example.project2_rev2.menus.MainMenu;
import com.example.project2_rev2.utils.Display;
import com.example.project2_rev2.utils.GameValues;

public class GameView extends AppCompatActivity implements View.OnTouchListener {

    private MainThread mainThread;
    private SceneManager sceneManager;

    private Display display;
    private Rect gameCanvasRect;

    // pause menu dialog elements
    Dialog pauseMenu;
    Button btnResume, btnTowers, btnSave, btnSettings, btnExitPauseMenu;
    TextView txtLevelName;

    // victory dialog elements
    Dialog victoryDialog;
    RelativeLayout btnExitVictory, btnContinue;

    // death dialog elements
    Dialog deathDialog;
    RelativeLayout btnExitDeath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        decorView.setOnSystemUiVisibilityChangeListener(i -> decorView.setSystemUiVisibility(flags));
        setContentView(R.layout.activity_game_view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        display = new Display(displayMetrics);

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

        GameValues.init(this, display);

        this.gameCanvasRect = new Rect(
                (int) GameValues.xCoordinate(0),
                (int) GameValues.yCoordinate(0),
                (int) GameValues.xCoordinate(GameValues.gameDisplay.size.width),
                (int) GameValues.yCoordinate(GameValues.gameDisplay.size.height)
        );

        Bundle bundle = getIntent().getExtras();
        sceneManager = new SceneManager(bundle.getInt("sceneIndex", 0), new Action[] {pause, victory, death}, this); // receive the index of the requested scene and init a new sceneManager with that scene
    }

    public void update() {
        sceneManager.update();
        if (GameValues.isFastForwarded) { // if game is fast forwarded than update the game twice every cycle instead of once
            sceneManager.update();
        }
    }

    public void draw(Canvas canvas) {
        // reset the canvas with background image
        // draw the scene
        sceneManager.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (gameCanvasRect.contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
            sceneManager.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void pause() {
        GameValues.isPaused = true;
    }

    public void resume() {
        GameValues.isPaused = false;
    }

    /***pause menu dialog***/
    public void createPauseMenuDialog() {
        pause();
        pauseMenu = new Dialog(this);
        pauseMenu.setContentView(R.layout.dialog_pause_menu);
        View decorView = pauseMenu.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        pauseMenu.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        pauseMenu.setTitle("Pause Menu");

        btnResume = pauseMenu.findViewById(R.id.btnResume_pauseMenuDialog);
        btnTowers = pauseMenu.findViewById(R.id.btnTowerInfo_pauseMenuDialog);
        btnSave = pauseMenu.findViewById(R.id.btnSave_pauseMenuDialog);
        btnSettings = pauseMenu.findViewById(R.id.btnSettings_pauseMenuDialog);
        btnExitPauseMenu = pauseMenu.findViewById(R.id.btnExit_pauseMenuDialog);
        txtLevelName = pauseMenu.findViewById(R.id.txtLevelName_pauseMenuDialog);

        btnResume.setOnTouchListener(this);
        btnTowers.setOnTouchListener(this);
        btnSave.setOnTouchListener(this);
        btnSettings.setOnTouchListener(this);
        btnExitPauseMenu.setOnTouchListener(this);

        txtLevelName.setText(sceneManager.getLevelName());

        pauseMenu.show();

        pauseMenu.setOnDismissListener(dialogInterface -> resume());
    }

    public Action pause = this::createPauseMenuDialog;

    public void clickResume() {
        pauseMenu.dismiss();
    }

    public void clickResume(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickResume();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha(0.5f);
        }
    }

    public void clickTowers() {

    }

    public void clickTowers(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickTowers();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha(0.5f);
        }
    }

    public void clickSave() {
        // TODO save game
    }

    public void clickSave(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickSave();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha(0.5f);
        }
    }

    public void clickSettings() {

    }

    public void clickSettings(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickSettings();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha(0.5f);
        }
    }

    public void clickExit() {
        pauseMenu.dismiss();
        startActivity(new Intent(this, MainMenu.class));
        this.finish();
    }

    public void clickExit(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickExit();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha(0.5f);
        }
    }
    /*****/

    /***victory dialog***/
    public void createVictoryDialog() {
        pause();
        victoryDialog = new Dialog(this);
        victoryDialog.setContentView(R.layout.dialog_victory);
        View decorView = victoryDialog.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        victoryDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        victoryDialog.setTitle("Victory Dialog");

        btnExitVictory = victoryDialog.findViewById(R.id.btnHome_victoryDialog);
        btnContinue = victoryDialog.findViewById(R.id.btnContinue_victoryDialog);

        btnExitVictory.setOnTouchListener(this);
        btnContinue.setOnTouchListener(this);

        victoryDialog.setCancelable(false);
        victoryDialog.show();
    }

    public Action victory = this::createVictoryDialog;

    public void clickHomeVictory() {
        // pass earnings
        victoryDialog.dismiss();
        startActivity(new Intent(this, MainMenu.class));
        this.finish();
    }

    public void clickHomeVictory(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickHomeVictory();
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickContinue() {
        victoryDialog.dismiss();
        GameValues.isPaused = false;
    }

    public void clickContinue(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickContinue();
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }
    /*****/

    /***death dialog***/
    public void createDeathDialog() {
        pause();
        deathDialog = new Dialog(this);
        deathDialog.setContentView(R.layout.dialog_death);
        View decorView = deathDialog.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        deathDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        deathDialog.setTitle("Death Dialog");

        btnExitDeath = deathDialog.findViewById(R.id.btnHome_deathDialog);

        btnExitDeath.setOnTouchListener(this);

        deathDialog.setCancelable(false);
        deathDialog.show();
    }

    public Action death = this::createDeathDialog;

    public void clickHomeDeath() {
        deathDialog.dismiss();
        startActivity(new Intent(this, MainMenu.class));
        this.finish();
    }

    public void clickHomeDeath(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickHomeDeath();
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }
    /*****/

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            //=pause menu dialog=//
            case R.id.btnResume_pauseMenuDialog:
                clickResume(view, motionEvent);
                break;
            case R.id.btnTowerInfo_pauseMenuDialog:
                clickTowers(view, motionEvent);
                break;
            case R.id.btnSave_pauseMenuDialog:
                clickSave(view, motionEvent);
                break;
            case R.id.btnSettings_pauseMenuDialog:
                clickSettings(view, motionEvent);
                break;
            case R.id.btnExit_pauseMenuDialog:
                clickExit(view, motionEvent);
                break;
            //=victory dialog=//
            case R.id.btnHome_victoryDialog:
                clickHomeVictory(view, motionEvent);
                break;
            case R.id.btnContinue_victoryDialog:
                clickContinue(view, motionEvent);
                break;
            //=death dialog=//
            case R.id.btnHome_deathDialog:
                clickHomeDeath(view, motionEvent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {}

    @Override
    protected void onPause() {
        mainThread.stopThread();
        super.onPause();
    }
}