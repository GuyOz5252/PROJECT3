package com.example.project2_rev2.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2_rev2.R;

public class Login extends AppCompatActivity implements View.OnTouchListener {

    // menu elements
    Button btnLogin, btnRegister, btnDebugGame;

    // login dialog elements
    Dialog login;
    Button btnLoginLoginDialog, btnGoogleLoginDialog;
    EditText edtEmailLoginDialog, edtPasswordLoginDialog;
    TextView txtResetPasswordLoginDialog;

    // register dialog elements
    Dialog register;
    Button btnRegisterRegisterDialog;
    EditText edtEmailRegisterDialog, edtPasswordRegisterDialog, edtConPasswordRegisterDialog;

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
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnDebugGame = findViewById(R.id.btnDebugGame);

        btnLogin.setOnTouchListener(this);
        btnRegister.setOnTouchListener(this);
        btnDebugGame.setOnTouchListener(this);
    }

    public void debugGame(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            startActivity(new Intent(this, MainMenu.class));
            this.finish();
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }

    //============login dialog=============//
    public void createLoginDialog() {
        login = new Dialog(this);
        View decorView = login.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        login.setContentView(R.layout.dialog_login);
        login.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        login.setTitle("Login");

        btnLoginLoginDialog = login.findViewById(R.id.btnLogin_loginDialog);
        btnGoogleLoginDialog = login.findViewById(R.id.btnGoogle_loginDialog);
        edtEmailLoginDialog = login.findViewById(R.id.edtMail_loginDialog);
        edtPasswordLoginDialog = login.findViewById(R.id.edtPassword_loginDialog);
        txtResetPasswordLoginDialog = login.findViewById(R.id.txtResetPassword_loginDialog);

        btnLoginLoginDialog.setOnTouchListener(this);
        btnGoogleLoginDialog.setOnTouchListener(this);
        txtResetPasswordLoginDialog.setOnTouchListener(this);

        login.show();
    }

    public void createLoginDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            createLoginDialog();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }

    public void clickLogin() {
        String email = edtEmailLoginDialog.getText().toString();
        String password = edtPasswordLoginDialog.getText().toString();

        if (email.isEmpty()) {
            edtEmailLoginDialog.setError("email required");
            edtEmailLoginDialog.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailLoginDialog.setError("invalid email");
            edtEmailLoginDialog.requestFocus();
            return;
        } else if (password.isEmpty()) {
            edtPasswordLoginDialog.setError("password required");
            edtPasswordLoginDialog.requestFocus();
            return;
        } else if (password.length() < 6) {
            edtPasswordLoginDialog.setError("password too short, minimum length is 6");
            edtPasswordLoginDialog.requestFocus();
            return;
        }

        // login user

        login.dismiss();
    }

    public void clickLogin(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickLogin();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }

    public void clickGoogle() {
        // login with google

        login.dismiss();
    }

    public void clickGoogle(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickGoogle();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }

    public void clickedResetPassword() {
        login.dismiss();
    }

    public void clickedResetPassword(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickedResetPassword();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }
    //=====================================//

    //==========register dialog============//
    public void createRegisterDialog() {
        register = new Dialog(this);
        View decorView = register.getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);
        register.setContentView(R.layout.dialog_register);
        register.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        register.setTitle("Register");

        btnRegisterRegisterDialog = register.findViewById(R.id.btnRegister_registerDialog);
        edtEmailRegisterDialog = register.findViewById(R.id.edtMail_registerDialog);
        edtPasswordRegisterDialog = register.findViewById(R.id.edtPassword_registerDialog);
        edtConPasswordRegisterDialog = register.findViewById(R.id.edtConPassword_registerDialog);

        btnRegisterRegisterDialog.setOnTouchListener(this);

        register.show();
    }

    public void createRegisterDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            createRegisterDialog();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }

    public void clickRegister() {
        String email = edtEmailRegisterDialog.getText().toString();
        String password = edtPasswordRegisterDialog.getText().toString();
        String conPassword = edtConPasswordRegisterDialog.getText().toString();

        if (email.isEmpty()) {
            edtEmailRegisterDialog.setError("email required");
            edtEmailRegisterDialog.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailRegisterDialog.setError("invalid email");
            edtEmailRegisterDialog.requestFocus();
            return;
        } else if (password.isEmpty()) {
            edtPasswordRegisterDialog.setError("password required");
            edtPasswordRegisterDialog.requestFocus();
            return;
        } else if (password.length() < 6) {
            edtPasswordRegisterDialog.setError("password too short, minimum length is 6");
            edtPasswordRegisterDialog.requestFocus();
            return;
        } else if (conPassword.isEmpty()) {
            edtConPasswordRegisterDialog.setError("confirm password");
            edtConPasswordRegisterDialog.requestFocus();
            return;
        } else if (!conPassword.equals(password)) {
            edtConPasswordRegisterDialog.setError("passwords don't match");
            edtConPasswordRegisterDialog.requestFocus();
            return;
        }

        //register user

        register.dismiss();
    }

    public void clickRegister(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickRegister();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }
    //====================================//

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnLogin:
                createLoginDialog(view, motionEvent);
                break;
            case R.id.btnRegister:
                createRegisterDialog(view, motionEvent);
                break;
            case R.id.btnDebugGame:
                debugGame(view, motionEvent);
                break;
            //=login dialog=//
            case R.id.btnLogin_loginDialog:
                clickLogin(view, motionEvent);
                break;
            case R.id.btnGoogle_loginDialog:
                clickGoogle(view, motionEvent);
                break;
            case R.id.txtResetPassword_loginDialog:
                clickedResetPassword(view, motionEvent);
                break;
            //=register dialog=//
            case R.id.btnRegister_registerDialog:
                clickRegister(view, motionEvent);
                break;
        }
        return true;
    }
}