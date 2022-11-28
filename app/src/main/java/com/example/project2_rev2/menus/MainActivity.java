package com.example.project2_rev2.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    boolean isRun;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        setContentView(R.layout.activity_main);

        isRun = false;

        RelativeLayout relativeLayout = findViewById(R.id.touchDetector);
        relativeLayout.setOnTouchListener(this::skipSplashScreen);

        handler = new Handler();
        handler.postDelayed(() -> {
            if (!isRun) {
                start();
            }
        }, 1500);
    }

    public boolean skipSplashScreen(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            handler = null;
            isRun = true;
            start();
        }
        return true;
    }

    public void start() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            setContentView(R.layout.activity_main);
            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User.getInstance().initUser(task.getResult());
                    startActivity(new Intent(this, MainMenu.class));
                } else {
                    startActivity(new Intent(MainActivity.this, Login.class));
                }
                this.finish();
            });
        } else {
            startActivity(new Intent(MainActivity.this, Login.class));
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {}
}