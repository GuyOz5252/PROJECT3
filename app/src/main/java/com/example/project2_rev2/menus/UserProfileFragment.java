package com.example.project2_rev2.menus;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project2_rev2.R;
import com.example.project2_rev2.data.User;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class UserProfileFragment extends Fragment {

    private View view;

    LinearProgressIndicator xpProgressBar;
    TextView txtPlayerLevel;
    TextView txtUsername;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        xpProgressBar = view.findViewById(R.id.xpProgressBar_userProfile);
        txtPlayerLevel = view.findViewById(R.id.txtLevel_userProfile);
        txtUsername = view.findViewById(R.id.txtUsername_userProfile);

        setPlayerLevel();
        txtUsername.setText(User.getInstance().getUsername());

        return view;
    }

    public void setPlayerLevel() {
        int level = User.getInstance().getUserLevel();
        double xp = User.getInstance().getUserXP();
        double max = level*1800;
        double percentage = (xp/max)*100;
        xpProgressBar.setProgress((int)percentage, true);
        txtPlayerLevel.setText(String.valueOf(level));
    }
}