package com.android.project3.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.project3.R;
import com.android.project3.data.User;
import com.google.firebase.auth.FirebaseAuth;

public class PendingVerification extends AppCompatActivity implements View.OnTouchListener {

    private FirebaseAuth firebaseAuth;
    private boolean isSentEmail;

    TextView txtUsername;
    Button btnAccountVerified, btnResendVerificationEmail, btnDeleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_pending_verification);

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.getCurrentUser().sendEmailVerification();

        isSentEmail = false;

        txtUsername = findViewById(R.id.txtUser_pendingVerification);
        btnAccountVerified = findViewById(R.id.btnAccountVerified);
        btnResendVerificationEmail = findViewById(R.id.btnResendVerificationEmail);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount_pendingVerification);

        btnAccountVerified.setOnTouchListener(this);
        btnResendVerificationEmail.setOnTouchListener(this);
        btnDeleteAccount.setOnTouchListener(this);

        txtUsername.setText(User.getInstance().getUsername());
    }

    public void clickAccountVerified() {
        firebaseAuth.getCurrentUser().reload().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                    Toast.makeText(PendingVerification.this, "email verified", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PendingVerification.this, MainMenu.class));
                    PendingVerification.this.finish();
                } else {
                    Toast.makeText(PendingVerification.this, "please verify email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void clickAccountVerified(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickAccountVerified();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickResendVerificationEmail() {
        if (!isSentEmail) {
            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(
                    task -> Toast.makeText(PendingVerification.this, "email sent", Toast.LENGTH_SHORT).show()
            );
            isSentEmail = true;
            new Handler().postDelayed(() -> isSentEmail = false, 30000);
        }
    }

    public void clickResendVerificationEmail(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickResendVerificationEmail();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickDeleteAccount() {
        createDeleteAccountDialog();
    }

    public void clickDeleteAccount(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickDeleteAccount();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    //========delete account dialog=======//
    public void createDeleteAccountDialog() {
        DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog(this);
        deleteAccountDialog.show();
    }
    //====================================//

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnAccountVerified:
                clickAccountVerified(view, motionEvent);
                break;
            case R.id.btnResendVerificationEmail:
                clickResendVerificationEmail(view, motionEvent);
                break;
            case R.id.btnDeleteAccount_pendingVerification:
                clickDeleteAccount(view, motionEvent);
                break;
        }
        return true;
    }
}