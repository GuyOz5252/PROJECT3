package com.example.project2_rev2.menus;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity implements View.OnTouchListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;

    // menu elements
    Button btnLogin, btnRegister, btnDebugGame;
    ProgressBar loginProgressBar;

    // login dialog elements
    Dialog login;
    Button btnLoginLoginDialog, btnGoogleLoginDialog;
    EditText edtEmailLoginDialog, edtPasswordLoginDialog;
    TextView txtResetPasswordLoginDialog;

    // register dialog elements
    Dialog register;
    Button btnRegisterRegisterDialog;
    EditText edtEmailRegisterDialog, edtUsernameRegisterDialog, edtPasswordRegisterDialog, edtConPasswordRegisterDialog;

    // reset password dialog elements
    Dialog resetPassword;
    Button btnSendResetEmail;
    EditText edtEmailResetPassword;

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

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        btnDebugGame = findViewById(R.id.btnDebugGame);
        loginProgressBar = findViewById(R.id.loginProgressBar);

        btnLogin.setOnTouchListener(this);
        btnRegister.setOnTouchListener(this);
        btnDebugGame.setOnTouchListener(this);
    }

    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                db.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        User.getInstance().setUserData(task1.getResult());
                        Toast.makeText(Login.this, "user logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainMenu.class));
                        this.finish();
                    } else {
                        setLoading(false);
                        Toast.makeText(Login.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                setLoading(false);
                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registerUser(String email, String password, String username) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentReference userDocument = db.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                User.getInstance().createUserData(userDocument, username);
                Toast.makeText(Login.this, "user registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainMenu.class));
                this.finish();
            } else {
                setLoading(false);
                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setLoading(boolean b) {
        if (b) {
            loginProgressBar.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.INVISIBLE);
            btnRegister.setVisibility(View.INVISIBLE);
        } else {
            loginProgressBar.setVisibility(View.INVISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            btnRegister.setVisibility(View.VISIBLE);
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
        login.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
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
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
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
        }

        // login user
        loginUser(email, password);
        setLoading(true);
        login.dismiss();
    }

    public void clickLogin(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickLogin();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickGoogle() {
        // login with google

        login.dismiss();
    }

    public void clickGoogle(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickGoogle();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickedResetPassword() {
        createResetPasswordDialog();
        login.dismiss();
    }

    public void clickedResetPassword(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickedResetPassword();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
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
        register.getWindow().setBackgroundDrawableResource(R.drawable.rounded_corners);
        register.setTitle("Register");

        btnRegisterRegisterDialog = register.findViewById(R.id.btnRegister_registerDialog);
        edtEmailRegisterDialog = register.findViewById(R.id.edtMail_registerDialog);
        edtUsernameRegisterDialog = register.findViewById(R.id.edtUsername_registerDialog);
        edtPasswordRegisterDialog = register.findViewById(R.id.edtPassword_registerDialog);
        edtConPasswordRegisterDialog = register.findViewById(R.id.edtConPassword_registerDialog);

        btnRegisterRegisterDialog.setOnTouchListener(this);

        register.show();
    }

    public void createRegisterDialog(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            createRegisterDialog();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickRegister() {
        String email = edtEmailRegisterDialog.getText().toString();
        String username = edtUsernameRegisterDialog.getText().toString();
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
        } else if (username.isEmpty()) {
            edtUsernameRegisterDialog.setError("name required");
            edtUsernameRegisterDialog.requestFocus();
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
        registerUser(email, password, username);
        setLoading(true);
        register.dismiss();
    }

    public void clickRegister(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickRegister();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }
    //====================================//

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
        firebaseAuth.sendPasswordResetEmail(edtEmailResetPassword.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(Login.this, "email sent", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    public void debugGame(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            startActivity(new Intent(this, MainMenu.class));
            this.finish();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

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
            //=reset password dialog=//
            case R.id.btnSendMail_resetPasswordDialog:
                clickResetPassword(view, motionEvent);
                break;
        }
        return true;
    }
}