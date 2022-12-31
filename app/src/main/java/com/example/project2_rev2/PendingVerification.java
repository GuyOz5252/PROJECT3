package com.example.project2_rev2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_rev2.data.User;
import com.example.project2_rev2.menus.Login;
import com.example.project2_rev2.menus.MainMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class PendingVerification extends AppCompatActivity implements View.OnTouchListener {

    private FirebaseAuth firebaseAuth;
    private boolean isSentEmail;

    TextView txtUsername;
    Button btnAccountVerified, btnResendVerificationEmail, btnDeleteAccount;

    // delete account dialog elements
    Dialog deleteAccount;
    EditText edtUsernameConfirm;
    Button btnDeleteAccountDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_verification);

        firebaseAuth = FirebaseAuth.getInstance();

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
        deleteAccount = new Dialog(this);
        deleteAccount.setContentView(R.layout.dialog_delete_account);
        deleteAccount.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        deleteAccount.setTitle("delete account");

        edtUsernameConfirm = deleteAccount.findViewById(R.id.edtUsernameConfirm_deleteAccountDialog);
        btnDeleteAccountDialog = deleteAccount.findViewById(R.id.btnDeleteAccount_deleteAccountDialog);

        btnDeleteAccountDialog.setOnTouchListener(this);

        deleteAccount.show();
    }

    public void deleteUserDocAndUser() {
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .delete().addOnCompleteListener(task -> FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(this, "user deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }));
    }

    public void deleteData() {
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("data_segment")
                .document("save_data")
                .delete().addOnCompleteListener(task -> FirebaseFirestore.getInstance().collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("data_segment")
                        .document("player_stats")
                        .delete().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                deleteUserDocAndUser();
                            } else {
                                Toast.makeText(this, task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }));
    }

    public void clickDeleteAccountDialog() {
        if (edtUsernameConfirm.getText().toString().isEmpty()) {
            edtUsernameConfirm.setError("enter username to confirm");
            edtUsernameConfirm.requestFocus();
            return;
        }
        if (edtUsernameConfirm.getText().toString().equals(User.getInstance().getUsername())) {
            deleteData();
            this.startActivity(new Intent(this, Login.class));
            this.finish();
        } else {
            edtUsernameConfirm.setError("incorrect username");
            edtUsernameConfirm.requestFocus();
        }
    }

    public void clickDeleteAccountDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickDeleteAccountDialog();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
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
            //=delete account dialog=//
            case R.id.btnDeleteAccount_deleteAccountDialog:
                clickDeleteAccountDialog(view, motionEvent);
                break;
        }
        return true;
    }
}