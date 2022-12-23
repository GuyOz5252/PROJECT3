package com.example.project2_rev2.menus;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.utils.Action;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.atomic.AtomicBoolean;

public class Settings extends Dialog implements View.OnTouchListener {

    private Context context;

    private String state;

    ImageButton btnBack, btnAccountSettings;
    Button btnLogout, btnChangePassword, btnDeleteAccount;

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

    public Settings(boolean accountSettings, @NonNull Context context) {
        super(context);
        this.context = context;
        View decorView = getWindow().getDecorView();
        setContentView(R.layout.dialog_settings);
        int flags;
        if (!accountSettings) {
            findViewById(R.id.accountSettings_linearLayout).setVisibility(View.INVISIBLE);
            flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        } else {
            flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        decorView.setSystemUiVisibility(flags);
        setCancelable(false);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(R.color.transparentWhite);

        bindSettings();
    }

    public void bindSettings() {
        state = "SETTINGS";
        btnAccountSettings = findViewById(R.id.btnAccountSettings_settingsDialog);
        btnBack = findViewById(R.id.btnBack_settings);
        btnAccountSettings.setOnTouchListener(this);
        btnBack.setOnTouchListener(this);
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
        } else if (state.equals("ACCOUNT")) {
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
    //=====================================//

    //===========confirm dialog============//
    public void createConfirmDialog(String messege) {
        confirmDialog = new Dialog(context);
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
            context.startActivity(new Intent(context, Login.class));
            ((Activity)context).finish();
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

    //=======reset password dialog=========//
    public void createResetPasswordDialog() {
        resetPassword = new Dialog(context);
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
        deleteAccount = new Dialog(context);
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

    Action deleteUserDocAndUser = () -> FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .delete().addOnCompleteListener(task -> FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    Toast.makeText(context, "user deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }));

    public void deleteUser() {
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
                                deleteUserDocAndUser.action();
                            } else {
                                Toast.makeText(context, task1.getException().getMessage(), Toast.LENGTH_LONG).show();
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
            deleteUser();
            context.startActivity(new Intent(context, Login.class));
            ((Activity)context).finish();
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
        clickBack();
    }
}
