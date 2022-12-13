package com.example.project2_rev2.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.guieffect.qual.UI;

public class Settings extends AppCompatActivity implements View.OnTouchListener {

    Button btnLogout, btnChangePassword, btnDeleteAccount;
    ImageButton btnBack;

    // confirm dialog elements
    Dialog confirmDialog;
    TextView txtConfirmMessege;
    Button btnYes, btnNo;

    // reset password dialog elements
    Dialog resetPassword;
    EditText edtEmailResetPassword;
    Button btnSendResetEmail;

    // delete account dialog elements
    Dialog deleteAccount;
    EditText edtUsernameConfirm;
    Button btnDeleteAccountDialog;

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
        decorView.setOnSystemUiVisibilityChangeListener(i -> decorView.setSystemUiVisibility(flags));
        setContentView(R.layout.activity_settings);

        btnLogout = findViewById(R.id.btnLogout_settings);
        btnChangePassword = findViewById(R.id.btnChangePassword_settings);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount_settings);
        btnBack = findViewById(R.id.btnBack_settings);

        btnLogout.setOnTouchListener(this);
        btnChangePassword.setOnTouchListener(this);
        btnDeleteAccount.setOnTouchListener(this);
        btnBack.setOnTouchListener(this);
    }

    public void clickLogout() {
        createConfirmDialog(" logout?");
    }

    public void clickLogout(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickLogout();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickChangePassword() {
        createResetPasswordDialog();
    }

    public void clickChangePassword(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickChangePassword();
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

    public void clickBack() {
        startActivity(new Intent(this, MainMenu.class));
        this.finish();
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

    //===========confirm dialog============//
    public void createConfirmDialog(String messege) {
        confirmDialog = new Dialog(this);
        View decorView = confirmDialog.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        confirmDialog.setContentView(R.layout.dialog_confirm);
        confirmDialog.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        confirmDialog.setTitle("confirm dialog");

        txtConfirmMessege = confirmDialog.findViewById(R.id.txtConfirmMessege_confirm);
        btnYes = confirmDialog.findViewById(R.id.btnYes_confirm);
        btnNo = confirmDialog.findViewById(R.id.btnNo_confirm);

        btnYes.setOnTouchListener(this);
        btnNo.setOnTouchListener(this);

        txtConfirmMessege.setText(txtConfirmMessege.getText() + messege);

        confirmDialog.show();
    }

    public void clickYes() {
        if (txtConfirmMessege.getText().toString().equals("Are you sure you want to logout?")) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Login.class));
            this.finish();
        }
    }

    public void clickYes(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickYes();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickNo() {
        confirmDialog.dismiss();
    }

    public void clickNo(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickNo();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }
    //=====================================//

    //=======reset password dialog=======//
    public void createResetPasswordDialog() {
        resetPassword = new Dialog(this);
        View decorView = resetPassword.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        resetPassword.setContentView(R.layout.dialog_reset_password);
        resetPassword.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        resetPassword.setTitle("Forgot Password");

        edtEmailResetPassword = resetPassword.findViewById(R.id.edtMail_resetPasswordDialog);
        btnSendResetEmail = resetPassword.findViewById(R.id.btnSendMail_resetPasswordDialog);

        btnSendResetEmail.setOnTouchListener(this);

        resetPassword.show();
    }

    public void clickResetPassword() {
        if (edtEmailResetPassword.getText().toString().isEmpty()) {
            edtEmailResetPassword.setError("email required");
            edtEmailResetPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(edtEmailResetPassword.getText().toString()).matches()) {
            edtEmailResetPassword.setError("invalid email");
            edtEmailResetPassword.requestFocus();
            return;
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(edtEmailResetPassword.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "email sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        resetPassword.dismiss();
    }

    public void clickResetPassword(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickResetPassword();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }
    //====================================//

    //========delete account dialog=======//
    public void createDeleteAccountDialog() {
        deleteAccount = new Dialog(this);
        View decorView = deleteAccount.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        deleteAccount.setContentView(R.layout.dialog_delete_account);
        deleteAccount.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        deleteAccount.setTitle("delete account");

        edtUsernameConfirm = deleteAccount.findViewById(R.id.edtUsernameConfirm_deleteAccountDialog);
        btnDeleteAccountDialog = deleteAccount.findViewById(R.id.btnDeleteAccount_deleteAccountDialog);

        btnDeleteAccountDialog.setOnTouchListener(this);

        deleteAccount.show();
    }

    public void clickDeleteAccountDialog() {
        if (edtUsernameConfirm.getText().toString().isEmpty()) {
            edtUsernameConfirm.setError("enter username to confirm");
            edtUsernameConfirm.requestFocus();
            return;
        }
        if (edtUsernameConfirm.getText().toString().equals(User.getInstance().getUsername())) {
            FirebaseFirestore.getInstance().collection("users")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid()).delete().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(Settings.this, "user deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Settings.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(Settings.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            startActivity(new Intent(Settings.this, Login.class));
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
            case R.id.btnLogout_settings:
                clickLogout(view, motionEvent);
                break;
            case R.id.btnChangePassword_settings:
                clickChangePassword(view, motionEvent);
                break;
            case R.id.btnDeleteAccount_settings:
                clickDeleteAccount(view, motionEvent);
                break;
            case R.id.btnBack_settings:
                clickBack(view, motionEvent);
                break;
            //=confirm dialog=//
            case R.id.btnYes_confirm:
                clickYes(view, motionEvent);
                break;
            case R.id.btnNo_confirm:
                clickNo(view, motionEvent);
                break;
            //=confirm dialog=//
            case R.id.btnSendMail_resetPasswordDialog:
                clickResetPassword(view, motionEvent);
                break;
            //=delete account dialog=//
            case R.id.btnDeleteAccount_deleteAccountDialog:
                clickDeleteAccountDialog(view, motionEvent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainMenu.class));
        this.finish();
    }
}