package com.example.project2_rev2.menus;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.Tower;

import java.util.ArrayList;
import java.util.Arrays;

public class TowerFragment extends Fragment implements View.OnTouchListener {

    private View view;

    LinearLayout currentTowerCard, prevTowerCard, nextTowerCard;
    ImageButton btnPrevTower, btnNextTower;

    private ArrayList<Tower.TowerType> towerArrayList;
    private int currentTowerIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tower, container, false);

        currentTowerCard = view.findViewById(R.id.towerCard_curr);
        prevTowerCard = view.findViewById(R.id.towerCard_prev);
        nextTowerCard = view.findViewById(R.id.towerCard_next);
        btnPrevTower = view.findViewById(R.id.btnPrevTower);
        btnNextTower = view.findViewById(R.id.btnNextTower);

        currentTowerCard.setOnTouchListener(this);
        btnPrevTower.setOnTouchListener(this);
        btnNextTower.setOnTouchListener(this);

        this.towerArrayList = new ArrayList<>(Arrays.asList(Tower.TowerType.values()));
        this.currentTowerIndex = 0;
        cycleTowers();

        return view;
    }

    public void cycleTowers() {
        if (currentTowerIndex == 0) {
            prevTowerCard.setVisibility(View.INVISIBLE);
            createTowerCard(currentTowerCard, currentTowerIndex);
            nextTowerCard.setVisibility(View.VISIBLE);
            createTowerCard(nextTowerCard, currentTowerIndex+1);
        } else if (currentTowerIndex == towerArrayList.size()-1) {
            prevTowerCard.setVisibility(View.VISIBLE);
            createTowerCard(prevTowerCard, currentTowerIndex-1);
            createTowerCard(currentTowerCard, currentTowerIndex);
            nextTowerCard.setVisibility(View.INVISIBLE);
        } else {
            prevTowerCard.setVisibility(View.VISIBLE);
            createTowerCard(prevTowerCard, currentTowerIndex-1);
            createTowerCard(currentTowerCard, currentTowerIndex);
            nextTowerCard.setVisibility(View.VISIBLE);
            createTowerCard(nextTowerCard, currentTowerIndex+1);
        }
    }

    public void createTowerCard(LinearLayout linearLayout, int towerIndex) {
        linearLayout.removeAllViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 5);

        ImageView imageView = new ImageView(view.getContext());
        imageView.setImageResource(towerArrayList.get(towerIndex).bitmap);
        imageView.setLayoutParams(params);

        TextView textView = new TextView(view.getContext());
        textView.setText(towerArrayList.get(towerIndex).towerName);
        textView.setTextSize(40);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
        textView.setLayoutParams(params);

        if (linearLayout == currentTowerCard) {
            LinearLayout pathOne = new LinearLayout(view.getContext());
            LinearLayout pathTwo = new LinearLayout(view.getContext());
            params.gravity = Gravity.CENTER;
            pathOne.setLayoutParams(params);
            pathTwo.setLayoutParams(params);
            for (int i = 0; i < 4; i++) {
                if (true) { // TODO set condition
                    LinearLayout.LayoutParams rectParams = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    );
                    rectParams.setMargins(5, 5, 5, 5);
                    ImageView greenRect = new ImageView(view.getContext());
                    greenRect.setImageResource(R.drawable.ic_square_green);
                    greenRect.setScaleX(2);
                    greenRect.setLayoutParams(rectParams);
                    pathOne.addView(greenRect);
                } else {
                    LinearLayout.LayoutParams rectParams = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    );
                    rectParams.setMargins(5, 5, 5, 5);
                    ImageView grayRect = new ImageView(view.getContext());
                    grayRect.setImageResource(R.drawable.ic_square_gray);
                    grayRect.setScaleX(2);
                    grayRect.setLayoutParams(rectParams);
                    pathOne.addView(grayRect);
                }
            }
            for (int i = 0; i < 4; i++) {
                if (false) { // TODO set condition
                    LinearLayout.LayoutParams rectParams = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    );
                    rectParams.setMargins(5, 5, 5, 5);
                    ImageView greenRect = new ImageView(view.getContext());
                    greenRect.setImageResource(R.drawable.ic_square_green);
                    greenRect.setScaleX(2);
                    greenRect.setLayoutParams(rectParams);
                    pathTwo.addView(greenRect);
                } else {
                    LinearLayout.LayoutParams rectParams = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT
                    );
                    rectParams.setMargins(5, 5, 5, 5);
                    ImageView grayRect = new ImageView(view.getContext());
                    grayRect.setImageResource(R.drawable.ic_square_gray);
                    grayRect.setScaleX(2);
                    grayRect.setLayoutParams(rectParams);
                    pathTwo.addView(grayRect);
                }
            }

            linearLayout.addView(imageView);
            linearLayout.addView(textView);
            linearLayout.addView(pathOne);
            linearLayout.addView(pathTwo);
        } else {
            linearLayout.addView(imageView);
            linearLayout.addView(textView);
        }
    }

    public void clickTowerCard() {

    }

    public void clickTowerCard(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickTowerCard();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.95f);
            view.setScaleY(0.95f);
        }
    }

    public void clickPrevTower() {
        if (currentTowerIndex != 0) {
            currentTowerIndex--;
            cycleTowers();
        }
    }

    public void clickPrevTower(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickPrevTower();
            view.setScaleX(1);
            view.setScaleY(1);
        } else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.setScaleX(0.9f);
            view.setScaleY(0.9f);
        }
    }

    public void clickNextTower() {
        if (currentTowerIndex != towerArrayList.size()-1) {
            currentTowerIndex++;
            cycleTowers();
        }
    }

    public void clickNextTower(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            clickNextTower();
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
            case R.id.towerCard_curr:
                clickTowerCard(view, motionEvent);
                break;
            case R.id.btnPrevTower:
                clickPrevTower(view, motionEvent);
                break;
            case R.id.btnNextTower:
                clickNextTower(view, motionEvent);
                break;
        }
        return true;
    }
}
