package com.example.project2_rev2.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Handler handler;

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
                    firebaseAuth.getCurrentUser().reload().addOnCompleteListener(task1 -> {
                        startActivity(new Intent(
                                MainActivity.this,
                                firebaseAuth.getCurrentUser().isEmailVerified() ? MainMenu.class : PendingVerification.class)
                        );
                        this.finish();
                    });
                } else {
                    startActivity(new Intent(this, Login.class));
                    this.finish();
                }
            });
        } else {
            startActivity(new Intent(this, Login.class));
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {}
}