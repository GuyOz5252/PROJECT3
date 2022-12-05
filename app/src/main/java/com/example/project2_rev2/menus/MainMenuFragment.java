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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameStructure.GameView;
import com.google.firebase.auth.FirebaseAuth;

public class MainMenuFragment extends Fragment implements View.OnTouchListener {

    private View view;

    private FirebaseAuth firebaseAuth;

    ImageButton btnSettings;
    TextView btnPlay;
    TextView txtUser;

    // start game dialog elements
    Dialog startGame;
    RelativeLayout btnNewGame, btnLoadGame;
    ImageButton btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        btnSettings = view.findViewById(R.id.btnSettings_mainMenuFragment);
        btnPlay = view.findViewById(R.id.btnPlay_mainMenuFragment);
        txtUser = view.findViewById(R.id.txtUser_mainMenuFragment);

        btnSettings.setOnTouchListener(this);
        btnPlay.setOnTouchListener(this);

        txtUser.setText(User.getInstance().getUsername());

        return view;
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
        btnBack = startGame.findViewById(R.id.btnBack_startGame);

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
        startGame.dismiss();
        Intent intent = new Intent(getContext(), GameView.class);
        intent.putExtra("sceneIndex", 0);
        intent.putExtra("loadSave", false);
        startActivity(intent);
        ((Activity) view.getContext()).setContentView(R.layout.activity_loading_screen);
        ((Activity) view.getContext()).finish();
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
            case R.id.btnBack_startGame:
                clickBack(view, motionEvent);
                break;
        }
        return true;
    }
}