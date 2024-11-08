package com.android.project3.gameStructure;

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

import com.android.project3.data.Constants;
import com.android.project3.data.User;
import com.android.project3.gameComponents.FPSCounter;
import com.android.project3.menus.CustomAlertDialog;
import com.android.project3.menus.Settings;
import com.android.project3.utils.Action;
import com.android.project3.R;
import com.android.project3.gameStructure.sceneManagement.SceneManager;
import com.android.project3.menus.MainMenu;
import com.android.project3.utils.Display;
import com.android.project3.data.GameValues;
import com.android.project3.xmlElements.TypeWriter;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class GameView extends AppCompatActivity implements View.OnTouchListener {

    private MainThread mainThread;
    private SceneManager sceneManager;

    private Rect gameCanvasRect;

    private BroadcastReceiver batteryReceiver;

    private FPSCounter fpsCounter;

    // pause menu dialog elements
    Dialog pauseMenu;
    Button btnResume, btnSettings, btnSaveAndExit, btnExitPauseMenu;
    TextView txtLevelName;

    // victory dialog elements
    Dialog victoryDialog;
    RelativeLayout btnExitVictory, btnContinue;
    LinearProgressIndicator xpProgressBar;
    TextView txtPlayerLevel;

    // death dialog elements
    Dialog deathDialog;
    RelativeLayout btnExitDeath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_game_view);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GameValues.init(this, new Display(displayMetrics));

        fpsCounter = new FPSCounter(this);

        SurfaceView surfaceView = findViewById(R.id.gameSurface);
        surfaceView.setOnTouchListener(this::onTouchEvent);
        surfaceView.setScaleX(GameValues.scaleFactor);
        surfaceView.setScaleY(GameValues.scaleFactor);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                mainThread = new MainThread(surfaceHolder, GameView.this);
                mainThread.startThread();
                fpsCounter.setThread(mainThread);
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

            }
        });

        this.gameCanvasRect = new Rect(
                0,
                0,
                (int)GameValues.gameDisplay.size.width,
                (int)GameValues.gameDisplay.size.height
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
        fpsCounter.update();
    }

    public void draw(Canvas canvas) {
        // reset the canvas with background image
        // draw the scene
        sceneManager.draw(canvas);
        if (false) { // TODO debug
            fpsCounter.draw(canvas);
        }
    }

    public boolean onTouchEvent(View view, MotionEvent motionEvent) {
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
        pauseMenu.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        pauseMenu.setContentView(R.layout.dialog_pause_menu);
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
        pauseMenu.dismiss();
        sceneManager.saveGame();
        clickExit();
    }

    public void clickSaveAndExit(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickSaveAndExit();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickExit() {
        pauseMenu.dismiss();
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
        if (deathDialog != null) {
            deathDialog.dismiss();
        }
        pause();
        victoryDialog = new Dialog(this);
        victoryDialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        victoryDialog.setContentView(R.layout.dialog_victory);
        victoryDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        victoryDialog.setCancelable(false);
        victoryDialog.setTitle("victory");

        btnExitVictory = victoryDialog.findViewById(R.id.btnHome_victoryDialog);
        btnContinue = victoryDialog.findViewById(R.id.btnContinue_victoryDialog);
        xpProgressBar = victoryDialog.findViewById(R.id.xpProgressBar_victoryDialog);
        txtPlayerLevel = victoryDialog.findViewById(R.id.txtLevel_victoryDialog);

        btnExitVictory.setOnTouchListener(this);
        btnContinue.setOnTouchListener(this);

        User.getInstance().getPlayerStats().setGamesPlayed(User.getInstance().getPlayerStats().getGamesPlayed() + 1);
        User.getInstance().getPlayerStats().setGamesWon(User.getInstance().getPlayerStats().getGamesWon() + 1);
        User.getInstance().addUserXP(1500);
        User.getInstance().updateFirestoreUserData();

        int level = User.getInstance().getUserLevel();
        double xp = User.getInstance().getUserXP();
        double max = level * Constants.LEVELING_FACTOR;
        double percentage = (xp/max)*100;
        xpProgressBar.setProgress((int)percentage, true);
        txtPlayerLevel.setText(String.valueOf(level));

        ((TypeWriter)victoryDialog.findViewById(R.id.txtVictory_victoryDialog)).animate("victory!");

        victoryDialog.show();
    }

    public Action victory = this::createVictoryDialog;

    public void clickHome(Dialog dialog) {
        dialog.dismiss();
        startActivity(new Intent(this, MainMenu.class));
        this.finish();
    }

    public void clickHomeVictory(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickHome(victoryDialog);
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
        deathDialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        deathDialog.setContentView(R.layout.dialog_death);
        deathDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        deathDialog.setCancelable(false);
        deathDialog.setTitle("death");

        btnExitDeath = deathDialog.findViewById(R.id.btnHome_deathDialog);

        btnExitDeath.setOnTouchListener(this);

        User.getInstance().getPlayerStats().setGamesPlayed(User.getInstance().getPlayerStats().getGamesPlayed() + 1);
        User.getInstance().getPlayerStats().setGamesLost(User.getInstance().getPlayerStats().getGamesLost() + 1);
        if (getIntent().getBooleanExtra("loadSave", false)) {
            User.getInstance().getSaveData().setIsActive(false);
        }
        User.getInstance().updateFirestoreUserData();

        ((TypeWriter)deathDialog.findViewById(R.id.txtDeath_deathDialog)).animate("you died");

        deathDialog.show();
    }

    public Action death = this::createDeathDialog;

    public void clickHomeDeath(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickHome(deathDialog);
            view.setScaleX(1f);
            view.setScaleY(1f);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }
    /*****/

    //=======battery low dialog===========//
    public void createBatteryLowDialog() {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(
                this,
                "BATTERY LOW!\nAre you sure you want to continue?",
                () -> {},
                this::clickSaveAndExit
        );
        customAlertDialog.show();
    }
    //====================================//

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