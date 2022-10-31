package com.example.project2_rev2.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameStructure.GameView;

public class MainMenuFragment extends Fragment implements View.OnTouchListener {

    private View activityView;

    ImageButton btnSettings;
    RelativeLayout btnPlay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activityView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        btnSettings = activityView.findViewById(R.id.btnSettings_mainMenuFragment);
        btnPlay = activityView.findViewById(R.id.btnPlay_mainMenuFragment);

        btnSettings.setOnTouchListener(this);
        btnPlay.setOnTouchListener(this);

        return activityView;
    }

    public void clickSettings(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            System.out.println("click settings");
            ((ImageButton)view).setScaleX(1);
            ((ImageButton)view).setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ((ImageButton)view).setScaleX(0.9f);
            ((ImageButton)view).setScaleY(0.9f);
        }
    }

    public void clickPlay() {
        Intent intent = new Intent((Activity) activityView.getContext(), GameView.class);
        intent.putExtra("sceneIndex", 0);
        startActivity(intent);
        ((Activity) activityView.getContext()).finish();
    }

    public void clickPlay(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickPlay();
            ((TextView)((ViewGroup)view).getChildAt(1)).setTextSize(50);
            ((ImageView)((ViewGroup)view).getChildAt(0)).setScaleX(1);
            ((ImageView)((ViewGroup)view).getChildAt(0)).setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ((TextView)((ViewGroup)view).getChildAt(1)).setTextSize(47.5f);
            ((ImageView)((ViewGroup)view).getChildAt(0)).setScaleX(0.95f);
            ((ImageView)((ViewGroup)view).getChildAt(0)).setScaleY(0.95f);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnSettings_mainMenuFragment:
                clickSettings(view, motionEvent);
                break;
            case R.id.btnPlay_mainMenuFragment:
                clickPlay(view, motionEvent);
                break;
        }
        return true;
    }
}