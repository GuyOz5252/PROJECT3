package com.example.project2_rev2.menus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameStructure.GameView;
import com.example.project2_rev2.gameStructure.sceneManagement.Scene;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.Arrays;

public class MainMenuFragment extends Fragment implements View.OnTouchListener {

    private View view;

    ImageButton btnSettings;
    MaterialButton btnPlay;
    TextView txtUsername;

    LinearProgressIndicator xpProgressBar;
    TextView txtPlayerLevel;

    // start game dialog elements
    Dialog startGame;
    Button btnNewGame, btnLoadGame;
    ImageButton btnBack;
    String state;

    // level select dialog elements
    TextView txtLevelName;
    ImageView btnPrevLevel, btnNextLevel;
    ImageView levelThumbnail;
    LinearLayout levelReqLock;
    ImageButton btnBackLevelSelect;
    ArrayList<Scene.Levels> levelArrayList;
    int currentLevelIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        btnSettings = view.findViewById(R.id.btnSettings_mainMenuFragment);
        btnPlay = view.findViewById(R.id.btnPlay_mainMenuFragment);
        txtUsername = view.findViewById(R.id.txtUser_mainMenuFragment);
        xpProgressBar = view.findViewById(R.id.xpProgressBar);
        txtPlayerLevel = view.findViewById(R.id.txtLevel_mainMenuFragment);

        btnSettings.setOnTouchListener(this);
        btnPlay.setOnTouchListener(this);

        setPlayerLevel();
        txtUsername.setText(User.getInstance().getUsername());

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
        btnPlay.setVisibility(View.INVISIBLE);
        Settings settings = new Settings(true, getContext());
        settings.setOnDismissListener(dialogInterface -> btnPlay.setVisibility(View.VISIBLE));
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
        startGame = new Dialog(getContext()) {
            @Override
            public void onBackPressed() {
                clickBack();
            }
        };

        bindStartGame();

        startGame.show();

        startGame.setOnDismissListener(dialogInterface -> btnPlay.setVisibility(View.VISIBLE));
    }

    public void bindStartGame() {
        state = "START_GAME";
        startGame.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        startGame.setContentView(R.layout.dialog_start_game);
        startGame.setCancelable(false);
        startGame.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        startGame.getWindow().setBackgroundDrawableResource(R.color.transparent_background);
        startGame.setTitle("start game");

        btnNewGame = startGame.findViewById(R.id.btnNewGame_startGameDialog);
        btnLoadGame = startGame.findViewById(R.id.btnLoadGame_startGameDialog);
        btnBack = startGame.findViewById(R.id.btnBack_startGameDialog);

        if (!User.getInstance().getSaveData().getIsActive()) {
            btnLoadGame.setTextColor(ContextCompat.getColor(getContext(), R.color.transparent_gray));
            btnLoadGame.getCompoundDrawables()[1].setTint(ContextCompat.getColor(getContext(), R.color.transparent_gray));
        } else {
            btnLoadGame.setOnTouchListener(this);
        }

        btnNewGame.setOnTouchListener(this);
        btnBack.setOnTouchListener(this);
    }

    public void clickNewGame() {
        bindLevelSelect();
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
        if (state.equals("START_GAME")) {
            startGame.dismiss();
        } else {
            bindStartGame();
        }
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

    public void bindLevelSelect() {
        state = "LEVEL_SELECT";
        startGame.setContentView(R.layout.dialog_level_select);
        txtLevelName = startGame.findViewById(R.id.levelName_levelSelectDialog);
        levelThumbnail = startGame.findViewById(R.id.levelThumbnail_levelSelectDialog);
        levelReqLock = startGame.findViewById(R.id.levelReqLock);
        btnPrevLevel = startGame.findViewById(R.id.btnPrevLevel);
        btnNextLevel = startGame.findViewById(R.id.btnNextLevel);
        btnBackLevelSelect = startGame.findViewById(R.id.btnBack_startGameDialog);

        levelThumbnail.setOnTouchListener(this);
        btnPrevLevel.setOnTouchListener(this);
        btnNextLevel.setOnTouchListener(this);
        btnBackLevelSelect.setOnTouchListener(this);

        currentLevelIndex = 0;

        levelArrayList = new ArrayList<>(Arrays.asList(Scene.Levels.values()));

        cycleLevels();
    }

    public void cycleLevels() {
        if (levelArrayList.get(currentLevelIndex).levelReq > User.getInstance().getUserLevel()) {
            levelThumbnail.setAlpha(0.7f);
            levelReqLock.setVisibility(View.VISIBLE);
            ((TextView)levelReqLock.getChildAt(1)).setText("level " + levelArrayList.get(currentLevelIndex).levelReq + " required");
        } else {
            levelThumbnail.setAlpha(1f);
            levelReqLock.setVisibility(View.INVISIBLE);
        }
        txtLevelName.setText(levelArrayList.get(currentLevelIndex).name);
        levelThumbnail.setImageResource(levelArrayList.get(currentLevelIndex).background);
        if (currentLevelIndex == 0) {
            btnPrevLevel.setAlpha(0.5f);
            btnNextLevel.setAlpha(1f);
        } else if (currentLevelIndex == levelArrayList.size()-1) {
            btnPrevLevel.setAlpha(1f);
            btnNextLevel.setAlpha(0.5f);
        } else {
            btnPrevLevel.setAlpha(1f);
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
        if (levelArrayList.get(currentLevelIndex).levelReq > User.getInstance().getUserLevel()) {
            return;
        }
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
}