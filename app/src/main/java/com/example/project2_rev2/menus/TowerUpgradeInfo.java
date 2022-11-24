package com.example.project2_rev2.menus;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TowerUpgradeInfo extends AppCompatActivity implements View.OnTouchListener {

    LinearLayout currentUpgradeCard, prevUpgradeCard, nextUpgradeCard;
    ImageButton btnPrevUpgrade, btnNextUpgrade;
    TextView tvTowerName;

    private ArrayList<String>[] upgradeNameArrayList;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tower_upgrade_info);
        View decorView = getWindow().getDecorView();
        int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(flags);

        currentUpgradeCard = findViewById(R.id.towerUpgradeCard_curr);
        prevUpgradeCard = findViewById(R.id.towerUpgradeCard_prev);
        nextUpgradeCard = findViewById(R.id.towerUpgradeCard_next);
        tvTowerName = findViewById(R.id.towerName_towerUpgradeInfo);
        btnPrevUpgrade = findViewById(R.id.btnPrevUpgrade);
        btnNextUpgrade = findViewById(R.id.btnNextUpgrade);

        btnPrevUpgrade.setOnTouchListener(this);
        btnNextUpgrade.setOnTouchListener(this);

        Bundle bundle = getIntent().getExtras();
        Tower.TowerType towerType = (Tower.TowerType) bundle.getSerializable("TowerType");
        tvTowerName.setText(towerType.towerName);

        upgradeNameArrayList = new ArrayList[2];
        upgradeNameArrayList[0] = new ArrayList<>(Arrays.asList(towerType.towerUpgradePathOne.name));
        upgradeNameArrayList[1] = new ArrayList<>(Arrays.asList(towerType.towerUpgradePathTwo.name));

        currentIndex = 0;
        cycleTowerUpgrades();
    }

    public void cycleTowerUpgrades() {
        if (currentIndex == 0) {
            prevUpgradeCard.setVisibility(View.INVISIBLE);
            btnPrevUpgrade.setAlpha(0.5f);
            createTowerUpgradeCard(currentUpgradeCard);
            nextUpgradeCard.setVisibility(View.VISIBLE);
            btnNextUpgrade.setAlpha(1f);
            createTowerUpgradeCard(nextUpgradeCard);
        } else if (currentIndex == 3) {
            prevUpgradeCard.setVisibility(View.VISIBLE);
            btnPrevUpgrade.setAlpha(1f);
            createTowerUpgradeCard(prevUpgradeCard);
            createTowerUpgradeCard(currentUpgradeCard);
            nextUpgradeCard.setVisibility(View.INVISIBLE);
            btnNextUpgrade.setAlpha(0.5f);
        } else {
            prevUpgradeCard.setVisibility(View.VISIBLE);
            btnPrevUpgrade.setAlpha(1f);
            createTowerUpgradeCard(prevUpgradeCard);
            createTowerUpgradeCard(currentUpgradeCard);
            nextUpgradeCard.setVisibility(View.VISIBLE);
            btnNextUpgrade.setAlpha(1f);
            createTowerUpgradeCard(nextUpgradeCard);
        }
    }

    public void createTowerUpgradeCard(LinearLayout linearLayout) {
        linearLayout.removeAllViews();

        linearLayout.addView(createTowerUpgradePathInfo(0));
        linearLayout.addView(createTowerUpgradePathInfo(1));
    }

    public LinearLayout createTowerUpgradePathInfo(int pathIndex) {
        LinearLayout linearLayout = new LinearLayout(this);

        float scale = this.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(210 * scale + 0.5f), (int)(117 * scale + 0.5f));
        params.setMargins(0, 0, 0, 5);
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER|Gravity.TOP);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(R.drawable.dialog_custom);
        linearLayout.getBackground().setTint(ContextCompat.getColor(this, R.color.black));

        TextView upgradeNameTextView = new TextView(this);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 0, 0, 5);
        upgradeNameTextView.setLayoutParams(textViewParams);
        upgradeNameTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
        upgradeNameTextView.setTextSize(20);
        upgradeNameTextView.setTypeface(null, Typeface.BOLD);
        if (currentIndex < upgradeNameArrayList[pathIndex].size()) {
            upgradeNameTextView.setText(upgradeNameArrayList[pathIndex].get(currentIndex));
        }

        TextView upgradeInfoTextView = new TextView(this);
        upgradeInfoTextView.setLayoutParams(textViewParams);
        upgradeInfoTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
        upgradeInfoTextView.setTextSize(16);
        upgradeInfoTextView.setText("INFO");

        linearLayout.addView(upgradeNameTextView);
        linearLayout.addView(upgradeInfoTextView);

        return linearLayout;
    }

    public void clickPrevUpgrade() {
        if (currentIndex != 0) {
            currentIndex--;
            cycleTowerUpgrades();
        }
    }

    public void clickPrevUpgrade(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickPrevUpgrade();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickNextUpgrade() {
        if (currentIndex != 3) {
            currentIndex++;
            cycleTowerUpgrades();
        }
    }

    public void clickNextUpgrade(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickNextUpgrade();
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
            case R.id.btnPrevUpgrade:
                clickPrevUpgrade(view, motionEvent);
                break;
            case R.id.btnNextUpgrade:
                clickNextUpgrade(view, motionEvent);
                break;
        }
        return true;
    }
}