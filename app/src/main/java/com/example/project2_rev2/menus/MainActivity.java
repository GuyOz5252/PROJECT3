package com.example.project2_rev2.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.SaveData;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
        handler.postDelayed(this::start, 500);
    }

    public void start() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            setContentView(R.layout.activity_main);
            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User.getInstance().setUserData(task.getResult());
                    startActivity(new Intent(this, MainMenu.class));
                } else {
                    startActivity(new Intent(this, Login.class));
                }
                this.finish();
            });
        } else {
            startActivity(new Intent(this, Login.class));
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {}
}