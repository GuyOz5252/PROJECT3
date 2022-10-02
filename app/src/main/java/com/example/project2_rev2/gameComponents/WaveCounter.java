package com.example.project2_rev2.gameComponents;

import static com.example.project2_rev2.utils.GaveValues.xCoordinate;
import static com.example.project2_rev2.utils.GaveValues.yCoordinate;

import android.content.Context;

import com.example.project2_rev2.R;
import com.example.project2_rev2.gameComponents.abstractComponents.TextUI;

public class WaveCounter extends TextUI {

    private WaveManager waveManager;

    public WaveCounter(WaveManager waveManager, Context context) {
        super(xCoordinate(360), yCoordinate(50), "WAVE: 0/" + waveManager.getWaveCount(), R.color.white, context);
        this.waveManager = waveManager;
    }

    @Override
    public void update() {

    }
}
