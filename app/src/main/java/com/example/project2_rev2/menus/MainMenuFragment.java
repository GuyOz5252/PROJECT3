package com.example.project2_rev2.menus;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameStructure.GameView;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainMenuFragment extends Fragment implements View.OnTouchListener {

    private View view;

    private FirebaseAuth firebaseAuth;

    ImageButton btnSettings;
    TextView btnPlay;
    TextView txtUser;

    LinearProgressIndicator xpProgressBar;
    TextView txtPlayerLevel;

    // start game dialog elements
    Dialog startGame;
    RelativeLayout btnNewGame, btnLoadGame;
    ImageButton btnBack;

    // level select dialog elements
    Dialog levelSelect;
    TextView txtLevelName;
    ImageView btnPrevLevel, btnNextLevel;
    ImageView levelThumbnail;
    ImageButton btnBackLevelSelect;
    ArrayList<Level> levelArrayList;
    int currentLevelIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSettings = view.findViewById(R.id.btnSettings_mainMenuFragment);
        btnPlay = view.findViewById(R.id.btnPlay_mainMenuFragment);
        txtUser = view.findViewById(R.id.txtUser_mainMenuFragment);
        xpProgressBar = view.findViewById(R.id.xpProgressBar);
        txtPlayerLevel = view.findViewById(R.id.txtLevel_mainMenuFragment);

        btnSettings.setOnTouchListener(this);
        btnPlay.setOnTouchListener(this);

        setPlayerLevel();
        txtUser.setText(User.getInstance().getUsername());

        return view;
    }

    public void setPlayerLevel() {
        int level = User.getInstance().getUserLevel();
        double xp = User.getInstance().getUserXP();
        double max = level*1800;
        double percentage = (xp/max)*100;
        xpProgressBar.setProgress((int)percentage, true);
        txtPlayerLevel.setText(String.valueOf(level));
    }

    public void clickSettings() {
        firebaseAuth.signOut();
        startActivity(new Intent(getContext(), Login.class));
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

    public void clickPlay() {
        createStartGameDialog();
    }

    public void clickPlay(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            btnPlay.setTextSize(50);
            clickPlay();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            btnPlay.setTextSize(47.5f);
        }
    }

    //==========start game dialog===========//
    public void createStartGameDialog() {
        btnPlay.setVisibility(View.INVISIBLE);
        startGame = new Dialog(getContext());
        View decorView = startGame.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        startGame.setContentView(R.layout.dialog_start_game);
        startGame.setCancelable(false);
        startGame.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        startGame.getWindow().setBackgroundDrawableResource(R.color.transparentWhite);
        startGame.setTitle("start game");

        btnNewGame = startGame.findViewById(R.id.btnNewGame_startGameDialog);
        btnLoadGame = startGame.findViewById(R.id.btnLoadGame_startGameDialog);
        btnBack = startGame.findViewById(R.id.btnBack_startGameDialog);

        if (!User.getInstance().getSaveData().getIsActive()) {
            ((ImageView)((ViewGroup)btnLoadGame).getChildAt(0)).setColorFilter(ContextCompat.getColor(getContext(), R.color.transparentGray));
            ((TextView)((ViewGroup)btnLoadGame).getChildAt(1)).setTextColor(ContextCompat.getColor(getContext(), R.color.transparentGray));
        } else {
            btnLoadGame.setOnTouchListener(this);
        }

        btnNewGame.setOnTouchListener(this);
        btnBack.setOnTouchListener(this);

        startGame.show();

        startGame.setOnDismissListener(dialog -> btnPlay.setVisibility(View.VISIBLE));
    }

    public void clickNewGame() {
        createLevelSelectDialog();
    }

    public void clickNewGame(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickNewGame();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickLoadGame() {
        startGame.dismiss();
        Intent intent = new Intent(getContext(), GameView.class);
        intent.putExtra("sceneIndex", 0);
        intent.putExtra("loadSave", true);
        startActivity(intent);
        ((Activity) view.getContext()).setContentView(R.layout.activity_loading_screen);
        ((Activity) view.getContext()).finish();
    }

    public void clickLoadGame(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickLoadGame();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickBack() {
        startGame.dismiss();
    }

    public void clickBack(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickBack();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void createLevelSelectDialog() {
        startGame.setContentView(R.layout.dialog_level_select);

        txtLevelName = startGame.findViewById(R.id.levelName_levelSelectDialog);
        levelThumbnail = startGame.findViewById(R.id.levelThumbnail_levelSelectDialog);
        btnPrevLevel = startGame.findViewById(R.id.btnPrevLevel);
        btnNextLevel = startGame.findViewById(R.id.btnNextLevel);
        btnBackLevelSelect = startGame.findViewById(R.id.btnBack_startGameDialog);

        levelThumbnail.setOnTouchListener(this);
        btnPrevLevel.setOnTouchListener(this);
        btnNextLevel.setOnTouchListener(this);
        btnBackLevelSelect.setOnTouchListener(this);

        currentLevelIndex = 0;

        levelArrayList = new ArrayList<>();
        for (String sceneTitle : Scene.sceneTitles) {
            levelArrayList.add(new Level(sceneTitle, Level.getLevelThumbnail(sceneTitle)));
        }

        cycleLevels();
    }

    public void cycleLevels() {
        if (currentLevelIndex == 0) {
            btnPrevLevel.setAlpha(0.5f);
            txtLevelName.setText(levelArrayList.get(currentLevelIndex).levelName);
            levelThumbnail.setImageResource(levelArrayList.get(currentLevelIndex).thumbnail);
            btnNextLevel.setAlpha(1f);
        } else if (currentLevelIndex == levelArrayList.size()-1) {
            btnPrevLevel.setAlpha(1f);
            txtLevelName.setText(levelArrayList.get(currentLevelIndex).levelName);
            levelThumbnail.setImageResource(levelArrayList.get(currentLevelIndex).thumbnail);
            btnNextLevel.setAlpha(0.5f);
        } else {
            btnPrevLevel.setAlpha(1f);
            txtLevelName.setText(levelArrayList.get(currentLevelIndex).levelName);
            levelThumbnail.setImageResource(levelArrayList.get(currentLevelIndex).thumbnail);
            btnNextLevel.setAlpha(1f);
        }
    }

    public void clickLevelThumbnail() {
        startGame.dismiss();
        Intent intent = new Intent(getContext(), GameView.class);
        intent.putExtra("sceneIndex", currentLevelIndex);
        intent.putExtra("loadSave", false);
        startActivity(intent);
        ((Activity) view.getContext()).setContentView(R.layout.activity_loading_screen);
        ((Activity) view.getContext()).finish();
    }

    public void clickLevelThumbnail(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickLevelThumbnail();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.95f);
            view.setScaleY(0.95f);
        }
    }

    public void clickPrevLevel() {
        if (currentLevelIndex != 0) {
            currentLevelIndex--;
            cycleLevels();
        }
    }

    public void clickPrevLevel(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickPrevLevel();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickNextLevel() {
        if (currentLevelIndex != levelArrayList.size()-1) {
            currentLevelIndex++;
            cycleLevels();
        }
    }

    public void clickNextLevel(View view,MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickNextLevel();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }
    //=====================================//

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnSettings_mainMenuFragment:
                clickSettings(view, motionEvent);
                break;
            case R.id.btnPlay_mainMenuFragment:
                clickPlay(view, motionEvent);
                break;
            //=start game dialog=//
            case R.id.btnNewGame_startGameDialog:
                clickNewGame(view, motionEvent);
                break;
            case R.id.btnLoadGame_startGameDialog:
                clickLoadGame(view, motionEvent);
                break;
            case R.id.btnBack_startGameDialog:
                clickBack(view, motionEvent);
                break;
            case R.id.levelThumbnail_levelSelectDialog:
                clickLevelThumbnail(view, motionEvent);
                break;
            case R.id.btnPrevLevel:
                clickPrevLevel(view, motionEvent);
                break;
            case R.id.btnNextLevel:
                clickNextLevel(view, motionEvent);
                break;
        }
        return true;
    }

    public static class Level {

        public String levelName;
        public int thumbnail;

        public Level(String levelName, int thumbnail) {
            this.levelName = levelName;
            this.thumbnail = thumbnail;
        }

        public static int getLevelThumbnail(String levelName) {
            switch (levelName) {
                case "DEMO 1":
                    return R.drawable.level_thumbnail;
                case "LEVEL":
                    return R.drawable.level_thumbnail;
                default:
                    return 0;
            }
        }
    }
}