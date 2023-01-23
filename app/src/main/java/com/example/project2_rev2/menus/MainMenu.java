package com.example.project2_rev2.menus;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.example.project2_rev2.utils.Action;
import com.example.project2_rev2.utils.GameValues;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainMenu extends AppCompatActivity {

    UserProfileFragment userProfileFragment;
    MainMenuFragment mainMenuFragment;
    TowerFragment towerFragment;

    BottomNavigationView navbar;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // version control
        FirebaseFirestore.getInstance().collection("users").document(getResources().getString(R.string.developerUID)).get().addOnCompleteListener(task -> {
            if (!((double)task.getResult().get("version") <= Double.parseDouble(getResources().getString(R.string.version)))) {
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(new NotificationChannel("", "Warnings", NotificationManager.IMPORTANCE_DEFAULT));
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "")
                        .setSmallIcon(R.drawable.ic_bug)
                        .setContentTitle("Update!")
                        .setContentText("ask for updated APK")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat.from(this).notify(0, builder.build());
                finish();
            }
        });

        userProfileFragment = new UserProfileFragment();
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
                replaceFragment(userProfileFragment);
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
        navbar.setSelectedItemId(R.id.home_mainMenuNavbar);
    }
}