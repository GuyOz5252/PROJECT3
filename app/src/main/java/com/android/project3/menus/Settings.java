package com.android.project3.menus;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.project3.R;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends Dialog implements View.OnTouchListener {

    private Context context;

    public static boolean isShown;

    private String state;
    private Boolean accountSettings;

    ImageButton btnBack;
    Button btnAccountSettings, btnLogout, btnChangePassword, btnDeleteAccount;

    // reset password dialog elements
    Dialog resetPassword;
    EditText edtEmailResetPassword;
    Button btnSendResetEmail;

    public Settings(boolean accountSettings, @NonNull Context context) {
        super(context);
        isShown = true;
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.dialog_settings);
        this.context = context;
        this.accountSettings = accountSettings;
        if (!accountSettings) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        setCancelable(false);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(R.color.transparent_background);

        bindSettings();
    }

    public void bindSettings() {
        state = "SETTINGS";
        btnAccountSettings = findViewById(R.id.btnAccountSettings_settingsDialog);
        btnBack = findViewById(R.id.btnBack_settings);
        btnAccountSettings.setOnTouchListener(this);
        btnBack.setOnTouchListener(this);
        if (!accountSettings) btnAccountSettings.setVisibility(View.GONE);
    }

    public void bindAccountSettings() {
        state = "ACCOUNT";
        setContentView(R.layout.dialog_account_settings);
        btnBack = findViewById(R.id.btnBack_settings);
        btnLogout = findViewById(R.id.btnLogout_accountSettings);
        btnChangePassword = findViewById(R.id.btnChangePassword_accountSettings);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount_accountSettings);
        btnBack.setOnTouchListener(this);
        btnLogout.setOnTouchListener(this);
        btnChangePassword.setOnTouchListener(this);
        btnDeleteAccount.setOnTouchListener(this);
    }

    public void clickAccountSettings() {
        bindAccountSettings();
    }

    public void clickAccountSettings(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickAccountSettings();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickBack() {
        if (state.equals("SETTINGS")) {
            dismiss();
        } else {
            setContentView(R.layout.dialog_settings);
            bindSettings();
        }
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

    //============account settings=========//
    public void clickLogout() {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(
                context,
                "Are you sure you want to logout?",
                () -> {
                    FirebaseAuth.getInstance().signOut();
                    context.startActivity(new Intent(context, Login.class));
                },
                () -> {}
        );
        customAlertDialog.show();
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
    //=====================================//

    //=======reset password dialog=========//
    public void createResetPasswordDialog() {
        resetPassword = new Dialog(context);
        resetPassword.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
                Toast.makeText(context, "email sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
        DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog(context);
        deleteAccountDialog.show();
    }
    //====================================//

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnAccountSettings_settingsDialog:
                clickAccountSettings(view, motionEvent);
                break;
            case R.id.btnBack_settings:
                clickBack(view, motionEvent);
                break;
            //=account settings=//
            case R.id.btnLogout_accountSettings:
                clickLogout(view, motionEvent);
                break;
            case R.id.btnChangePassword_accountSettings:
                clickChangePassword(view, motionEvent);
                break;
            case R.id.btnDeleteAccount_accountSettings:
                clickDeleteAccount(view, motionEvent);
                break;
            //=confirm dialog=//
            case R.id.btnSendMail_resetPasswordDialog:
                clickResetPassword(view, motionEvent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        clickBack();
    }
}
