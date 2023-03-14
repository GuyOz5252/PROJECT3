package com.android.project3.menus;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.android.project3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainMenu extends AppCompatActivity {

    UserProfileFragment userProfileFragment;
    MainMenuFragment mainMenuFragment;
    TowerFragment towerFragment;

    BottomNavigationView navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_main_menu);

        userProfileFragment = new UserProfileFragment();
        mainMenuFragment = new MainMenuFragment();
        towerFragment = new TowerFragment();

        navbar = findViewById(R.id.NavBar_mainMenu);
        navbar.setOnItemSelectedListener(this::onItemItemSelected);
        navbar.setSelectedItemId(R.id.home_mainMenuNavbar);
    }

    public void replaceFragment(Fragment fragment, String dir) {
        if (dir.equals("calc")) {
            if (navbar.getSelectedItemId() == R.id.profile_mainMenuNavbar) {
                dir = "left";
            } else {
                dir = "right";
            }
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        dir.equals("right") ? R.anim.slide_in_right : R.anim.slide_in_left,
                        dir.equals("right") ? R.anim.slide_out_right : R.anim.slide_out_left
                )
                .replace(R.id.frameLayout_mainMenu, fragment)
                .addToBackStack(null)
                .commit();
    }

    public boolean onItemItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_mainMenuNavbar:
                replaceFragment(userProfileFragment, "right");
                break;
            case R.id.home_mainMenuNavbar:
                replaceFragment(mainMenuFragment, "calc");
                break;
            case R.id.towers_mainMenuNavbar:
                replaceFragment(towerFragment, "left");
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        navbar.setSelectedItemId(R.id.home_mainMenuNavbar);
    }
}