package com.example.project2_rev2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends AppCompatActivity implements View.OnTouchListener {

    // menu elements
    Button btnLogin, btnRegister;

    // login dialog elements
    Dialog login;
    Button btnLoginLoginD, btnGoogleLoginD;
    EditText edtEmailLoginD, edtPasswordLoginD;
    TextView txtRegisterLoginD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnTouchListener(this);
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
        //createRegisterDialog();
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnLogin:
                createLoginDialog(view, motionEvent);
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
        }
        return true;
    }
}