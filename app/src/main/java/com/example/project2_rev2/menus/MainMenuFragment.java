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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameStructure.GameView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class MainMenuFragment extends Fragment implements View.OnTouchListener {

    private View view;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    ImageButton btnSettings;
    RelativeLayout btnPlay;
    TextView txtUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
        Intent intent = new Intent(getContext(), GameView.class);
        intent.putExtra("sceneIndex", 0);
        intent.putExtra("loadSave", true);
        startActivity(intent);
        ((Activity) view.getContext()).setContentView(R.layout.activity_loading_screen);
        ((Activity) view.getContext()).finish();
    }

    public void clickPlay(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            ((TextView)((ViewGroup)view).getChildAt(1)).setTextSize(50);
            ((ViewGroup)view).getChildAt(0).setScaleX(1);
            ((ViewGroup)view).getChildAt(0).setScaleY(1);
            clickPlay();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ((TextView)((ViewGroup)view).getChildAt(1)).setTextSize(47.5f);
            ((ViewGroup)view).getChildAt(0).setScaleX(0.95f);
            ((ViewGroup)view).getChildAt(0).setScaleY(0.95f);
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