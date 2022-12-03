package com.example.project2_rev2.menus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Binder;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.utils.GameValues;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenu extends AppCompatActivity {

    MainMenuFragment mainMenuFragment;
    TowerFragment towerFragment;

    BottomNavigationView navbar;

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
        setContentView(R.layout.activity_main_menu);

        mainMenuFragment = new MainMenuFragment();
        towerFragment = new TowerFragment();

        navbar = findViewById(R.id.NavBar_mainMenu);
        navbar.setOnItemSelectedListener(this::onItemItemSelected);
        navbar.setSelectedItemId(R.id.home_mainMenuNavbar);
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_left,
                        R.anim.slide_right
                )
                .replace(R.id.frameLayout_mainMenu, fragment)
                .addToBackStack(null)
                .commit();
    }

    public boolean onItemItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_mainMenuNavbar:
                //replaceFragment();
                break;
            case R.id.home_mainMenuNavbar:
                replaceFragment(mainMenuFragment);
                break;
            case R.id.towers_mainMenuNavbar:
                replaceFragment(towerFragment);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        replaceFragment(mainMenuFragment);
    }
}