package com.example.project2_rev2.gameStructure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project2_rev2.data.User;
import com.example.project2_rev2.menus.Settings;
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

    private BroadcastReceiver batteryReceiver;

    // pause menu dialog elements
    Dialog pauseMenu;
    Button btnResume, btnSettings, btnSaveAndExit, btnExitPauseMenu;
    TextView txtLevelName;

    // victory dialog elements
    Dialog victoryDialog;
    RelativeLayout btnExitVictory, btnContinue;

    // death dialog elements
    Dialog deathDialog;
    RelativeLayout btnExitDeath;

    // battery low dialog
    Dialog batteryLow;
    TextView txtBatteryLowWarning;
    Button btnContinueLowBattery, btnSaveAndExitLowBattery;

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
        sceneManager = new SceneManager( // receive the index of the requested scene and init a new sceneManager with that scene
                (bundle.getBoolean("loadSave", false)) ? User.getInstance().getSaveData().getSceneIndex() : bundle.getInt("sceneIndex", 0),
                new Action[] {pause, victory, death},
                bundle.getBoolean("loadSave", false),
                this
        );

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                createBatteryLowDialog();
            }
        };
        registerReceiver(batteryReceiver, intentFilter);
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
        pauseMenu.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        pauseMenu.setTitle("pause menu");

        btnResume = pauseMenu.findViewById(R.id.btnResume_pauseMenuDialog);
        btnSaveAndExit = pauseMenu.findViewById(R.id.btnSaveAndExit_pauseMenuDialog);
        btnSettings = pauseMenu.findViewById(R.id.btnSettings_pauseMenuDialog);
        btnExitPauseMenu = pauseMenu.findViewById(R.id.btnExit_pauseMenuDialog);
        txtLevelName = pauseMenu.findViewById(R.id.txtLevelName_pauseMenuDialog);

        btnResume.setOnTouchListener(this);
        btnSettings.setOnTouchListener(this);
        btnExitPauseMenu.setOnTouchListener(this);
        btnSaveAndExit.setOnTouchListener(this);

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
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickSettings() {
        Settings settings = new Settings(false, this);
        settings.show();
    }

    public void clickSettings(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickSettings();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickSaveAndExit() {
        sceneManager.saveGame();
        clickExit();
    }

    public void clickSaveAndExit(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            pauseMenu.dismiss();
            clickSaveAndExit();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickExit() {
        User.getInstance().updateFirestoreUserData();
        startActivity(new Intent(this, MainMenu.class));
        this.finish();
    }

    public void clickExit(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickExit();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
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
        victoryDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        victoryDialog.setCancelable(false);
        victoryDialog.setTitle("victory");

        btnExitVictory = victoryDialog.findViewById(R.id.btnHome_victoryDialog);
        btnContinue = victoryDialog.findViewById(R.id.btnContinue_victoryDialog);

        btnExitVictory.setOnTouchListener(this);
        btnContinue.setOnTouchListener(this);

        User.getInstance().getPlayerStats().setGamesPlayed(User.getInstance().getPlayerStats().getGamesPlayed() + 1);
        User.getInstance().getPlayerStats().setGamesWon(User.getInstance().getPlayerStats().getGamesWon() + 1);
        User.getInstance().addUserXP(1500);
        User.getInstance().updateFirestoreUserData();

        victoryDialog.show();
    }

    public Action victory = this::createVictoryDialog;

    public void clickHomeVictory() {
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
        deathDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        deathDialog.setTitle("death");

        btnExitDeath = deathDialog.findViewById(R.id.btnHome_deathDialog);

        btnExitDeath.setOnTouchListener(this);

        User.getInstance().getPlayerStats().setGamesPlayed(User.getInstance().getPlayerStats().getGamesPlayed() + 1);
        User.getInstance().getPlayerStats().setGamesLost(User.getInstance().getPlayerStats().getGamesLost() + 1);
        User.getInstance().getSaveData().setIsActive(false);
        User.getInstance().updateFirestoreUserData();

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

    /***battery low dialog***/
    public void createBatteryLowDialog() {
        batteryLow = new Dialog(this);
        batteryLow.setContentView(R.layout.dialog_confirm);
        View decorView = batteryLow.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        batteryLow.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        batteryLow.setTitle("battery low");

        txtBatteryLowWarning = batteryLow.findViewById(R.id.txtConfirmMessege_confirm);
        btnContinueLowBattery = batteryLow.findViewById(R.id.btnYes_confirm);
        btnSaveAndExitLowBattery = batteryLow.findViewById(R.id.btnNo_confirm);

        txtBatteryLowWarning.setText("BATTERY LOW!\nAre you sure you want to continue?");
        btnContinueLowBattery.setText("continue");
        btnSaveAndExitLowBattery.setText("save and exit");
        btnContinueLowBattery.setOnTouchListener(this);
        btnSaveAndExitLowBattery.setOnTouchListener(this);

        batteryLow.show();
    }

    public void clickContinueLowBattery(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            batteryLow.dismiss();
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickSaveAndExitLowBattery(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickSaveAndExit();
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
            case R.id.btnSaveAndExit_pauseMenuDialog:
                clickSaveAndExit(view, motionEvent);
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
            //=battery low=//
            case R.id.btnYes_confirm:
                clickContinueLowBattery(view, motionEvent);
                break;
            case R.id.btnNo_confirm:
                clickSaveAndExitLowBattery(view, motionEvent);
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(batteryReceiver);
        super.onDestroy();
    }
}