package com.example.project2_rev2;

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

public class Login extends AppCompatActivity implements View.OnTouchListener {

    // menu elements
    Button btnLogin, btnRegister, btnDebugGame;

    // login dialog elements
    Dialog login;
    Button btnLoginLoginD, btnGoogleLoginD;
    EditText edtEmailLoginD, edtPasswordLoginD;
    TextView txtRegisterLoginD;

    // register dialog elements
    Dialog register;
    Button btnRegisterRegisterD;
    EditText edtEmailRegisterD, edtPasswordRegisterD, edtConPasswordRegisterD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            startActivity(new Intent(this, GameView.class));
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }

    //============login dialog=============//
    public void createLoginDialog() {
        login = new Dialog(this);
        login.setContentView(R.layout.dialog_login);
        login.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // hide bottom bar
        login.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        login.setTitle("Login");

        btnLoginLoginD = login.findViewById(R.id.btnLogin_loginDialog);
        btnGoogleLoginD = login.findViewById(R.id.btnGoogle_loginDialog);
        edtEmailLoginD = login.findViewById(R.id.edtMail_loginDialog);
        edtPasswordLoginD = login.findViewById(R.id.edtPassword_loginDialog);
        txtRegisterLoginD = login.findViewById(R.id.txtRegister_loginDialog);

        btnLoginLoginD.setOnTouchListener(this);
        btnGoogleLoginD.setOnTouchListener(this);
        txtRegisterLoginD.setOnTouchListener(this);

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
        String email = edtEmailLoginD.getText().toString();
        String password = edtPasswordLoginD.getText().toString();

        if (email.isEmpty()) {
            edtEmailLoginD.setError("email required");
            edtEmailLoginD.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailLoginD.setError("invalid email");
            edtEmailLoginD.requestFocus();
            return;
        } else if (password.isEmpty()) {
            edtPasswordLoginD.setError("password required");
            edtPasswordLoginD.requestFocus();
            return;
        } else if (password.length() < 6) {
            edtPasswordLoginD.setError("password too short, minimum length is 6");
            edtPasswordLoginD.requestFocus();
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

    public void clickedRegisterInDialog() {
        login.dismiss();
        createRegisterDialog();
    }

    public void clickedRegisterInDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickedRegisterInDialog();
            view.setAlpha(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setAlpha((float)0.5);
        }
    }
    //=====================================//

    //==========register dialog============//
    public void createRegisterDialog() {
        register = new Dialog(this);
        register.setContentView(R.layout.dialog_register);
        register.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); // hide bottom bar
        register.getWindow().setBackgroundDrawableResource(R.drawable.dialog_custom);
        register.setTitle("Register");

        btnRegisterRegisterD = register.findViewById(R.id.btnRegister_registerDialog);
        edtEmailRegisterD = register.findViewById(R.id.edtMail_registerDialog);
        edtPasswordRegisterD = register.findViewById(R.id.edtPassword_registerDialog);
        edtConPasswordRegisterD = register.findViewById(R.id.edtConPassword_registerDialog);

        btnRegisterRegisterD.setOnTouchListener(this);

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
        String email = edtEmailRegisterD.getText().toString();
        String password = edtPasswordRegisterD.getText().toString();
        String conPassword = edtConPasswordRegisterD.getText().toString();

        if (email.isEmpty()) {
            edtEmailRegisterD.setError("email required");
            edtEmailRegisterD.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmailRegisterD.setError("invalid email");
            edtEmailRegisterD.requestFocus();
            return;
        } else if (password.isEmpty()) {
            edtPasswordRegisterD.setError("password required");
            edtPasswordRegisterD.requestFocus();
            return;
        } else if (password.length() < 6) {
            edtPasswordRegisterD.setError("password too short, minimum length is 6");
            edtPasswordRegisterD.requestFocus();
            return;
        } else if (conPassword.isEmpty()) {
            edtConPasswordRegisterD.setError("confirm password");
            edtConPasswordRegisterD.requestFocus();
            return;
        } else if (!conPassword.equals(password)) {
            edtConPasswordRegisterD.setError("passwords don't match");
            edtConPasswordRegisterD.requestFocus();
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
            case R.id.txtRegister_loginDialog:
                clickedRegisterInDialog(view, motionEvent);
                break;
            //=register dialog=//
            case R.id.btnRegister_registerDialog:
                clickRegister(view, motionEvent);
                break;
        }
        return true;
    }
}