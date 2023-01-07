package com.example.project2_rev2.menus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String developerUID = "fbwBdNU80PgfjQZWVwynJtmUJPx2";
        final double version = 0.5;
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(developerUID).get().addOnCompleteListener(task -> {
                    if ((double)(task.getResult().get("version")) <= version) {
                        handler = new Handler();
                        handler.postDelayed(this::start, 500);
                    } else {
                        Toast.makeText(MainActivity.this, "update!, ask for updated APK", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
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