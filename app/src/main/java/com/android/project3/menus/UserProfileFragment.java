package com.android.project3.menus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.project3.R;
import com.android.project3.adapters.PlayerStatsAdapter;
import com.android.project3.data.Constants;
import com.android.project3.data.User;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class UserProfileFragment extends Fragment {

    private View view;

    LinearProgressIndicator xpProgressBar;
    TextView txtPlayerLevel;
    TextView txtUsername;
    RecyclerView rvPlayerStats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        xpProgressBar = view.findViewById(R.id.xpProgressBar_userProfile);
        txtPlayerLevel = view.findViewById(R.id.txtLevel_userProfile);
        txtUsername = view.findViewById(R.id.txtUsername_userProfile);
        rvPlayerStats = view.findViewById(R.id.playerStatsRecyclerView);

        setPlayerLevel();
        txtUsername.setText(User.getInstance().getUsername());
        setPlayerStats();

        return view;
    }

    public void setPlayerLevel() {
        int level = User.getInstance().getUserLevel();
        double xp = User.getInstance().getUserXP();
        double max = level * Constants.LEVELING_FACTOR;
        double percentage = (xp/max)*100;
        xpProgressBar.setProgress((int)percentage, true);
        txtPlayerLevel.setText(String.valueOf(level));
    }

    public void setPlayerStats() {
        PlayerStatsAdapter adapter = new PlayerStatsAdapter(getContext(), User.getInstance().getPlayerStats().getAllStats());
        rvPlayerStats.setAdapter(adapter);
        rvPlayerStats.setLayoutManager(new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        });
    }
}