package com.example.project2_rev2.menus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project2_rev2.R;
import com.example.project2_rev2.menus.Login;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void pass(View view) {
        startActivity(new Intent(this, Login.class));
    }
}