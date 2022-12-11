package com.example.project2_rev2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2_rev2.R;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerStatsAdapter extends RecyclerView.Adapter<PlayerStatsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Object[]> playerStatsDataModel;

    public PlayerStatsAdapter(Context context, ArrayList<Object[]> playerStatsDataModel) {
        this.context = context;
        this.playerStatsDataModel = playerStatsDataModel;
    }

    @Override
    public PlayerStatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_row_player_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerStatsAdapter.ViewHolder holder, int position) {
        holder.txtStatName.setText(String.valueOf((playerStatsDataModel.get(position)[0])));
        holder.txtStatValue.setText(String.valueOf(((Double)playerStatsDataModel.get(position)[1]).intValue()));
    }

    @Override
    public int getItemCount() {
        return playerStatsDataModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtStatName, txtStatValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStatName = itemView.findViewById(R.id.txtStatName_playerStatsItemRow);
            txtStatValue = itemView.findViewById(R.id.txtStatValue_playerStatsItemRow);
        }
    }
}
