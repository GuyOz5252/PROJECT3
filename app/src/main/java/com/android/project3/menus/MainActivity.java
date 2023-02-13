package com.android.project3.menus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.project3.R;
import com.android.project3.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        handler = new Handler();
        handler.postDelayed(this::start, 500);
    }

    public void start() {
        if (firebaseAuth.getCurrentUser() != null) {
            db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    User.getInstance().setUserData(task.getResult());
                    firebaseAuth.getCurrentUser().reload().addOnCompleteListener(task1 -> {
                        startActivity(new Intent(
                                MainActivity.this,
                                firebaseAuth.getCurrentUser().isEmailVerified() ? MainMenu.class : PendingVerification.class
                        ));
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