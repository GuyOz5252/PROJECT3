package com.android.project3.menus;

import static com.android.project3.utils.HelperMethods.getBitmapFromPicture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.project3.R;
import com.android.project3.data.TowerType;
import com.android.project3.data.User;

import java.util.ArrayList;
import java.util.Arrays;

public class TowerFragment extends Fragment implements View.OnTouchListener {

    private View view;

    LinearLayout currentTowerCard, prevTowerCard, nextTowerCard;
    ImageButton btnPrevTower, btnNextTower;

    private ArrayList<TowerType> towerArrayList;
    private int currentTowerIndex;

    private Bitmap towerBackground;

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

        float scale = this.getResources().getDisplayMetrics().densityDpi;
        this.towerBackground = Bitmap.createScaledBitmap(getBitmapFromPicture(getContext(), R.drawable.tower_background), (int)(105*(scale/160)), (int)(105*(scale/160)), false);

        this.towerArrayList = new ArrayList<>(Arrays.asList(TowerType.values()));
        this.currentTowerIndex = 0;
        cycleTowers();

        return view;
    }

    public void cycleTowers() {
        if (currentTowerIndex == 0) {
            prevTowerCard.setVisibility(View.INVISIBLE);
            btnPrevTower.setAlpha(0.5f);
            createTowerCard(currentTowerCard, currentTowerIndex);
            nextTowerCard.setVisibility(View.VISIBLE);
            btnNextTower.setAlpha(1f);
            createTowerCard(nextTowerCard, currentTowerIndex+1);
        } else if (currentTowerIndex == towerArrayList.size()-1) {
            prevTowerCard.setVisibility(View.VISIBLE);
            btnPrevTower.setAlpha(1f);
            createTowerCard(prevTowerCard, currentTowerIndex-1);
            createTowerCard(currentTowerCard, currentTowerIndex);
            nextTowerCard.setVisibility(View.INVISIBLE);
            btnNextTower.setAlpha(0.5f);
        } else {
            prevTowerCard.setVisibility(View.VISIBLE);
            btnPrevTower.setAlpha(1f);
            createTowerCard(prevTowerCard, currentTowerIndex-1);
            createTowerCard(currentTowerCard, currentTowerIndex);
            nextTowerCard.setVisibility(View.VISIBLE);
            btnNextTower.setAlpha(1f);
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

        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(params);

        ImageView towerBackgroundImageView = new ImageView(getContext());
        towerBackgroundImageView.setImageBitmap(towerBackground);

        RelativeLayout.LayoutParams towerImageViewParams = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        towerImageViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(towerImageViewParams);
        float scale = this.getResources().getDisplayMetrics().densityDpi;
        Bitmap newBitmap = Bitmap.createScaledBitmap(getBitmapFromPicture(getContext(), towerArrayList.get(towerIndex).icon), (int)(100*(scale/160)), (int)(100*(scale/160)), false);
        imageView.setImageBitmap(newBitmap);

        relativeLayout.addView(towerBackgroundImageView);
        relativeLayout.addView(imageView);

        TextView textView = new TextView(getContext());
        textView.setText(towerArrayList.get(towerIndex).towerName);
        textView.setTextSize(30);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(ContextCompat.getColor(getContext(), R.color.on_container_text_color));
        textView.setLayoutParams(params);

        LinearLayout pathOne = new LinearLayout(getContext());
            LinearLayout pathTwo = new LinearLayout(getContext());
            params.gravity = Gravity.CENTER;
            pathOne.setLayoutParams(params);
            pathTwo.setLayoutParams(params);
            for (int i = 0; i < 4; i++) {
                if (i < towerArrayList.get(towerIndex).towerUpgradePathOne.name.length) {
                    if (towerArrayList.get(towerIndex).towerUpgradePathOne.xpReq[i] <= User.getInstance().getTowerXP(towerArrayList.get(towerIndex))) {
                        LinearLayout.LayoutParams rectParams = new LinearLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT
                        );
                        rectParams.setMargins(5, 5, 5, 5);
                        ImageView greenRect = new ImageView(getContext());
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
                        ImageView grayRect = new ImageView(getContext());
                        grayRect.setImageResource(R.drawable.ic_square_gray);
                        grayRect.setScaleX(2);
                        grayRect.setLayoutParams(rectParams);
                        pathOne.addView(grayRect);
                    }
                }
            }
            for (int i = 0; i < 4; i++) {
                if (i < towerArrayList.get(towerIndex).towerUpgradePathTwo.name.length) {
                    if (towerArrayList.get(towerIndex).towerUpgradePathTwo.xpReq[i] <= User.getInstance().getTowerXP(towerArrayList.get(towerIndex))) {
                        LinearLayout.LayoutParams rectParams = new LinearLayout.LayoutParams(
                                LayoutParams.WRAP_CONTENT,
                                LayoutParams.WRAP_CONTENT
                        );
                        rectParams.setMargins(5, 5, 5, 5);
                        ImageView greenRect = new ImageView(getContext());
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
                        ImageView grayRect = new ImageView(getContext());
                        grayRect.setImageResource(R.drawable.ic_square_gray);
                        grayRect.setScaleX(2);
                        grayRect.setLayoutParams(rectParams);
                        pathTwo.addView(grayRect);
                    }
                }
            }

            linearLayout.addView(relativeLayout);
            linearLayout.addView(textView);
            linearLayout.addView(pathOne);
            linearLayout.addView(pathTwo);
    }

    public void clickTowerCard() {
        Intent intent = new Intent(getContext(), TowerUpgradeInfo.class);
        intent.putExtra("hasNavbar", true);
        intent.putExtra("towerType", towerArrayList.get(currentTowerIndex));
        startActivity(intent);
        // not finishing current activity in order to return to same point
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
