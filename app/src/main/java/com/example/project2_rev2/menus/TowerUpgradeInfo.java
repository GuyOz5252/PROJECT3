package com.example.project2_rev2.menus;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.TowerType;
import com.example.project2_rev2.data.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TowerUpgradeInfo extends AppCompatActivity implements View.OnTouchListener {

    LinearLayout currentUpgradeCard, prevUpgradeCard, nextUpgradeCard;
    ImageButton btnPrevUpgrade, btnNextUpgrade, btnBack;
    TextView tvTowerName;

    private TowerType towerType;

    private ArrayList<String>[] upgradeNameArrayList;
    private ArrayList<String>[] upgradeInfoArrayList;
    private int[][] upgradeXpReqArray;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (!bundle.getBoolean("hasNavbar", false)) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        setContentView(R.layout.activity_tower_upgrade_info);

        currentUpgradeCard = findViewById(R.id.towerUpgradeCard_curr);
        prevUpgradeCard = findViewById(R.id.towerUpgradeCard_prev);
        nextUpgradeCard = findViewById(R.id.towerUpgradeCard_next);
        tvTowerName = findViewById(R.id.towerName_towerUpgradeInfo);
        btnPrevUpgrade = findViewById(R.id.btnPrevUpgrade);
        btnNextUpgrade = findViewById(R.id.btnNextUpgrade);
        btnBack = findViewById(R.id.btnBack_towerUpgradeInfo);

        btnPrevUpgrade.setOnTouchListener(this);
        btnNextUpgrade.setOnTouchListener(this);
        btnBack.setOnTouchListener(this);

        TowerType towerType = (TowerType) bundle.getSerializable("towerType");
        this.towerType = towerType;
        tvTowerName.setText(towerType.towerName);

        upgradeNameArrayList = new ArrayList[2];
        upgradeNameArrayList[0] = new ArrayList<>(Arrays.asList(towerType.towerUpgradePathOne.name));
        upgradeNameArrayList[1] = new ArrayList<>(Arrays.asList(towerType.towerUpgradePathTwo.name));
        upgradeInfoArrayList = new ArrayList[2];
        upgradeInfoArrayList[0] = new ArrayList<>(Arrays.asList(towerType.towerUpgradePathOne.upgradeInfo));
        upgradeInfoArrayList[1] = new ArrayList<>(Arrays.asList(towerType.towerUpgradePathTwo.upgradeInfo));
        upgradeXpReqArray = new int[2][];
        upgradeXpReqArray[0] = new int[towerType.towerUpgradePathOne.xpReq.length];
        System.arraycopy(towerType.towerUpgradePathOne.xpReq, 0, upgradeXpReqArray[0], 0, towerType.towerUpgradePathOne.xpReq.length);
        upgradeXpReqArray[1] = new int[towerType.towerUpgradePathTwo.xpReq.length];
        System.arraycopy(towerType.towerUpgradePathTwo.xpReq, 0, upgradeXpReqArray[1], 0, towerType.towerUpgradePathTwo.xpReq.length);

        currentIndex = 0;
        cycleTowerUpgrades();
    }

    public void cycleTowerUpgrades() {
        if (currentIndex == 0) {
            prevUpgradeCard.setVisibility(View.INVISIBLE);
            btnPrevUpgrade.setAlpha(0.5f);
            createTowerUpgradeCard(currentUpgradeCard, currentIndex);
            nextUpgradeCard.setVisibility(View.VISIBLE);
            btnNextUpgrade.setAlpha(1f);
            createTowerUpgradeCard(nextUpgradeCard, currentIndex+1);
        } else if (currentIndex == 3) {
            prevUpgradeCard.setVisibility(View.VISIBLE);
            btnPrevUpgrade.setAlpha(1f);
            createTowerUpgradeCard(prevUpgradeCard, currentIndex-1);
            createTowerUpgradeCard(currentUpgradeCard, currentIndex);
            nextUpgradeCard.setVisibility(View.INVISIBLE);
            btnNextUpgrade.setAlpha(0.5f);
        } else {
            prevUpgradeCard.setVisibility(View.VISIBLE);
            btnPrevUpgrade.setAlpha(1f);
            createTowerUpgradeCard(prevUpgradeCard, currentIndex-1);
            createTowerUpgradeCard(currentUpgradeCard, currentIndex);
            nextUpgradeCard.setVisibility(View.VISIBLE);
            btnNextUpgrade.setAlpha(1f);
            createTowerUpgradeCard(nextUpgradeCard, currentIndex+1);
        }
    }

    public void createTowerUpgradeCard(LinearLayout linearLayout, int upgradeIndex) {
        linearLayout.removeAllViews();

        linearLayout.addView(createTowerUpgradePathInfo(0, upgradeIndex));
        linearLayout.addView(createTowerUpgradePathInfo(1, upgradeIndex));
    }

    public LinearLayout createTowerUpgradePathInfo(int pathIndex, int upgradeIndex) {
        LinearLayout linearLayout = new LinearLayout(this);

        float scale = this.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(210 * scale + 0.5f), (int)(117 * scale + 0.5f));
        params.setMargins(0, 0, 0, 10);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(30, 0, 30, 0);
        linearLayout.setGravity(Gravity.CENTER|Gravity.TOP);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundResource(R.drawable.rounded_corners);
        linearLayout.getBackground().setTint(ContextCompat.getColor(this, R.color.black));

        TextView upgradeNameTextView = new TextView(this);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        textViewParams.setMargins(0, 0, 0, 8);
        upgradeNameTextView.setLayoutParams(textViewParams);
        upgradeNameTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
        upgradeNameTextView.setTextSize(20);
        upgradeNameTextView.setTypeface(null, Typeface.BOLD);
        if (upgradeIndex < upgradeNameArrayList[pathIndex].size()) {
            upgradeNameTextView.setText(upgradeNameArrayList[pathIndex].get(upgradeIndex));
        }

        TextView upgradeInfoTextView = new TextView(this);
        upgradeInfoTextView.setLayoutParams(textViewParams);
        upgradeInfoTextView.setTextColor(ContextCompat.getColor(this, R.color.white));
        upgradeInfoTextView.setTextSize(16);
        upgradeInfoTextView.setGravity(Gravity.CENTER);
        if (upgradeIndex < upgradeInfoArrayList[pathIndex].size()) {
            upgradeInfoTextView.setText(upgradeInfoArrayList[pathIndex].get(upgradeIndex));
        }

        linearLayout.addView(upgradeNameTextView);
        linearLayout.addView(upgradeInfoTextView);

        LinearLayout finalLinearLayout = new LinearLayout(this);

        if (upgradeXpReqArray[pathIndex].length > upgradeIndex && upgradeXpReqArray[pathIndex][upgradeIndex] > User.getInstance().getTowerXP(towerType)) {
            RelativeLayout relativeLayout = new RelativeLayout(this);
            linearLayout.getChildAt(0).setAlpha(0.5f);
            linearLayout.getChildAt(1).setAlpha(0.5f);
            relativeLayout.addView(linearLayout);
            RelativeLayout.LayoutParams imageViewParams = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT
            );
            imageViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(imageViewParams);
            imageView.setImageResource(R.drawable.ic_lock_unfocused);
            imageView.setScaleX(3);
            imageView.setScaleY(3);
            relativeLayout.addView(imageView);
            finalLinearLayout.addView(relativeLayout);
        } else {
            finalLinearLayout.addView(linearLayout);
        }

        return finalLinearLayout;
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

    public void clickBack() {
        this.finish();
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.btnPrevUpgrade:
                clickPrevUpgrade(view, motionEvent);
                break;
            case R.id.btnNextUpgrade:
                clickNextUpgrade(view, motionEvent);
                break;
            case R.id.btnBack_towerUpgradeInfo:
                clickBack(view, motionEvent);
                break;
        }
        return true;
    }
}